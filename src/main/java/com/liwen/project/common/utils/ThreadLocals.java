package com.liwen.project.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程变量
 */
public class ThreadLocals {
    protected static final TransmittableThreadLocal<Map<String, Object>> threadContext = new MapThreadLocal();
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocals.class);

    private ThreadLocals() {
    }

    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    public static Object get(String key) {
        return getContextMap().get(key);
    }

    protected static Map<String, Object> getContextMap() {
        return (Map)threadContext.get();
    }

    public static void reset() {
        getContextMap().clear();
    }

    private static class MapThreadLocal extends TransmittableThreadLocal<Map<String, Object>> {
        private MapThreadLocal() {
        }

        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>() {
                private static final long serialVersionUID = 3637958959138295593L;

                public Object put(String key, Object value) {
                    if (ThreadLocals.LOGGER.isDebugEnabled()) {
                        if (this.containsKey(key)) {
                            ThreadLocals.LOGGER.debug("Overwritten attribute to thread context: {} = {}", key, value);
                        } else {
                            ThreadLocals.LOGGER.debug("Added attribute to thread context: {} = {}", key, value);
                        }
                    }

                    return super.put(key, value);
                }
            };
        }
    }
}

