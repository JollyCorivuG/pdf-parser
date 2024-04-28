package bupt.edu.jhc.pdf.parser.core.extraction.spire;

import bupt.edu.jhc.pdf.parser.util.FileUtils;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Description: 从 PDF 文件中提取图像
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/24
 */
@Slf4j
public class ExtractFiguresFromPDF {
    private static String getFiguresStorageDir(String pdfFilePath) {
        String dir = "output/" + FileUtils.getNameFromPath(pdfFilePath, false) + "/figures";
        File outputDir = new File(dir);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                log.error("Failed to create output directory {}", outputDir.getPath());
            }
        }
        return dir;
    }

    public static void exec(String pdfFilePath) {
        // 创建一个 PdfDocument 实例
        var doc = new PdfDocument();

        // 加载 PDF 示例文档
        doc.loadFromFile(pdfFilePath);

        var imageNumber = 0;
        for (var page : (Iterable<PdfPageBase>) doc.getPages()) {
            var images = page.extractImages();
            if (images == null) {
                continue;
            }
            //从所给页面提取图片
            for (BufferedImage image : page.extractImages()) {

                // 指定输出文档的路径和名称
                File output = new File(getFiguresStorageDir(pdfFilePath) + "/" + String.format("figure-%d.png", imageNumber));

                // 将图像另存为.png文件
                try {
                    ImageIO.write(image, "PNG", output);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                imageNumber++;
            }
        }
    }
}
