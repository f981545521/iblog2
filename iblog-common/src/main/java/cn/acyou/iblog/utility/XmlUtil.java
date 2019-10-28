package cn.acyou.iblog.utility;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * DOM4j XML 工具类
 */
public final class XmlUtil {
    /**
     * 使用的查询子孙节点的路径
     */
    private static final String SELECT_PATH = ".//";
    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    private XmlUtil() {

    }


    /**
     * 描述  取得当前节点特定子节点的正文，只取第一个
     *
     * @param element   当前XML节点
     * @param fieldCode 正文内容
     * @return
     */
    public static String getSonFieldContent(final Element element, final String fieldCode) {
        if (element == null) {
            return null;
        }

        String content = null;
        Element ele = element.element(fieldCode);
        if (ele != null) {
            content = ele.getText();
        }
        return content;
    }


    /**
     * 描述 取得当前节点特定子孙节点的正文，只取第一个
     *
     * @param element   操作的xml元素
     * @param fieldCode 栏位名称
     * @return
     */
    public static Element getDesFieldElement(final Element element, final String fieldCode) {
        Element tagertElement = null;
        List<Element> list = (List<Element>) element.selectNodes(SELECT_PATH + fieldCode);
        if (list.size() > 0) {
            tagertElement = list.get(0);
        }

        return tagertElement;
    }

    /**
     * 描述  取得当前节点特定子孙节点的正文，只取第一个
     *
     * @param text      传入报文
     * @param fieldCode 标签代码
     * @return 标签内容
     * @throws DocumentException
     */
    public static String getDesFieldText(final String text, final String fieldCode) throws DocumentException {
        Element rootEle = DocumentHelper.parseText(text).getRootElement();
        Element desFieldEle = getDesFieldElement(rootEle, fieldCode);
        if (desFieldEle != null) {
            return desFieldEle.getText();
        } else {
            return null;
        }
    }


    /**
     * 描述 取得当前节点特定子孙节点的正文，制定个数
     *
     * @param text      传入报文
     * @param fieldCode 标签代码
     * @param index     处理的元素索引
     * @return 标签内容
     * @throws DocumentException
     */
    public static String getDesFieldTextByIndex(final String text, final String fieldCode, final int index)
            throws DocumentException {
        Element rootEle = DocumentHelper.parseText(text).getRootElement();
        List<Element> eleList = (List<Element>) rootEle.selectNodes(SELECT_PATH + fieldCode);
        if (eleList.size() > index) {
            return eleList.get(index).getText();
        } else {
            return null;
        }

    }


    /**
     * 描述 取得子孙节点集合
     *
     * @param element   操作的xml元素
     * @param fieldCode 栏位名称
     * @return 正文内容
     */
    public static List<Element> getDesFieldElementList(final Element element, final String fieldCode) {
        return element.selectNodes(SELECT_PATH + fieldCode);

    }


    /**
     * 描述  取得当前节点下所有拥有当前属性的元素
     *
     * @param element   当前元素
     * @param fieldCode 查找属性名称
     * @return
     */
    public static List<Element> getEleListByAttr(final Element element, final String fieldCode) {
        // 返回数据
        List<Element> eleList = new ArrayList<Element>();

        // 查找并循环添加
        List<Attribute> attrList = element.selectNodes(SELECT_PATH + "@" + fieldCode);
        for (Attribute attr : attrList) {
            eleList.add(attr.getParent());
        }

        // 返回
        return eleList;
    }


    /**
     * 描述  取得当前节点下所有拥有当前属性的元素
     *
     * @param msgText 当前元素
     * @param path    查找属性名称
     * @return
     */
    public static List<Element> getEleListByPath(final String msgText, final String path) {
        // 返回数据
        List<Element> eleList = new ArrayList<Element>();

        try {
            Document document;
            document = DocumentHelper.parseText(msgText);
            eleList = document.selectNodes(path);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 返回
        return eleList;
    }


    /**
     * 描述  一次性增加多个子节点
     *
     * @param fatherEle  父亲节点
     * @param sonEleList 要增加的子节点元素
     */
    public static void addElements(final Element fatherEle, final List<Element> sonEleList) {
        for (Element ele : sonEleList) {
            fatherEle.add((Element) ele.clone());
        }
    }

    /**
     * 描述 增加子元素
     *
     * @param fatherEle     父亲节点
     * @param sonEleName    儿子节点名
     * @param sonEleContent 儿子节点内容
     * @return 儿子节点
     */
    public static Element addElement(final Element fatherEle, final String sonEleName, final String sonEleContent) {
        // 创建子节点
        Element ele = DocumentHelper.createElement(sonEleName);
        if (StringUtils.isNotBlank(sonEleContent)) {
            ele.setText(sonEleContent);
        }
        // 关联
        fatherEle.add(ele);
        // 返回
        return ele;
    }


    /**
     * 描述 最后最后一级的子节点，如果传入数据为空不增加
     *
     * @param fatherEle     父亲节点
     * @param sonEleName
     * @param sonEleContent
     * @return
     */
    public static Element addFinalSonElement(final Element fatherEle, final String sonEleName,
                                             final String sonEleContent) {
        // 创建子节点
        Element ele = null;

        // 增加
        if (StringUtils.isNotBlank(sonEleContent)) {
            ele = DocumentHelper.createElement(sonEleName);
            ele.setText(sonEleContent);
            // 关联
            fatherEle.add(ele);
        }

        // 返回
        return ele;
    }


    /**
     * 描述 取得XMl字符串中的指定路径字符
     *
     * @param text
     * @param url
     * @return
     */
    public static String getUrlElementText(final String text, final String url) {
        // 返回值
        String resultText = null;
        // 转化XML
        Element busiElement;
        try {
            busiElement = DocumentHelper.parseText(text).getRootElement();
            List<Element> eleList = busiElement.selectNodes(url);
            if (eleList.size() > 0) {
                resultText = ((Element) busiElement.selectNodes(url).get(0)).getText();
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        }

        // 返回
        return resultText;
    }

    /**
     * 描述
     *
     * @param doc
     * @param url
     * @return
     */
    public static String getUrlElementText(Document doc, final String url) {
        Node node = doc.selectSingleNode(url);
        if (node == null) {
            return null;
        }
        return node.getText();
    }

    /**
     * 描述 格式化xml字符串
     *
     * @param xmlText xml字符串
     * @return 格式化后的字符
     * @throws Exception
     */
    public static String formatXml(final String xmlText) throws Exception {
        // 返回字符串
        String reText;

        try {
            reText = formatXml(xmlText, DEFAULT_ENCODING);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        // 返回
        return reText;
    }


    /**
     * 描述 格式化xml字符串
     *
     * @param xmlText    xml字符串
     * @param inEncoding 字符编码
     * @return 格式化后的字符
     * @throws Exception
     */
    public static String formatXml(final String xmlText, final String inEncoding) throws Exception {
        String encoding = inEncoding;
        // 返回字符串
        String reText;

        if (StringUtils.isBlank(encoding)) {
            encoding = DEFAULT_ENCODING;
        }
        // 转化
        try {
            // 将字符串格式转换成document对象
            Document document = DocumentHelper.parseText(xmlText);
            // 注意,用这种方式来创建指定格式的format
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(encoding);
            // 创建String输出流
            StringWriter out = new StringWriter();
            // 包装String流
            XMLWriter writer = new XMLWriter(out, format);
            // 将当前的document对象写入底层流out中
            writer.write(document);
            writer.close();
            reText = out.toString();
        } catch (Exception e) {
            String errTagMsg = "数据格式化失败！";
            throw new Exception(errTagMsg);
        }

        // 返回
        return reText;
    }


    /**
     * 描述 复制XML元素的值到对应的对象属性当中
     *
     * @param obj 配置对象
     * @param ele XML元素
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void copyEleToAttr(final Object obj, final Element ele)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String fieldName = fields[i].getName();
            fields[i].setAccessible(true);
            /**
             * String属性直接进行赋值，如果是复杂类，进行下一步赋值
             */
            if (fields[i].getType().equals(String.class)) {
                String eleContent = XmlUtil.getSonFieldContent(ele, fieldName);
                if (eleContent != null && !eleContent.equals("")) {
                    fields[i].set(obj, eleContent);
                }
            } else if (fields[i].getType().equals(List.class)) {
                String eleName = fieldName.substring(0, fieldName.length() - 4);
                List<Element> eleList = ele.elements(eleName);
                List<Object> field = (List<Object>) fields[i].get(obj);

                // 取得泛型类型
                Type fc = fields[i].getGenericType();
                ParameterizedType pt = (ParameterizedType) fc;
                Class driveClass = (Class) pt.getActualTypeArguments()[0];
                try {
                    for (Element singleEle : eleList) {
                        Object singObjct = driveClass.newInstance();
                        // 判断是否是字符串
                        if (driveClass.equals(String.class)) {
                            singObjct = singleEle.getText();

                        } else {
                            copyEleToAttr(singObjct, singleEle);
                        }
                        field.add(singObjct);
                    }
                } catch (Exception e) {
                    for (Element singleEle : eleList) {
                        String eleContent = singleEle.getTextTrim();
                        if (eleContent != null && !eleContent.equals("")) {
                            field.add(eleContent);
                        }
                    }
                }

            } else {
                Object fieldObject = fields[i].get(obj);
                Element fieldEle = ele.element(fieldName);
                if (fieldObject != null && fieldEle != null) {
                    copyEleToAttr(fieldObject, fieldEle);
                }
            }
        }
    }


    /**
     * 描述  复制对应的对象属性当到XML元素的值中
     *
     * @param obj 配置对象
     * @param ele XML元素
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void copyAttrToEle(final Object obj, final Element ele)
            throws IllegalArgumentException, IllegalAccessException {

        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String fieldName = fields[i].getName();
            fields[i].setAccessible(true);

            // 过滤不处理的对象
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }

            /**
             * String属性直接进行赋值，如果是复杂类，进行下一步赋值
             */
            if (fields[i].getType().equals(String.class)) {
                XmlUtil.addFinalSonElement(ele, fieldName, (String) fields[i].get(obj));
            } else if (fields[i].getType().equals(List.class)) {
                List<Object> fieldList = (List<Object>) fields[i].get(obj);
                for (Object object : fieldList) {
                    if (object.getClass().equals(String.class)) {
                        XmlUtil.addElement(ele, fieldName, (String) object);
                    } else {
                        copyAttrToEle(object, XmlUtil.addElement(ele, fieldName, null));
                    }
                }
            } else {
                Object fieldObject = fields[i].get(obj);
                if (fieldObject != null) {
                    Element fieldEle = XmlUtil.addElement(ele, fieldName, null);
                    copyAttrToEle(fields[i].get(obj), fieldEle);
                }
            }
        }

    }

}