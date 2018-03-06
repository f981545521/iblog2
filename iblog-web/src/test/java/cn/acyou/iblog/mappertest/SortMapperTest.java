package cn.acyou.iblog.mappertest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.mappers.SortMapper;
import cn.acyou.iblog.model.Sort;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-24 21:14
 **/
public class SortMapperTest extends BaseTest{
    private SortMapper sortMapper;

    @Before
    public void initTest(){
        sortMapper = applicationContext.getBean("sortMapper",SortMapper.class);
    }

    @Test
    public void test1(){
        List<Sort> sortList = sortMapper.findAll();
        System.out.println(sortMapper.findSortById(1));
    }

    @Test
    public void test2(){
        List<Sort> sortList = sortMapper.findSortNamesByUid(5);
        System.out.println(sortList);
    }

    @Test
    public void test3(){
        List<Sort> sortList = Lists.newArrayList();
        sortList.add(new Sort(1,"ceshi1",11,"批量新增1",new Timestamp(System.currentTimeMillis()),new Date(),null));
        sortList.add(new Sort(2,"ceshi2",11,"批量新增2",new Timestamp(System.currentTimeMillis()),new Date(),null));
        sortMapper.addSort(sortList);
    }

    @Test
    public void test4(){
        Sort sort = sortMapper.findSortById(7);
        System.out.println(sort);
        sort.setDescription("修改的4");
        //sort.setVersion(2);
        sortMapper.updateSort(sort);
        Sort sortNew = sortMapper.findSortById(7);
        System.out.println(sortNew);
    }

    @Test
    public void testOptmisticBatch(){
        Sort sort = sortMapper.findSortById(6);
        Sort sort2 = sortMapper.findSortById(7);
        sort.setSortName("修改的5");
        sort2.setSortName("修改的6");
        //sort.setVersion(2);
        List<Sort> sortList = new ArrayList<>();
        sortList.add(sort);
        sortList.add(sort2);
        sortMapper.updateSortBatch(sortList);
    }
}
