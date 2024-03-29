/*
 * Copyright (c) 2021 VMware, Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iproute.reactor.netty.document.tcp.server.address;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class MultiAddressApplication {

    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.create();
        DisposableServer server1 = tcpServer
                .host("localhost") //<1>
                .port(8080)        //<2>
                .bindNow();

        DisposableServer server2 = tcpServer
                .host("0.0.0.0") //<3>
                .port(8081)      //<4>
                .bindNow();

        Mono.when(server1.onDispose(), server2.onDispose())
                .block();
    }
}
