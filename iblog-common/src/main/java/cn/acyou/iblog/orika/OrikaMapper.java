package cn.acyou.iblog.orika;

import cn.acyou.iblog.orika.converter.BigIntegerConverter;
import com.google.common.collect.Lists;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 17:11
 **/
public class OrikaMapper {
    private MapperFacade mapperFacade = null;

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    @Autowired(required = false)
    private List<OrikaRegisty> orikaRegistyList = Lists.newArrayList();

    /**
     * 初始化Orika注册
     */
    @PostConstruct
    private void init() {
        if (!orikaRegistyList.isEmpty()) {
            for (OrikaRegisty orikaRegisty : orikaRegistyList) {
                orikaRegisty.register(mapperFactory);
            }
        }
        mapperFactory.getConverterFactory().registerConverter(BigIntegerConverter.class.getSimpleName(), new BigIntegerConverter());
        //初始化注册信息
        //mapperFactory.classMap(People.class, Student.class).field("name","userName").byDefault().register();
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
