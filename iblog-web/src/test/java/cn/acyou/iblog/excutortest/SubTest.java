package cn.acyou.iblog.excutortest;

import org.junit.Test;

/**
 * @author youfang
 * @date 2018-03-03 23:29
 **/
public class SubTest {

    @Test
    public void test1(){
        String ss= "id, title, content, excerpt, type, id_user, id_sort, id_attachment, top, hide, fabulous, comment_number, allow_comment, creationtime, modifiedtime, version";
        String[] strings = ss.split(", ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++){
            sb.append("ibb." + strings[i] + ",");
        }
        System.out.println(sb);
    }
}
