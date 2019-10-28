package cn.acyou.iblog.simpletest;

import org.apache.ibatis.io.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2019/10/28]
 **/
public class DomTest {

    public static void main(String[] args) throws Exception {

        // 创建xml解析器
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = Resources.getResourceAsStream("conf/menu/211-iblog.xml");
        // 加载文件,读取到document中
        Document document = saxReader.read(resourceAsStream);
        // 通过document对象获取根元素的信息
        Element rootEle = document.getRootElement();
        // 通过根元素获取下面的所有直接子元素
        List<Element> rchilds = rootEle.elements();

        // 根据元素名字获取根元素下某个子元素
        Element menusNode = rootEle.element("menus");
        List<Element> menusNodeList = menusNode.elements();

        // 遍历根元素下所有直接子元素
        for (Element e : menusNodeList) {
            // 获取子元素名称
            System.out.print(e.getName() + " ");
            // 获取下一级子元素
            List<Element> echilds = e.elements();
            for (Element e2 : echilds) {
                // 获取子元素名称和值
                System.out.println("\t" + e2.getName() + "=" + e2.getTextTrim());
            }
            System.out.println("——————————————————————————————————————————————————");

        }


        System.out.println("-------------------------");

    }
}
