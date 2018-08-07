package genericity;

import cn.acyou.iblog.dozer.DozerHelper;

import java.io.Serializable;
import java.util.List;

/**
 * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
 * 在实例化泛型类时，必须指定T的具体类型
 *
 * 泛型的类型参数只能是类类型，不能是简单类型。
 *
 * @author youfang
 * @version [1.0.0, 2018-08-07 上午 10:19]
 **/
public class Children<T> implements Serializable {
    private static final long serialVersionUID = -132510321898489021L;
    private List<T> list;

    public Children() {
    }

    public <E> Children(Children<E> pageResult, Class<T> type) {
        DozerHelper.convertList(pageResult.getList(), type);
    }


        public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Children{");
        for (T t: list){
            sb.append("T = ").append(t).append(",");
        }
        sb.append('}');
        return sb.toString();
    }
}
