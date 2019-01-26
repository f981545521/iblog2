package beancopytest;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 17:11
 **/
public class OrikaUtil {
    private MapperFacade mapperFacade = null;

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    /**
     * 初始化Orika注册
     */
    OrikaUtil(){
        mapperFacade = mapperFactory.getMapperFacade();

    }

    public <V, P> P convert(V base, Class<P> target) {
        return mapperFacade.map(base, target);
    }

    public <V, P> List<P> convertList(List<V> baseList, Class<P> target) {
        List<P> list = baseList.isEmpty() ? Collections.<P>emptyList() : mapperFacade.mapAsList(baseList, target);
        return list;
    }

}
