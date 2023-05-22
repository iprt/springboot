package org.iproute.java.introspection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Demo
 *
 * @author zhuzhenjie
 * @since 5/22/2023
 */
public class Demo {
    public static void main(String[] args) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();


            for (PropertyDescriptor pd : propertyDescriptors) {
                String name = pd.getName();
                Method readMethod = pd.getReadMethod();
                Method writeMethod = pd.getWriteMethod();

                System.out.println("name = " + name + "\r\n"
                        + "readMethod = " + readMethod + "\r\n"
                        + "writeMethod = " + writeMethod + "\r\n");

            }

            // BeanInfo includeParentBeanInfo = Introspector.getBeanInfo(Person.class);

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }
}
