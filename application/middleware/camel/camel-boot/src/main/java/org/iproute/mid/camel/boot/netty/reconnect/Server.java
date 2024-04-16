package org.iproute.mid.camel.boot.netty.reconnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * NettyServer
 *
 * @author devops@kubectl.net
 * @since 2022/8/15
 */
public class Server {
    private ServerSocket serverSock;

    public Server(int port) throws IOException {
        serverSock = new ServerSocket(port);

        for (; ; ) {
            Socket sock = serverSock.accept();
            System.out.println("socket accepted:" + sock);
            try {
                byte[] buf = new byte[5];

                int n = sock.getInputStream().read(buf);
                System.out.println(new String(buf, 0, n));
                sock.getOutputStream().write("world".getBytes());
                sock.getOutputStream().flush();
                Thread.sleep(1000);
                sock.close();
            } catch (Exception ex) {

            }
        }
    }

    public static void main(String... args) {
        try {
            new Server(34567);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
