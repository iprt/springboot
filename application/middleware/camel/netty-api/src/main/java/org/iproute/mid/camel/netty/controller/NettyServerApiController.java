package org.iproute.mid.camel.netty.controller;

import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.netty.api.NettyServerApi;
import org.iproute.mid.camel.netty.model.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * NettyServerApiController
 *
 * @author devops@kubectl.net
 * @since 2022/12/20
 */
@RestController
public class NettyServerApiController {

    @Autowired
    NettyServerApi nettyServerApi;

    @GetMapping("/startTcp/{port}")
    public boolean start(@PathVariable("port") int port) {
        return nettyServerApi.start(ServerInfo.builder().port(port).build());
    }

    @GetMapping("/stopTcp/{port}")
    public boolean stop(@PathVariable("port") int port) {
        return nettyServerApi.stop(ServerInfo.builder().port(port).build());
    }
}
