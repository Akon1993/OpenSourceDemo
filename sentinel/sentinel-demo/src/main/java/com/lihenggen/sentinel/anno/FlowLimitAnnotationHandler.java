package com.lihenggen.sentinel.anno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FlowLimitAnnotationHandler implements Supplier<Map<String, Integer>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlowLimitAnnotationHandler.class);
    private Map<String, Integer> urlQpsMap = new ConcurrentHashMap<>();
    CompletableFuture<Map<String, Integer>> urlQpsMapFuture = CompletableFuture.supplyAsync(this);
    {
        urlQpsMapFuture.thenApplyAsync(x -> {
            initSentinelResource();
            return null;
        });
    }

    private ApplicationContext applicationContext;

    public FlowLimitAnnotationHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public Integer getConfigForSentinelResource(String resource) throws ExecutionException, InterruptedException {
        Map<String, Integer> stringIntegerMap = urlQpsMapFuture.get();
        return stringIntegerMap.get(resource);
    }

    public Set<String> getAllSentinelResource() throws ExecutionException, InterruptedException {
        return urlQpsMapFuture.get().keySet();
    }

    // 初始化需要限流的资源
    public void initSentinelResource() {
        String[] controllerBeanNames = applicationContext.getBeanNamesForAnnotation(FlowLimit.class);
        Set<String> urlBeanNames = new HashSet<>();
        urlBeanNames.addAll(Arrays.asList(controllerBeanNames));
        initRule(urlBeanNames);
    }

    // 初始化限流规则
    private void initRule(Set<String> urlBeanNames) {
        for (String beanName : urlBeanNames) {
            Object bean = applicationContext.getBean(beanName);
            if (bean.getClass().isAnnotationPresent(FlowLimit.class)) {
                FlowLimit flowLimit = bean.getClass().getAnnotation(FlowLimit.class);
                List<Method> declaredMethodsWithRequestAnnotation = getMethodWithAnnotation(Arrays.asList(bean.getClass().getDeclaredMethods()));
                for (Method requestMethod : declaredMethodsWithRequestAnnotation) {
                    RequestMapping requestMapping = requestMethod.getAnnotation(RequestMapping.class);
                    buildMapResource(requestMapping, flowLimit);
                }
            } else {
                buildMethodResource(bean.getClass());
            }
        }
//        Object bean = applicationContext.getBean("testController");
////        Class<?>[] declaredClasses = bean.getClass().getDeclaredClasses();
//        for (Annotation annotation : bean.getClass().getAnnotations()) {
//            System.out.println(annotation.getClass().getName());
//        }
//        FlowLimit flowLimit = bean.getClass().getAnnotation(FlowLimit.class);
////        int flowQps = flowLimit.flowQps();
////        System.out.println(flowQps);
//
//        Method[] methods = bean.getClass().getMethods();
//        for (Method method : methods) {
//            String name = method.getName();
//            System.out.println(name);
//            Annotation[] annotations = method.getAnnotations();
//            for (Annotation annotation : annotations) {
//                Class<? extends Annotation> aClass = annotation.getClass();
//                System.out.println(aClass.getName());
//            }
//        }
    }

    private void buildMethodResource(Class<?> aClass) {
        List<Method> methods = Arrays.stream(aClass.getDeclaredMethods()).filter(md -> md.getName().contains("test")).collect(Collectors.toList());
//        Stream.of(declaredMethods)
//                .filter(declaredMethod -> declaredMethod.isAnnotationPresent(FlowLimit.class) && declaredMethod.isAnnotationPresent(RequestMapping.class))
//                .forEach(d -> {
//                    FlowLimit flowLimit = d.getAnnotation(FlowLimit.class);
//                    RequestMapping requestMapping = d.getAnnotation(RequestMapping.class);
//                    String resource = requestMapping.name();
//                    int flowQps = flowLimit.flowQps();
//                    urlQpsMap.put(resource, flowQps);
//                });

        for (Method method : methods) {
            LOGGER.info("methodName:{}, flowLimit:{}, requestMapping:{}", method.getName(), method.isAnnotationPresent(FlowLimit.class), method.isAnnotationPresent(RequestMapping.class));
            if (method.isAnnotationPresent(FlowLimit.class) && method.isAnnotationPresent(RequestMapping.class)) {
                FlowLimit flowLimit = method.getAnnotation(FlowLimit.class);
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String resource = requestMapping.name();
                int flowQps = flowLimit.flowQps();
                urlQpsMap.put(resource, flowQps);
            } else {
                System.out.println(method);
            }
        }
    }

    private List<Method> getMethodWithAnnotation(List<Method> methods) {
        return methods.stream()
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());
    }

    private void buildMapResource(RequestMapping requestMapping, FlowLimit flowLimit) {
        String[] value = requestMapping.value();
        Arrays.asList(value).forEach(resource -> {
            urlQpsMap.put(resource, flowLimit.flowQps());
        });


    }

    @Override
    public Map<String, Integer> get() {
        return this.urlQpsMap;
    }
}
