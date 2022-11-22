package org.iproute.springboot.design.mysqltree.controller;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.iproute.springboot.design.mysqltree.model.req.InitReq;
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
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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


    @PostMapping("/init")
    @SuppressWarnings("all")
    public String init(@RequestBody InitReq req) {

        Long basePid = req.getBasePid();
        String path = req.getPath();

        new Thread(() -> {
            initLock.lock();
            try {
                folderTreeNodeInit.init(basePid, path);
            } finally {
                initLock.unlock();
            }
        }, "INIT").start();

        return "init in thread";
    }

    @PostMapping("/logPrintQuery")
    @SuppressWarnings("all")
    public String query(@RequestBody InitReq req) {
        if (Objects.isNull(req.getId())) {
            return "id is null";
        }
        Long id = req.getId();
        new Thread(() -> {
            initLock.lock();
            try {
                folderTreeNodeInit.logPrintQuery(id);
            } finally {
                initLock.unlock();
            }
        }, "QUERY").start();

        return "query in thread";
    }

    @PostMapping("/addNode")
    public TreeNode addNode(@RequestBody TreeNodeReq req) {
        TreeNode parentNode = req.getParentNode();
        TreeNode addedNode = req.getAddedNode();
        return treeNodeService.addNode(parentNode.getId(), addedNode.getName());
    }

    @PostMapping("/addNodes")
    public List<TreeNode> addNodes(@RequestBody TreeNodeReq req) {
        TreeNode parentNode = req.getParentNode();
        List<TreeNode> addNodes = req.getAddedNodes();
        return treeNodeService.addNodes(parentNode.getId(), addNodes.stream().map(TreeNode::getName).collect(Collectors.toList()));
    }


    @PostMapping("/reset")
    public String reset() {
        treeNodeService.reset();
        return "reset";
    }

}
