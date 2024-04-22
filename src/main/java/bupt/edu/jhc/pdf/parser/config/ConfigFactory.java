package bupt.edu.jhc.pdf.parser.config;

import bupt.edu.jhc.pdf.parser.util.ConfigUtils;

/**
 * @Description: 配置工厂
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
public class ConfigFactory {
    private static volatile ClientCredentials clientCredentials;

    /**
     * 获取 pdfservices-api-credentials 配置信息
     *
     * @return
     */
    public static ClientCredentials getClientCredentials() {
        if (clientCredentials == null) {
            synchronized (ConfigFactory.class) {
                if (clientCredentials == null) {
                    clientCredentials = ConfigUtils.loadConfig(ClientCredentials.class, ConfigKeys.CLIENT_CREDENTIAL_PREFIX);
                }
            }
        }
        return clientCredentials;
    }
}
