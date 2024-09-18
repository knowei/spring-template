package com.liwen.project.common.utils;




import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ma.glasnost.orika.DefaultFieldMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.util.CollectionUtils;


public class MapUtils {
    protected static Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap();
    private static MapperFactory mapperFactory = (new DefaultMapperFactory.Builder()).build();
    public static final MapperFacade MAPPER_FACADE;
    private static Map<String, Map<String, String>> CACHE_FIELD_MAP;

    public MapUtils() {
    }

    public static <S, D> List<D> mapList(Collection<S> source, Class<D> destinationClass) {
        return mapList(source, destinationClass, (Map)null);
    }

    public static <S, D> List<D> mapList(Collection<S> source, Class<D> destinationClass, Map<String, String> fieldMap) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        } else {
            Class clazz = getNotNullOneClass(source);
            if (clazz == null) {
                return Collections.emptyList();
            } else {
                return CollectionUtils.isEmpty(fieldMap) ? MAPPER_FACADE.mapAsList(source, destinationClass) : getFieldMapCacheable(clazz, destinationClass, fieldMap).mapAsList(source, destinationClass);
            }
        }
    }

    private static <S> Class getNotNullOneClass(Collection<S> source) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        } else {
            Iterator<S> iterator = source.iterator();

            while(iterator.hasNext()) {
                Object object = iterator.next();
                if (object != null) {
                    return object.getClass();
                }

                iterator.remove();
            }

            return null;
        }
    }

    public static MapperFacade getFieldMapCacheable(Class soureClass, Class destClass, Map<String, String> fieldMap) {
        String key = destClass.getCanonicalName() + "_" + soureClass.getCanonicalName();
        if (!CollectionUtils.isEmpty(fieldMap)) {
            key = key + "_" + fieldMap.hashCode();
        }

        MapperFacade mapperFacade = (MapperFacade)CACHE_MAPPER_FACADE_MAP.get(key);
        if (mapperFacade != null) {
            return mapperFacade;
        } else {
            MapperFactory factory = (new DefaultMapperFactory.Builder()).build();
            ClassMapBuilder classMapBuilder = factory.classMap(soureClass, destClass);
            if (!CollectionUtils.isEmpty(fieldMap)) {
                fieldMap.forEach(classMapBuilder::field);
            }

            classMapBuilder.byDefault(new DefaultFieldMapper[0]).register();
            mapperFacade = factory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(key, mapperFacade);
            return mapperFacade;
        }
    }

    public static <D> D map(Object source, Class<D> destinationClass) {
        return (D) map(source, destinationClass, (Map)null);
    }

    public static <D> D map(Object source, Class<D> destinationClass, Map<String, String> fieldMap) {
        if (source == null) {
            return null;
        } else {
            return CollectionUtils.isEmpty(fieldMap) ? MAPPER_FACADE.map(source, destinationClass) : getFieldMapCacheable(source.getClass(), destinationClass, fieldMap).map(source, destinationClass);
        }
    }

    public static <S, D> MapperFacade getMapperFacade(Class<S> sourceClass, Class<D> destinationClass, Map<String, String> fieldMap) {
        if (!CollectionUtils.isEmpty(fieldMap)) {
            ClassMapBuilder<S, D> sdClassMapBuilder = mapperFactory.classMap(sourceClass, destinationClass);
            Iterator var4 = fieldMap.keySet().iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                sdClassMapBuilder.field(key, (String)fieldMap.get(key));
            }

            sdClassMapBuilder.byDefault(new DefaultFieldMapper[0]).register();
        }

        return mapperFactory.getMapperFacade();
    }

    static {
        MAPPER_FACADE = mapperFactory.getMapperFacade();
        CACHE_FIELD_MAP = new ConcurrentHashMap();
    }
}
