//Test.java
package com.nanjing.weather.demo;

import org.springframework.context.annotation.Bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Method metd = null;
        String fdname = null;
        // 添加两个测试数据。。。
        List list = new ArrayList();
        list.add(new demo("张三", "女"));
        list.add(new demo("李四", "男"));
        for(Object object:list){
            demo demo = (demo) object;
            System.out.println(demo.getName()+"\t"+ demo.getSex());
        }
        /*try {
            // 遍历集合
            for (Object object : list) {
                Class clazz = object.getClass();// 获取集合中的对象类型
                Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
                for (Field field : fds) {// 遍历该数组
                    fdname = field.getName();// 得到字段名，

                    metd = clazz.getMethod("get" + change(fdname), null);// 根据字段名找到对应的get方法，null表示无参数

                    if ("name".equals(fdname) && metd != null) {// 比较是否在字段数组中存在name字段，如果不存在短路，如果存在继续判断该字段的get方法是否存在，同时存在继续执行
                        Object name = metd.invoke(object, null);// 调用该字段的get方法
                        System.out.print("姓名:" + name);// 输出结果
                    }
                    if ("sex".equals(fdname) && metd != null) {// 同上
                        Object sex = metd.invoke(object, null);
                        System.out.println("\t性别:" + sex);
                    }
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    /**
     * @param src
     *            源字符串
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
     */
    public static String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }
}


