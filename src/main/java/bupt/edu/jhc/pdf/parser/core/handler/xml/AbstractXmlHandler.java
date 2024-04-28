package bupt.edu.jhc.pdf.parser.core.handler.xml;

import bupt.edu.jhc.pdf.parser.util.FileUtils;

/**
 * @Description: 抽象 xml 解析类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/24
 */
public abstract class AbstractXmlHandler {
    /**
     * 得到相应元素存储目录
     *
     * @param xmlPath
     * @return
     */
    protected String getElementStorageDir(String xmlPath) {
        return FileUtils.getStoreDir(xmlPath) + "/" + elementKey();
    }

    protected abstract String elementKey();

    /**
     * 解析 xml
     *
     * @param xmlPath
     */
    public abstract void parse(String xmlPath);
}
