package bupt.edu.jhc.pdf.parser;

import bupt.edu.jhc.pdf.parser.core.extraction.spire.ExtractFiguresFromPDF;
import org.junit.Test;

/**
 * @Description: 从 PDF 提取图像测试类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/24
 */
public class ExtractFiguresFromPDFTest {
    @Test
    public void test() {
        ExtractFiguresFromPDF.exec("src/main/resources/A titanium-nitrogen alloy with ultrahigh strength by ball milling and spark plasma sintering.pdf");
    }
}
