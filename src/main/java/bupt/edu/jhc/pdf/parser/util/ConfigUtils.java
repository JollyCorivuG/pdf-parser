package bupt.edu.jhc.pdf.parser.util;

import cn.hutool.setting.dialect.Props;

/**
 * @Description: 配置工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
public class ConfigUtils {
    private static final String DEFAULT_CONFIG_FILE_NAME = "config.properties";

    /**
     * 加载配置
     *
     * @param configFileName 配置文件名
     * @param configClass 配置类
     * @param prefix 配置前缀
     * @return 配置对象
     */
    public static <T> T loadConfig(String configFileName, Class<T> configClass, String prefix) {
        var props = new Props(configFileName);
        return props.toBean(configClass, prefix);
    }

    // 加载默认配置
    public static <T> T loadConfig(Class<T> configClass, String prefix) {
        return loadConfig(DEFAULT_CONFIG_FILE_NAME, configClass, prefix);
    }
}
