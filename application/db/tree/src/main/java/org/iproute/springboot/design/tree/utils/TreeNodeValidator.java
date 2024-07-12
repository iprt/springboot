package org.iproute.springboot.design.tree.utils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * TreeNodeValidator
 *
 * @author tech@intellij.io
 * @since 2022/4/20
 */
public class TreeNodeValidator {

    public static class Option implements IParameterValidator {

        public static final String ADD = "add";
        public static final String QUERY = "query";
        public static final String REMOVE = "remove";

        static Set<String> types = Sets.newHashSet(ADD, QUERY, REMOVE);

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!types.contains(value)) {
                throw new ParameterException("option: " + String.join(",", types));
            }
        }
    }

    public static class Id implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            long id = Long.parseLong(value);
            if (id <= 0) {
                throw new ParameterException("id must > 0");
            }
        }
    }

    public static class Type implements IParameterValidator {

        public static final String SELF = "self";
        public static final String CHILDREN = "children";
        public static final String PARENT = "parent";

        static Set<String> types = Sets.newHashSet(SELF, CHILDREN, PARENT);

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!types.contains(value)) {
                throw new ParameterException("type: " + String.join(",", types));
            }
        }

    }

    public static class Include implements IParameterValidator {
        static Set<String> types = Sets.newHashSet(
                String.valueOf(true), String.valueOf(false)
        );

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!types.contains(value)) {
                throw new ParameterException("include: " + String.join(",", types));
            }
        }
    }

    public static class Level implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            long level = Long.parseLong(value);
            if (level < 0) {
                throw new ParameterException("level must >= 0");
            }
        }
    }

}
