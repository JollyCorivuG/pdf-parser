package bupt.edu.jhc.pdf.parser.core.handler.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 解析 xml  (基于责任链模式)
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/24
 */
public class XmlHandlerChain {
    private static final List<AbstractXmlHandler> handlerChain = new ArrayList<>();

    /**
     * 添加 handler
     *
     * @param handler
     */
    public static void addHandler(AbstractXmlHandler handler) {
        handlerChain.add(handler);
    }

    /**
     * 解析 xml
     *
     * @param xmlPath
     */
    public static void put(String xmlPath) {
        for (AbstractXmlHandler handler : handlerChain) {
            handler.parse(xmlPath);
        }
    }
}
