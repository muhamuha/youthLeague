package com.wzxc.common.utils;

import org.springframework.boot.test.json.GsonTester;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PathUtils {

    /**
     * 获取项目根路径
     * 例：G://MybatisPlus
     */
    public static String rootPath(){
        String classPath = System.getProperty("user.dir");
        return classPath;
    }

    private static String packPath(String packName){
        String path = "";
        // 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
        if(packName.indexOf(".") < 0) path = packName + "/";
        else{ // 否则按照包名的组成部分，将包名转换为路径
            int start = 0, end = 0;
            end = packName.indexOf(".");
            while(end != -1){
                path = path + packName.substring(start, end) + "/";
                start = end + 1;
                end = packName.indexOf(".", start);
            }
            path = path + packName.substring(start) + "/";
        }
        return path;
    }

    private static URL getResourceURL(Class clazz){
        // 检查用户传入的参数是否为空
        if(clazz == null)
            throw new java.lang.IllegalArgumentException( "参数不能为空！");
        ClassLoader loader = clazz.getClassLoader();
        // 获得类的全名，包括包名
        String clsName = clazz.getName() + ".class";
        // 获得传入参数所在的包
        Package pack = clazz.getPackage();
        // 如果不是匿名包，将包名转化为路径
        String path = "";
        if(pack != null){
            String packName = pack.getName();
            // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if(packName.startsWith( "java.") || packName.startsWith( "javax."))
                throw new java.lang.IllegalArgumentException("不要传送系统类！");
            // 在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            path = packPath(packName);
        }
        // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        return url;
    }

    private static String getPackPath(Class clazz){
        // 检查用户传入的参数是否为空
        if(clazz == null)
            throw new java.lang.IllegalArgumentException( "参数不能为空！");
        ClassLoader loader = clazz.getClassLoader();
        // 获得传入参数所在的包
        Package pack = clazz.getPackage();
        // 如果不是匿名包，将包名转化为路径
        String path = "";
        if(pack != null){
            String packName = pack.getName();
            // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if(packName.startsWith( "java.") || packName.startsWith( "javax."))
                throw new java.lang.IllegalArgumentException("不要传送系统类！");
            path = packPath(packName);
        }
        return path;
    }

    /**
     * 获取程序当前路径
     * 例：/C:/javaProject/MUHAMUHA-framework-server/util-common/target/classes/com/wzxc/common/
     */
    public static String currentPath(Class clazz){
        URL url = getResourceURL(clazz);
        // 从URL对象中获取路径信息
        String realPath = url.getPath();
        String clsName = realPath.split("/")[realPath.split("/").length - 1];
        //去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if(pos > - 1) realPath = realPath.substring(pos + 5);
        // 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(clsName);
        realPath = realPath.substring(0, pos);
        //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if(realPath.endsWith( "!"))
            realPath = realPath.substring(0, realPath.lastIndexOf( "/"));
       /*------------------------------------------------------------
       ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径
        中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要
        的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的
        中文及空格路径
      -------------------------------------------------------------*/
        try{
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch(Exception e){ throw new RuntimeException(e);}
        return realPath;
    }

    /**
     * 获取程序当前开发路径
     * 例：/C:/javaProject/MUHAMUHA-framework-server/util-common/src/com/wzxc/common/
     */
    public static String currentDevPath(Class clazz){
        String realPath = currentPath(clazz);
        String devPath = realPath.replace("target/classes/", "src/main/java/");
        return devPath;
    }

    /**
     * 获取程序当前开发根路径
     * 例：/C:/javaProject/MUHAMUHA-framework-server/util-common/src/com/wzxc/common/
     */
    public static String currentDevRootPath(Class clazz){
        String realPath = currentPath(clazz);
        String devPath = realPath.split("target/classes")[0] + "src/main/java/";
        return devPath;
    }

    /**
     * 获取程序当前相对的开发路径
     * 例：/C:/javaProject/MUHAMUHA-framework-server/busi-service/src/main/java/com/wzxc/busi
     * @param backLevel: 返回几层，1表示上一层目录
     */
    public static String relativeDevPath(Class clazz, int backLevel){
        String devPath = currentDevPath(clazz);
        while(backLevel-- > 0){
            if(devPath.equals("/") || !devPath.contains("/")){
                return devPath;
            }
            devPath = devPath.substring(0, devPath.length() - 1);
            devPath = devPath.substring(0, devPath.lastIndexOf("/") + 1);
        }
        return devPath;
    }

    /**
     * 获取程序开发的资源根路径
     * 例：/C:/javaProject/MUHAMUHA-framework-server/busi-service/src/main/java/resources/
     */
    public static String currentDevResourceRootPath(Class clazz){
        String realPath = currentPath(clazz);
        String resourcePath = realPath.split("target/classes")[0] + "src/main/resources/";
        return resourcePath;
    }


    public static void main(String[] args) throws IOException {
        String path = currentDevRootPath(com.wzxc.common.utils.PathUtils.class);
        getPackPath(com.wzxc.common.utils.PathUtils.class);
    }
}
