package bupt.edu.jhc.pdf.parser;

import bupt.edu.jhc.pdf.parser.core.extraction.ExtractTableInfoFromPDF;
import bupt.edu.jhc.pdf.parser.core.handler.UnzipTableInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: PDF 解析
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
@Slf4j
public class Parser {
    public static void main(String[] args) {
        var outputPath = ExtractTableInfoFromPDF.exec("G:\\PDF Data\\高强韧钛合金文献（60篇）\\2022-2024年\\A titanium-nitrogen alloy with ultrahigh strength by ball milling and spark plasma sintering.pdf");
        UnzipTableInfo.exec(outputPath);
    }
}
