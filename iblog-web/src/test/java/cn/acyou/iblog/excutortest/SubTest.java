package cn.acyou.iblog.excutortest;

import org.junit.Test;

/**
 * @author youfang
 * @date 2018-03-03 23:29
 **/
public class SubTest {

    @Test
    public void test1(){
        String ss= "id,sort_name,description,id_user,createtime,modifiedtime,version";
        String[] strings = ss.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++){
            sb.append("ibs." + strings[i] + ",");
        }
        System.out.println(sb);
    }
}
