package cn.acyou.iblog.exceptionResolver;

import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.exception.SystemException;
import cn.acyou.iblog.utility.JsonResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.google.common.base.Throwables;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.PriorityOrdered;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import se.spagettikod.optimist.LockedByAnotherUserException;
import se.spagettikod.optimist.ModifiedByAnotherUserException;
import se.spagettikod.optimist.RemovedByAnotherUserException;
import se.spagettikod.optimist.impl.AnnotationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author youfang
 * @date 2018-04-12 下午 09:47
 **/
public class JsonExceptionResolver extends SimpleMappingExceptionResolver implements InitializingBean, PriorityOrdered {

    private static final Logger logger = LoggerFactory.getLogger(JsonExceptionResolver.class);

    private FastJsonJsonView jsonView;
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        Throwable throwable = Throwables.getRootCause(ex);
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            boolean returnJson = method.getAnnotation(ResponseBody.class) != null;
            if (returnJson) {
                Class clazz = method.getReturnType();
                Object result = null;
                try {
                    result = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException ignore) {
                }
                if (result != null && result instanceof JsonResult) {
                    JsonResult ri = (JsonResult) result;
                    //handle optimist locker exception
                    if (throwable instanceof ModifiedByAnotherUserException ||
                            throwable instanceof RemovedByAnotherUserException ||
                            throwable instanceof LockedByAnotherUserException ||
                            throwable instanceof AnnotationException) {
                        handleOptimistException(throwable, ri);
                    } else {
                        ri.setState(AppConstant.FALSE);
                        ri.setMessage(getMessageStr(throwable));
                    }
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("result", result);
                map.put("message",getMessageStr(throwable));
                return new ModelAndView(jsonView, map);
            } else {
                throwable = new SystemException(getMessageStr(throwable));
            }
        }
        return super.doResolveException(request, response, handler, (Exception) throwable);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        jsonView = new FastJsonJsonView();
        jsonView.setExtractValueFromSingleKeyModel(true);
    }

    @Override
    protected void logException(Exception ex, HttpServletRequest request) {
        final Throwable throwable = Throwables.getRootCause(ex);
        if (throwable instanceof ModifiedByAnotherUserException ||
                throwable instanceof RemovedByAnotherUserException ||
                throwable instanceof IllegalArgumentException) {
            logger.warn(ex.getMessage(), throwable);
        } else {
            String str = ex.getMessage() +
                    '\n' +
                    "Parameters:" +
                    JSON.toJSON(request.getParameterMap()) +
                    '\n';
            logger.error(str, ex);
        }
    }

    /**
     * handle optimist locker exception
     *
     * @param cause      Exception
     * @param jsonResult return object
     */
    private void handleOptimistException(Throwable cause, JsonResult jsonResult) {
        if (cause instanceof ModifiedByAnotherUserException) {
            jsonResult.setState(AppConstant.MODIFIED_BY_ANOTHER_USER);
            jsonResult.setMessage(AppConstant.MODIFIED_BY_ANOTHER_USER_MSG);
        } else if (cause instanceof RemovedByAnotherUserException) {
            jsonResult.setState(AppConstant.REMOVED_BY_ANOTHER_USER);
            jsonResult.setMessage(AppConstant.REMOVED_BY_ANOTHER_USER_MSG);
        } else {
            jsonResult.setState(AppConstant.FALSE);
            jsonResult.setMessage(AppConstant.DEFAULT_EXCEPTION_MSG);
        }
    }

    private String getMessageStr(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return "系统运行异常";
        } else if (throwable.getClass().getSimpleName().toLowerCase().contains("timeout")) {
            return AppConstant.DEFAULT_EXCEPTION_MSG;
        } else if (StringUtils.isBlank(throwable.getMessage())) {
            return AppConstant.DEFAULT_EXCEPTION_MSG;
        } else if ((throwable instanceof SQLException)) {
            return AppConstant.DEFAULT_EXCEPTION_MSG;
        } else {
            return throwable.getMessage();
        }
    }
}
