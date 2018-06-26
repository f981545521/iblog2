package cn.acyou.iblog.dozer;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.dozer.Mapper;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DozerHelper {

    private static Mapper mapper;

    public static <P> P clone(P base) {

        if (base == null) {
            return null;
        }
        if (ClassUtils.isPrimitiveOrWrapper(base.getClass()) || base instanceof String) {
            return base;
        } else {
            Mapper mapper = getMapper();
            return (P) mapper.map(base, base.getClass());
        }

    }

    public static <P> List<P> cloneList(List<P> baseList) {

        if (baseList == null) {
            return null;
        } else if (baseList.isEmpty()) {
            return Lists.newArrayList();
        }
        {
            List<P> targetList = Lists.newArrayListWithExpectedSize(baseList.size());
            for (P p : baseList) {

                targetList.add(clone(p));
            }
            return targetList;
        }

    }

    public static <P> Set<P> cloneSet(Set<P> baseSet) {

        if (baseSet == null) {
            return null;
        } else if (baseSet.isEmpty()) {
            return Sets.newHashSet();
        } else {
            Set<P> targetSet = Sets.newHashSetWithExpectedSize(baseSet.size());
            for (P p : baseSet) {
                targetSet.add(clone(p));
            }
            return targetSet;
        }

    }

    public static <V, P> P convert(V base, Class<P> target) {

        if (base == null) {
            return null;
        } else {
            Mapper mapper = getMapper();
            return mapper.map(base, target);
        }

    }

    public static <V, P> P convert(V base, P target) {

        if (base != null) {
            Mapper mapper = getMapper();
            mapper.map(base, target);
            return target;
        }
        return target;
    }

    public static <V, P> List<P> convertList(List<V> baseList, Class<P> target) {

        if (baseList == null) {
            return null;
        } else if (baseList.isEmpty()) {
            return Lists.newArrayList();
        } else {
            List<P> targetList = Lists.newArrayListWithExpectedSize(baseList.size());
            for (V vo : baseList) {

                targetList.add(convert(vo, target));
            }
            return targetList;
        }

    }
    public static <V, P> List<P> convertCollection(Collection<V> baseList, Class<P> target) {

        if (baseList == null) {
            return null;
        } else if (baseList.isEmpty()) {
            return Lists.newArrayList();
        } else {
            List<P> targetList = Lists.newArrayListWithExpectedSize(baseList.size());
            for (V vo : baseList) {

                targetList.add(convert(vo, target));
            }
            return targetList;
        }

    }


    public static Mapper getMapper() {
        return mapper;
    }

    public static void setMapper(Mapper mapper) {
        DozerHelper.mapper = mapper;
    }
}
