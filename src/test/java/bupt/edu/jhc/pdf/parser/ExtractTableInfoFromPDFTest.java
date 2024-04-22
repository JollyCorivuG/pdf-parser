package bupt.edu.jhc.pdf.parser;

import bupt.edu.jhc.pdf.parser.core.extraction.ExtractTableInfoFromPDF;
import bupt.edu.jhc.pdf.parser.core.handler.UnzipTableInfo;
import org.junit.Test;

/**
 * @Description: PDF 提取表格测试类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
public class ExtractTableInfoFromPDFTest {
    @Test
    public void testExtractTableInfoFromPDF() {
        ExtractTableInfoFromPDF.exec("G:\\PDF Data\\高强韧钛合金文献（60篇）\\2022-2024年\\A titanium-nitrogen alloy with ultrahigh strength by ball milling and spark plasma sintering.pdf");
        UnzipTableInfo.exec("output/A titanium-nitrogen alloy with ultrahigh strength by ball milling and spark plasma sintering.pdf");
    }
}
