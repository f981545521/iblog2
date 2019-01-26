package cn.acyou.iblog.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtil {

    public static <T,E> E copy(T t,Class<E> clz){
        try {
            E e = clz.newInstance();
            BeanUtils.copyProperties(t,e);
            return e;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static <T,E> List<E> copyList(List<T> l,Class<E> clz){
        List<E> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(l)){
            l.forEach(item->list.add(copy(item,clz)));
        }
        return list;
    }
}
