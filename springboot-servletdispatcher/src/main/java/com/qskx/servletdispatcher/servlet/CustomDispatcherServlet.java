package com.qskx.servletdispatcher.servlet;

import com.qskx.servletdispatcher.annotation.CustomController;
import com.qskx.servletdispatcher.annotation.CustomRequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author 111111
 * @date 2018-08-19 09:32
 */
public class CustomDispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    private Map<String, Object> controllerMap = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        //加载配置文件
        doLoadConfig(servletConfig.getInitParameter("contextConfigLocation"));

        //初始化所有相关联的类,扫描用户设定的包下面所有的类
        doScanner(properties.getProperty("scanPackage"));

        //拿到扫描到的类,通过反射机制,实例化,并且放到ioc容器中(k-v  beanName-bean) beanName默认是首字母小写
        doInstance();

        //初始化HandlerMapping(将url和method对应上)
        initHandlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //处理请求
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (handlerMapping.isEmpty()){
            return;
        }
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        uri = uri.replace(contextPath, "").replaceAll("/+", "/");
        if (!this.handlerMapping.containsKey(uri)){
            resp.getWriter().write("404 NOT FOUND!");
            return;
        }

        Method method = this.handlerMapping.get(uri);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];

        
    }

    private void initHandlerMapping() {
        try {
            if (ioc.isEmpty()){
                return;
            }
            for (Map.Entry<String, Object> entry : ioc.entrySet()){
                Class<?> clz = entry.getValue().getClass();
                if (!clz.isAnnotationPresent(CustomController.class)){
                    continue;
                }
                //controller 类上的url
                String baseUrl = "";
                if (clz.isAnnotationPresent(CustomRequestMapping.class)){
                    CustomRequestMapping annotation = clz.getAnnotation(CustomRequestMapping.class);
                    baseUrl = annotation.value();
                }

                //方法上的url
                Method[] methods = clz.getMethods();
                for (Method method : methods){
                    if (!method.isAnnotationPresent(CustomRequestMapping.class)){
                        continue;
                    }
                    CustomRequestMapping methodAnnotation = method.getAnnotation(CustomRequestMapping.class);
                    String subUrl = methodAnnotation.value();

                    String url =(baseUrl+"/"+subUrl).replaceAll("/+", "/");
                    handlerMapping.put(url,method);
                    controllerMap.put(url,clz.newInstance());
                    System.out.println(url+","+method);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void doInstance() {
        if (classNames.isEmpty()){
            return;
        }
        for (String className : classNames){
            //CustomController需要实例化
            try {
                Class<?> clz = Class.forName(className);
                if (clz.isAnnotationPresent(CustomController.class)){
                    ioc.put(toLowerFirstWord(clz.getSimpleName()), clz.newInstance());
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void doScanner(String pakageName) {
        URL url = this.getClass().getClassLoader().getResource(pakageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()){
            if (file.isDirectory()){
                doScanner(pakageName + "." + file.getName());
            } else {
                String className = pakageName + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
    }
    
    /**
     * 把字符串的首字母小写
     * @author 111111
     * @date 2018-08-19 13:34
     * @param name
     * @return 
     * @throws 
     * @since 
    */
    private String toLowerFirstWord(String name){
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }
}
