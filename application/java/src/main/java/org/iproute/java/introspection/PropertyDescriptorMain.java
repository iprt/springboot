package org.iproute.java.introspection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * PropertyDescriptorMain
 *
 * @author zhuzhenjie
 * @since 5/22/2023
 */
public class PropertyDescriptorMain {
    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor pd : propertyDescriptors) {
            String name = pd.getName();
            System.out.println("name = " + name);

            Class<?> propertyType = pd.getPropertyType();
            System.out.println("propertyType = " + propertyType);

            Method readMethod = pd.getReadMethod();
            System.out.println("readMethod = " + readMethod);

            Method writeMethod = pd.getWriteMethod();
            System.out.println("writeMethod = " + writeMethod);

            System.out.println();

        }

    }
}

