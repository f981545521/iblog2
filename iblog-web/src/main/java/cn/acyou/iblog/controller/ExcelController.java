package cn.acyou.iblog.controller;

import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.utility.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
public class ExcelController extends BaseController{

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
        ExcelExportEntity entity = new ExcelExportEntity("表头4", "table4", 35);
        entityList.add(entity);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("table1", "苹果"+i);
            map.put("table2", "香蕉"+i);
            map.put("table3", "鸭梨"+i);
            map.put("table4", i);
            dataResult.add(map);
        }
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        String fileName = "easypoi测试列表";
        modelMap.put(MapExcelConstants.FILE_NAME, fileName);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("easypoi列表", "测试列表"));
        return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
    }

    @RequestMapping(value = "/export2",method = {RequestMethod.POST,RequestMethod.GET})
    public String export2(HttpServletResponse response) throws IOException {
        List<Map<String,Object>> dataResult = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("sheetName", "sheet1");
            map.put("table1", "草莓"+i);
            map.put("table2", "果汁"+i);
            map.put("table3", "芒果"+i);
            map.put("table4", i);
            dataResult.add(map);
        }
        String[] keys = {"table1","table2","table3","table4"};//map中的key
        String[] columnNames = {"表头1","表头2","表头3","表头4"};//列名
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelUtil.createWorkBook(dataResult,keys,columnNames).write(os);
        ExcelUtil.writeIO(response,os,"easypoi测试列表2");
        return AppConstant.SUCCESS;
    }

    @RequestMapping(value = "/export3",method = {RequestMethod.POST,RequestMethod.GET})
    public void fe_map() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "WEB-INF/doc/专项支出用款申请书_map.xls");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2014-12-25");
        map.put("money", 2000000.00);
        map.put("upperMoney", "贰佰万");
        map.put("company", "执笔潜行科技有限公司");
        map.put("bureau", "财政局");
        map.put("person", "JueYue");
        map.put("phone", "1879740****");
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("zijin", i * 10000 + "");
            lm.put("bianma", "A001");
            lm.put("mingcheng", "设计");
            lm.put("xiangmumingcheng", "EasyPoi " + i + "期");
            lm.put("quancheng", "开源项目");
            lm.put("sqje", i * 10000 + "");
            lm.put("hdje", i * 10000 + "");

            listMap.add(lm);
        }
        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/专项支出用款申请书_map.xls");
        workbook.write(fos);
        fos.close();
    }



    @RequestMapping(value = "/export4",method = {RequestMethod.POST,RequestMethod.GET})
    public String export(){
        //内容
        List<Map<String,Object>> dataResult = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("table1", "苹2果"+i);
            map.put("table2", "香2蕉"+i);
            map.put("table3", "鸭2梨"+i);
            map.put("table4", i);
            dataResult.add(map);
        }

        return AppConstant.SUCCESS;
    }

}