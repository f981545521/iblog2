package cn.acyou.iblog.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.math.BigInteger;

/**
 * @author youfang
 * @date 2018-02-09 17:12
 **/
public class BigIntegerConverter extends BidirectionalConverter<String, BigInteger> {

    @Override
    public BigInteger convertTo(String source, Type<BigInteger> destinationType, MappingContext mappingContext) {
        return source == null ? null : new BigInteger(source);
    }

    @Override
    public String convertFrom(BigInteger source, Type<String> destinationType, MappingContext mappingContext) {
        return source == null ? null : source.toString();
    }
}