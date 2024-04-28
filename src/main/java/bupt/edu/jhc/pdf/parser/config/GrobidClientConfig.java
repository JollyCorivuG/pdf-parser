package bupt.edu.jhc.pdf.parser.config;

import lombok.Data;

/**
 * @Description: grobid client 配置
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/23
 */
@Data
public class GrobidClientConfig {
    private String serverHost;
    private String serverPort;
    private Integer sleepTime;
}
