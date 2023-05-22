package org.iproute.java.introspection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

/**
 * MethodDescriptorMain
 *
 * @author zhuzhenjie
 * @since 5/22/2023
 */
public class MethodDescriptorMain {
    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);

        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();

        for (MethodDescriptor methodDescriptor : methodDescriptors) {
            String name = methodDescriptor.getName();
            System.out.println("name = " + name);

            String displayName = methodDescriptor.getDisplayName();
            System.out.println("displayName = " + displayName);

            Method method = methodDescriptor.getMethod();
            System.out.println("method = " + method);


        }

    }
}
