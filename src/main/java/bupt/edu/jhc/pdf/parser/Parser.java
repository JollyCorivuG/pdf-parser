package bupt.edu.jhc.pdf.parser;

import bupt.edu.jhc.pdf.parser.core.extraction.adobe.AdobeProcess;
import bupt.edu.jhc.pdf.parser.core.extraction.grobid.GrobidProcess;
import bupt.edu.jhc.pdf.parser.core.extraction.spire.SpireProcess;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: PDF 解析
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
@Slf4j
public class Parser {
    public static void main(String[] args) {
        var dir = "G:\\PDF Data";
        AdobeProcess.exec(dir);
        GrobidProcess.exec(dir);
        SpireProcess.exec(dir);
    }
}
