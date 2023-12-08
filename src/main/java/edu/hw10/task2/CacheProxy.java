package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.h2.mvstore.MVStore;
import org.jetbrains.annotations.NotNull;

public final class CacheProxy {
    private CacheProxy() {
    }

    @SuppressWarnings("unchecked")
    public static <T> @NotNull T create(@NotNull T target, @NotNull Class<T> clazz) {
        final Map<String, Map<MethodArgs, Object>> methodsToCache = new HashMap<>();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Cache.class)) {
                Map<MethodArgs, Object> cache;
                if (method.getAnnotation(Cache.class).persist()) {
                    MVStore s = MVStore.open("%s_%s_cache.db".formatted(clazz.getName(), method.getName()));
                    cache = s.openMap("%s_%s_cache".formatted(clazz.getName(), method.getName()));
                } else {
                    cache = new HashMap<>();
                }
                methodsToCache.put(method.getName(), cache);
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
        private final Map<String, Map<MethodArgs, Object>> methodsToCache;

        CachingHandler(Object target, Map<String, Map<MethodArgs, Object>> methodsToCache) {
            this.target = target;
            this.methodsToCache = methodsToCache;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!methodsToCache.containsKey(method.getName())) {
                return method.invoke(target, args);
            }
            return methodsToCache.get(method.getName()).computeIfAbsent(MethodArgs.of(method, args), (k) -> {
                try {
                    return method.invoke(target, k.getArgs());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
