package org.iproute.mid.camel.boot.netty.attr;

import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * NetttyChannel
 *
 * @author devops@kubectl.net
 * @since 4/29/2023
 */
@Builder
@Data
public class NettyChannel {
    public static final DateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private String name;
    private Date date;

    @Override
    public String toString() {
        return "NettyChannel{" +
                "name='" + name + '\'' +
                ", date=" + FMT.format(date) +
                '}';
    }
}
