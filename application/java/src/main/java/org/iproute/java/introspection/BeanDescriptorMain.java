package org.iproute.java.introspection;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * BeanDescriptorMain
 *
 * @author zhuzhenjie
 * @since 5/22/2023
 */
public class BeanDescriptorMain {
    public static void main(String[] args) throws IntrospectionException {

        // 不内省父类的信息,第二个参数stopClass代表从stopClass开始往上的父类不再内省
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);


        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();


        Class<?> beanClass = beanDescriptor.getBeanClass();
        System.out.println("beanClass = " + beanClass);

        String name = beanDescriptor.getName();
        System.out.println("name = " + name);

        String displayName = beanDescriptor.getDisplayName();
        System.out.println("displayName = " + displayName);

        String shortDescription = beanDescriptor.getShortDescription();
        System.out.println("shortDescription = " + shortDescription);

        Class<?> customizerClass = beanDescriptor.getCustomizerClass();
        System.out.println("customizerClass = " + customizerClass);

    }
}
