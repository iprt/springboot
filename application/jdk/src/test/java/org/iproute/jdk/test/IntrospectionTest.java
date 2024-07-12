package org.iproute.jdk.test;

import lombok.extern.slf4j.Slf4j;
import org.iproute.jdk.introspection.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * IntrospectionTest
 *
 * @author tech@intellij.io
 * @since 2023/7/28
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class IntrospectionTest {

    private static BeanInfo beanInfo;

    @BeforeAll
    public static void beforeAll() {
        try {
            beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        } catch (IntrospectionException e) {
            log.error("", e);
        }
    }


    @Test
    @Order(1)
    public void testPropertyDescriptor() {
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            String name = pd.getName();
            Method readMethod = pd.getReadMethod();
            Method writeMethod = pd.getWriteMethod();
            log.info("name = {} | readMethod = {} | writeMethod = {}", name, readMethod, writeMethod);
        }
    }


    @Test
    @Order(2)
    public void testBeanDescription() {

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();

        log.info("BeanDescriptor beanClass = {}", beanDescriptor.getBeanClass());

        log.info("BeanDescriptor name = {}", beanDescriptor.getName());

        log.info("BeanDescriptor displayName = {}", beanDescriptor.getDisplayName());

        log.info("BeanDescriptor shortDescription = {}", beanDescriptor.getShortDescription());

        log.info("BeanDescriptor customizerClass = {}", beanDescriptor.getCustomizerClass());

    }


    @Test
    @Order(3)
    public void testMethodDescription() {
        MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
        for (MethodDescriptor md : methodDescriptors) {
            log.info("name = {} | displayName = {} | method = {}", md.getName(), md.getDisplayName(), md.getMethod());
        }
    }


}
