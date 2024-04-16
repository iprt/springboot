package org.iproute.mid.client.es.config;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Properties;

/**
 * Esconfig
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
@Getter
@ToString
public class EsConfig {
    private static final EsConfig config = new EsConfig();
    private String host;
    private int port;
    private String scheme;

    private EsConfig() {
    }

    public static EsConfig get() {
        return config;
    }

    static {
        ClassPathResource cpr = new ClassPathResource("es-config.properties");

        try {
            Properties p = new Properties();
            p.load(cpr.getStream());

            String host1 = p.getProperty("host");
            if (StrUtil.isBlank(host1)) {
                host1 = "127.0.0.1";
            }

            String port1 = p.getProperty("port");
            if (StrUtil.isBlank(port1)) {
                port1 = "9200";
            }

            String scheme1 = p.getProperty("scheme");
            if (StrUtil.isBlank(scheme1)) {
                scheme1 = "9200";
            }

            config.host = host1;
            config.port = Integer.parseInt(port1);
            config.scheme = scheme1;
        } catch (Exception e) {
            // do nothing
        }
    }
}
