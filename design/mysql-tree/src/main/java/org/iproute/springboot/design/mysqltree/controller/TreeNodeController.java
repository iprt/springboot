package org.iproute.springboot.design.mysqltree.controller;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.mysqltree.controller.model.req.InitReq;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.iproute.springboot.design.mysqltree.model.req.TreeNodeReq;
import org.iproute.springboot.design.mysqltree.service.FolderTreeNodeInit;
import org.iproute.springboot.design.mysqltree.service.TreeNodeCmdService;
import org.iproute.springboot.design.mysqltree.service.TreeNodeService;
import org.iproute.springboot.design.mysqltree.utils.TreeNodeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TreeNodeController
 *
 * @author winterfell
 * @since 2022/4/19
 */
@RestController
@RequestMapping("/treeNode")
public class TreeNodeController {

    private final Lock initLock = new ReentrantLock();
    @Autowired
    private TreeNodeService treeNodeService;

    @Autowired
    private FolderTreeNodeInit folderTreeNodeInit;
    @Autowired
    private TreeNodeCmdService treeNodeCmdService;

    @PostMapping("/command")
    public List<TreeNode> command(@RequestBody TreeNodeReq req) {
        if (StringUtils.isBlank(req.getCommand())) {
            throw new IllegalArgumentException("command cannot be null");
        }

        String cmdStr = req.getCommand();

        if (!cmdStr.startsWith(TreeNodeCommand.PREFIX)) {
            throw new IllegalArgumentException("command does not exist!");
        }

        TreeNodeCommand cmd = new TreeNodeCommand();
        String[] strs = cmdStr.substring(TreeNodeCommand.PREFIX.length()).trim().split("\\s+");

        JCommander.newBuilder()
                .addObject(cmd)
                .build()
                .parse(strs);

        initLock.lock();
        try {
            return treeNodeCmdService.command(
                    cmd.option, cmd.type,
                    cmd.id, cmd.pid, cmd.name,
                    cmd.include, cmd.level
            );
        } finally {
            initLock.unlock();
        }
    }


    @PostMapping("/nodeInfo")
    public TreeNode nodeInfo(@RequestBody TreeNode node) {
        return treeNodeService.nodeInfo(node.getId());
    }

    @PostMapping("/levelNodes")
    public List<TreeNode> levelNodes(@RequestBody TreeNode node) {
        return treeNodeService.levelNodes(node.getLevel());
    }

    @SuppressWarnings("all")
    @PostMapping("/init")
    public String init(@RequestBody InitReq req) {

        if (StringUtils.isBlank(req.getPath())) {
            return "path is blank";
        }
        String path = req.getPath();

        new Thread(() -> {
            initLock.lock();
            try {
                folderTreeNodeInit.init(path);
            } finally {
                initLock.unlock();
            }
        }, "INIT").start();

        return "init in thread";
    }

}
