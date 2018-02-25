package cn.acyou.iblog.controller;

import cn.acyou.iblog.model.Sort;
import cn.acyou.iblog.service.SortService;
import cn.acyou.iblog.utility.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @date 2018-02-24 21:01
 **/
@Controller
@RequestMapping("/sort")
public class SortController {

    private Logger log = Logger.getLogger(getClass());

    @Resource
    private SortService sortService;

    /**
     * 查看所有分类
     * @param uid
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public JsonResult listSorts(Integer uid){
        List<Sort> list=sortService.listSorts(uid);
        //System.out.println(list);
        return new JsonResult(list);
    }
    /**
     * 添加分类
     * @param sortName
     * @param uid
     * @param description
     * @return
     */
    @RequestMapping("addSort.do")
    @ResponseBody
    public JsonResult addSort(String sortName,Integer uid,String description){

        log.warn("添加分类信息：" + sortName + "" + uid + "" + description);
        int n = sortService.addSort(sortName, uid, description);
        if(n!=1){
            return new JsonResult("添加分类失败！");
        }
        return new JsonResult("添加成功");
    }
    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping("delSort.do")
    @ResponseBody
    public JsonResult delSort(Integer id){
        int n = sortService.delSort(id);
        if(n!=1){
            return new JsonResult(1,"删除分类失败！");
        }
        return new JsonResult(0,"删除成功！");
    }

    /**
     * 修改分类
     * @param id
     * @param sortName
     * @param description
     * @return
     */
    @RequestMapping("updateSort.do")
    @ResponseBody
    public JsonResult updateSort(Integer id,String sortName,String description){
        Sort sort = sortService.updateSort(id, sortName, description);
        return new JsonResult(sort);
    }

    @RequestMapping("updateSortByOptmistic.do")
    @ResponseBody
    public JsonResult updateSortByOptmistic(Integer id){
        Sort sort= sortService.updateSort(id);
        return new JsonResult(sort);
    }


    @RequestMapping("listSortNames.do")
    @ResponseBody
    public JsonResult listSortNames(Integer uid){
        List<Sort> list = sortService.listSortNames(uid);
        return new JsonResult(list);
    }







}