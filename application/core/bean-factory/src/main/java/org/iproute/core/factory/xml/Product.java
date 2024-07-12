package org.iproute.core.factory.xml;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Date;

/**
 * Product
 *
 * @author tech@intellij.io
 */
@ToString
@Data
public class Product implements InitializingBean, DisposableBean {

    private String name;

    private Date date;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("before Product.afterPropertiesSet : " + this);
        System.out.println("Product.afterPropertiesSet");
        this.name = "afterPropertiesSet";
    }

    public void myInit() {
        System.out.println("before Product.myInit : " + this);
        System.out.println("Product.myInit");
        this.name = "myInit";
    }

    /**
     * 销毁方法的操作只适用于 `scope="singleton"`
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("Product.destroy");
    }

    public void myDestroy() {
        System.out.println("Product.myDestroy");
    }
}
