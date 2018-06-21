/*
 * 文 件 名:  StaticTest
 * 版    权:  Copyright 2018 南京慕冉信息科技有限公司,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <1.0.0>
 * 创 建 人:  youfang
 * 创建时间:   2018-06-21

 */
package extendtest;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-21 下午 08:08]
 * @since [小倦鸟/远方模块]
 **/
public class StaticTest {

    private static String a = initStaticString();

    private static String initStaticString(){
        System.out.println("初始化静态变量");
        return "youfang";
    }

    public static char getSomeThing(int index){
        return a.charAt(index);
    }

    public static void main(String[] args) {
        System.out.println(StaticTest.getSomeThing(2));
        System.out.println(StaticTest.getSomeThing(3));
        System.out.println(StaticTest.getSomeThing(4));
    }
}
