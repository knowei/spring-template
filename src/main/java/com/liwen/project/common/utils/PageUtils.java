package com.liwen.project.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwen.project.common.response.PageResponse;
import ma.glasnost.orika.MapperFacade;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PageUtils {
    public PageUtils() {
    }

    public static <T> PageResponse<T> convertPageResponse(Page<T> source) {
        int pageNo = (int)source.getCurrent();
        int pageSize = (int)source.getSize();
        return PageResponse.of(pageNo, pageSize, (int)source.getTotal(), source.getRecords());
    }

    public static <S, T> PageResponse<T> convertPageResponse(PageResponse<S> source, Class<T> tClass, Map<String, String> fieldMap) {
        List<S> sourceData = source.getData();
        if (CollectionUtils.isEmpty(sourceData)) {
            return PageResponse.of(source.getPageNo(), source.getPageSize(), source.getTotalCount(), Collections.emptyList());
        } else {
            Class sourceClass = sourceData.get(0).getClass();
            MapperFacade mapperFacade = null;
            if (!CollectionUtils.isEmpty(fieldMap)) {
                mapperFacade = MapUtils.getMapperFacade(sourceClass, tClass, fieldMap);
            }

            return convertPageResponseWithMapperFacade(source, tClass, mapperFacade);
        }
    }

    public static <S, T> PageResponse<T> convertPageResponse(PageResponse<S> source, Class<T> tClass) {
        return convertPageResponse(source, tClass, (Map)null);
    }

    public static <S, T> PageResponse<T> convertPageResponseWithMapperFacade(PageResponse<S> source, Class<T> tClass, MapperFacade mapperFacade) {
        List<S> sourceData = source.getData();
        if (CollectionUtils.isEmpty(sourceData)) {
            return PageResponse.of(source.getPageNo(), source.getPageSize(), source.getTotalCount(), Collections.emptyList());
        } else {
            List list;
            if (mapperFacade == null) {
                list = MapUtils.MAPPER_FACADE.mapAsList(sourceData, tClass);
            } else {
                list = mapperFacade.mapAsList(sourceData, tClass);
            }

            return PageResponse.of(source.getPageNo(), source.getPageSize(), source.getTotalCount(), list);
        }
    }
}
