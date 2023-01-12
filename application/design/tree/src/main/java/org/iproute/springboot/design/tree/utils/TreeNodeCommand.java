package org.iproute.springboot.design.tree.utils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * TreeNodeCommand
 *
 * @author winterfell
 * @since 2022/4/20
 */
@Parameters(separators = "=")
public class TreeNodeCommand {

    public static final String PREFIX = "treeNode";

    @Parameter(names = {"--option"}, description = "TreeNode Id",
            validateWith = TreeNodeValidator.Option.class)
    public String option = TreeNodeValidator.Option.QUERY;

    @Parameter(names = "--id", description = "TreeNode Id",
            validateWith = TreeNodeValidator.Id.class)
    public Long id = 1L;

    @Parameter(names = "--pid", description = "TreeNode Id",
            validateWith = TreeNodeValidator.Id.class)
    public Long pid = 1L;

    @Parameter(names = "--name", description = "TreeNode Name")
    public String name = "default_by_command";

    @Parameter(names = "--type", description = "Query type",
            validateWith = TreeNodeValidator.Type.class
    )
    public String type = TreeNodeValidator.Type.SELF;

    @Parameter(names = "--include", description = "Include or Exclude", arity = 1,
            validateWith = TreeNodeValidator.Include.class)
    public Boolean include = false;

    @Parameter(names = "--level", description = "Query level",
            validateWith = TreeNodeValidator.Level.class)
    public Integer level = -1;

}
