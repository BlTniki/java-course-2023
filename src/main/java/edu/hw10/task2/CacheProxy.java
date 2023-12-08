package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.mapdb.DBMaker;

public final class CacheProxy {
    private CacheProxy() {
    }

    @SuppressWarnings("unchecked")
    public static <T> @NotNull T create(@NotNull T target, @NotNull Class<T> clazz) {
        final Map<Method, Map<MethodArgs, Object>> methodsToCache = new HashMap<>();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Cache.class)) {
                Map<MethodArgs, Object> cache;
                if (method.getAnnotation(Cache.class).persist()) {
                    cache = (Map<MethodArgs, Object>) DBMaker.fileDB("%s_cache.db".formatted(method.getName())).make().hashMap("cache").createOrOpen();
                } else {
                    cache = new HashMap<>();
                }
                methodsToCache.put(method, cache);
            }
        }

        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class<?>[] {clazz},
            new CachingHandler(target, methodsToCache)
        );
    }

    private final static class CachingHandler implements InvocationHandler {
        private final Object target;
        private final Map<Method, Map<MethodArgs, Object>> methodsToCache;

        CachingHandler(Object target, Map<Method, Map<MethodArgs, Object>> methodsToCache) {
            this.target = target;
            this.methodsToCache = methodsToCache;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!methodsToCache.containsKey(method)) {
                return method.invoke(target, args);
            }
            return methodsToCache.get(method).computeIfAbsent(MethodArgs.of(method, args), (k) -> {
                try {
                    return method.invoke(target, k.getArgs());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
