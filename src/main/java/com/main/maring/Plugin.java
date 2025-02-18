package com.main.maring;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Plugin {
	
    // detect 函数，扫描当前目录下的所有 .jar 文件并执行第一个带有 @MaringPlugin 注解的方法
    public static void detect() {
        try {
            // 获取当前目录
            File currentDirectory = new File(".");

            // 扫描目录下的所有 .jar 文件
            File[] jarFiles = currentDirectory.listFiles((dir, name) -> name.endsWith(".jar"));

            if (jarFiles != null) {
                for (File jarFile : jarFiles) {
                    // 加载 .jar 文件
                    URL jarUrl = jarFile.toURI().toURL();
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});

                    // 扫描 .jar 文件中的类，并执行第一个找到的带有 @MaringPlugin 注解的方法
                    if (scanJarForMaringPlugin(classLoader)) {
                        // 找到并执行了一个方法后，跳出当前 .jar 文件的处理
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 扫描 .jar 文件中的类，查找带有 @MaringPlugin 注解的方法并执行，第一个找到的就执行
    private static boolean scanJarForMaringPlugin(URLClassLoader classLoader) {
        try {
            // 假设我们知道某个 jar 包里包含某些类，示例中直接列出要加载的类
            // 在实际应用中，可以动态扫描 .jar 文件中的所有类
            Class<?>[] classes = {
                Class.forName("com.main.maring.SomePlugin", true, classLoader), // 假设这里是从 jar 中加载的类
                Class.forName("com.main.maring.AnotherPlugin", true, classLoader) // 更多类
            };

            // 检查每个类中的方法是否带有 @MaringPlugin 注解
            for (Class<?> clazz : classes) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(MaringPlugin.class)) {
                        // 执行带有 @MaringPlugin 注解的方法
                        method.setAccessible(true); // 如果方法是 private，设置为可访问
                        method.invoke(clazz.getDeclaredConstructor().newInstance());
                        
                        // 找到第一个方法后，返回 true，表示不再继续处理当前 jar 文件
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // 如果没有找到带有 @MaringPlugin 注解的方法，返回 false
    }
    
}
