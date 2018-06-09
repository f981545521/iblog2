/*
 * 文 件 名:  ValidateUtilTest
 * 版    权:  Copyright 2018 南京慕冉信息科技有限公司,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <1.0.0>
 * 创 建 人:  youfang
 * 创建时间:   2018-06-09

 */
package cn.acyou.iblog.utiltytest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.so.ActiveCodeSo;
import cn.acyou.iblog.utility.JsonResult;
import cn.acyou.iblog.utility.ValidateUtil;
import org.junit.Test;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-09 下午 12:37]
 * @since [小倦鸟/远方模块]
 **/
public class ValidateUtilTest extends BaseTest {

    @Test
    public void test1(){
        ActiveCodeSo so = new ActiveCodeSo();
        so.setUsed("1");
        JsonResult result =ValidateUtil.valid(so);
        System.out.println(result);
    }
}
