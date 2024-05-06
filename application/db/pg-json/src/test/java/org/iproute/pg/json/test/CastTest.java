package org.iproute.pg.json.test;

import org.iproute.pg.json.utils.CastUtils;
import org.junit.jupiter.api.Test;

/**
 * CastTest
 *
 * @author devops@kubectl.net
 */
public class CastTest {

    @Test
    public void testCastChildToParent() {
        Child child = new Child("child", 1);
        Parent parent = CastUtils.cast(child, Parent.class);
        System.out.println(parent.name);
    }

    @Test
    public void testCastParentToChild() {
        Parent parent = new Child("parent", 123);
        Child child = CastUtils.cast(parent, Child.class);

        System.out.println(child.id);
    }

    public static class Parent {
        protected String name;

        public Parent(String name) {
            this.name = name;
        }
    }

    public static class Child extends Parent {
        protected int id;

        public Child(String name) {
            super(name);
        }

        public Child(String name, int id) {
            this(name);
            this.id = id;
        }

    }

}
