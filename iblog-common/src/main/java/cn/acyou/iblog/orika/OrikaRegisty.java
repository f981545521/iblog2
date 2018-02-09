package cn.acyou.iblog.orika;

import ma.glasnost.orika.MapperFactory;

/**
 * @author youfang
 * @date 2018-02-09 17:12
 **/
public interface OrikaRegisty {

    void register(MapperFactory factory);
}