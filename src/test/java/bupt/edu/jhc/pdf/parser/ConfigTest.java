package bupt.edu.jhc.pdf.parser;

import bupt.edu.jhc.pdf.parser.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;


/**
 * @Description: 配置信息测试类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
@Slf4j
public class ConfigTest {
    @Test
    public void testGetClientCredentials() {
        var clientCredentials = ConfigFactory.getClientCredentials();
        Assert.assertNotNull(clientCredentials);
        log.debug("id: {}", clientCredentials.getId());
        log.debug("secret: {}", clientCredentials.getSecret());
    }

    @Test
    public void testGetGrobidClientConfig() {
        var grobidClientConfig = ConfigFactory.getGrobidClientConfig();
        Assert.assertNotNull(grobidClientConfig);
        log.debug("serverHost: {}", grobidClientConfig.getServerHost());
        log.debug("serverPort: {}", grobidClientConfig.getServerPort());
        log.debug("sleepTime: {}", grobidClientConfig.getSleepTime());
    }
}
