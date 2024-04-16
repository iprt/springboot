package org.iproute.springboot.design.tree.controller;

import com.beust.jcommander.JCommander;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.tree.model.req.InitReq;
import org.iproute.springboot.design.tree.model.req.TreeNodeReq;
import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.iproute.springboot.design.tree.service.FolderTreeNodeInit;
import org.iproute.springboot.design.tree.service.TreeNodeCmdService;
import org.iproute.springboot.design.tree.service.TreeNodeService;
import org.iproute.springboot.design.tree.utils.TreeNodeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * @author devops@kubectl.net
 */
@RestController
@RequestMapping("/treeNode")
@Slf4j
public class TreeNodeController {

    private final Lock initLock = new ReentrantLock();

    @Qualifier("mysqlTreeNodeService")
    @Autowired
    private TreeNodeService mysqlTreeNodeService;

    @Qualifier("postgresTreeNodeService")
    @Autowired
    private TreeNodeService postgresTreeNodeService;

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
                    choose(req.getDs()),
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
        return choose(node.getDs()).nodeInfo(node.getId());
    }

    @PostMapping("/levelNodes")
    public List<TreeNode> levelNodes(@RequestBody TreeNode node) {
        return choose(node.getDs()).levelNodes(node.getLevel());
    }


    @PostMapping("/init")
    @SuppressWarnings("all")
    public String init(@RequestBody InitReq req) {

        Long basePid = req.getBasePid();
        String path = req.getPath();

        new Thread(() -> {
            initLock.lock();
            try {
                folderTreeNodeInit.init(choose(req.getDs()), basePid, path);
            } finally {
                initLock.unlock();
            }
        }, req.getDs().toLowerCase() + " init").start();

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
                folderTreeNodeInit.logPrintQuery(choose(req.getDs()), id);
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
        return choose(req.getDs()).addNode(parentNode.getId(), addedNode.getName());
    }

    @PostMapping("/addNodes")
    public List<TreeNode> addNodes(@RequestBody TreeNodeReq req) {
        TreeNode parentNode = req.getParentNode();
        List<TreeNode> addNodes = req.getAddedNodes();
        return choose(req.getDs()).addNodes(parentNode.getId(),
                addNodes.stream().map(TreeNode::getName).collect(Collectors.toList()));
    }

    @PostMapping("/removeNodes")
    public int removeNodes(@RequestBody TreeNodeReq req) {
        return choose(req.getDs()).removeNode(req.getRemovedNode().getId(), req.getInclude());
    }

    @PostMapping("/removeLevel")
    public int removeLevel(@RequestBody TreeNodeReq req) {
        Integer level = req.getRemovedNode().getLevel();
        return choose(req.getDs()).removeLevel(level);
    }


    @PostMapping("/reset")
    public String reset(@RequestBody TreeNodeReq req) {
        String ds = req.getDs();
        choose(ds).reset();
        return "reset " + ds;
    }

    @PostMapping("/resetAll")
    public String reset() {
        mysqlTreeNodeService.reset();
        postgresTreeNodeService.reset();
        return "resetAll";
    }


    private TreeNodeService choose(String ds) {
        if ("mysql".equals(ds)) {
            return mysqlTreeNodeService;
        } else if ("postgres".equals(ds)) {
            return postgresTreeNodeService;
        }
        return mysqlTreeNodeService;
    }

}
