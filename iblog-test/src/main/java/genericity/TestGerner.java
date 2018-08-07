package genericity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-07 上午 10:16]
 **/
public class TestGerner {

    /**
     * 泛型的使用
     *
     * 泛型有三种使用方式，分别为：泛型类、泛型接口、泛型方法
     */
    public static void main(String[] args) {
        Children<Son> childrens = new Children<>();
        childrens.setList(listSon());

        //Children<Daughter> daughters = new Children<>(childrens, Daughter.class);
        System.out.println(childrens);
        //System.out.println(daughters);

        //System.out.println(children instanceof Children);

        Children<String> str = new Children<>();
        List<String> stringList = Lists.newArrayList("aaa","bbb");
        str.setList(stringList);
        System.out.println(str);

        showFullName(childrens);


    }

    private static List<Son> listSon(){
        List<Son> sonList = Lists.newArrayList();
        sonList.add(new Son("小明", "张"));
        sonList.add(new Son("小法"));
        sonList.add(new Son("小华"));
        return sonList;
    }

    private static void showFullName(Children<? extends Parent> children){
        List<? extends Parent> list = children.getList();
        for (Parent p : list){
            System.out.println(p.getSurname());
        }
    }

}
