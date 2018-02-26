package cn.acyou.iblog.controller;

import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @date 2018-02-26 17:50
 **/
@Controller
@RequestMapping(value="/excel")
public class ExcelController {

    /**
     *
     * 方法名：
     * 开发者：
     * 开发时间：2016-12-29
     */
    @RequestMapping(value = "/export",method = {RequestMethod.POST,RequestMethod.GET})
    public String export(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        //标题
        List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
        //内容
        List<Map<String,Object>> dataResult = new ArrayList<Map<String,Object>>();
        entityList.add(new ExcelExportEntity("表头1", "table1", 15));
        entityList.add(new ExcelExportEntity("表头2", "table2", 25));
        entityList.add(new ExcelExportEntity("表头3", "table3", 35));
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("table1", "苹果"+i);
            map.put("table2", "香蕉"+i);
            map.put("table3", "鸭梨"+i);
            dataResult.add(map);
        }
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        String fileName = "easypoi测试列表";
        modelMap.put(MapExcelConstants.FILE_NAME, fileName);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("easypoi列表", "测试列表"));
        return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
    }

}