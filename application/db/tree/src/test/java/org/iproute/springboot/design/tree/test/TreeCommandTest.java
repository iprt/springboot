package org.iproute.springboot.design.tree.test;

import com.beust.jcommander.JCommander;
import org.iproute.springboot.design.tree.utils.TreeNodeCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * TreeCommandTest
 *
 * @author tech@intellij.io
 * @since 2022/4/20
 */
public class TreeCommandTest {

    @Test
    public void testCommand() {
        TreeNodeCommand command = new TreeNodeCommand();
        String commandStr = " --id=123  --type=children --level=2";

        String[] strs = commandStr.trim().split("\\s+");

        JCommander.newBuilder()
                .addObject(command)
                .build()
                .parse(strs);

        assertEquals("query", command.option);
        assertEquals(123L, command.id.longValue());
        assertEquals(1L, command.pid.longValue());
        assertEquals("default_by_command", command.name);
        assertEquals("children", command.type);
        assertEquals(2, command.level.intValue());
        assertEquals(false, command.include);
    }
}
