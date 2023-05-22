package org.iproute.src.learn.generic;

import org.springframework.context.support.GenericApplicationContext;

/**
 * GenericMain
 *
 * @author zhuzhenjie
 * @since 5/22/2023
 */
public class GenericMain {
    public static void main(String[] args) {

        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();

        genericApplicationContext.refresh();

        System.out.println();

    }
}
