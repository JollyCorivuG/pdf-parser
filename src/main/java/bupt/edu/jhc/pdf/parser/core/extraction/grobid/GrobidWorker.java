package bupt.edu.jhc.pdf.parser.core.extraction.grobid;

import bupt.edu.jhc.pdf.parser.core.extraction.grobid.exceptions.GrobidTimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @Description: grobid worker
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/23
 */
@Slf4j
@AllArgsConstructor
public class GrobidWorker {
    private final File pdfFile; // 每个 worker 对应的 PDF 文件

    /**
     * 工作
     *
     * @return xml 输出文件的路径
     */
    public String work() {
        var startTime = System.currentTimeMillis();
        log.info("{} Start. Processing = {}", Thread.currentThread().getName(), pdfFile.getPath());
        var xmlPath = processCommand();
        var endTime = System.currentTimeMillis();
        log.info("{} End. : {} ms", Thread.currentThread().getName(), endTime - startTime);
        return xmlPath;
    }

    /**
     * 得到输出目录
     *
     * @return 输出目录
     */
    private String getOutputDir() {
        var dir = "output/" + pdfFile.getName().replace(".pdf", "");
        File outputDir = new File(dir);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                log.error("Failed to create output directory {}", outputDir.getPath());
            }
        }
        return dir;
    }

    /**
     * 执行
     */
    private String processCommand() {
        try {
            var tei = GrobidService.runGrobid(pdfFile);
            var outputDir = getOutputDir();
            File outputFile = new File(outputDir  + "/" + pdfFile.getName().replace(".pdf", ".xml"));
            try {
                FileUtils.writeStringToFile(outputFile, tei, "UTF-8");
            } catch (Exception e) {
                log.error("\t\t error wiring result under path {}", outputFile.getPath());
            }
            log.info("\t\t result written in {}", outputFile.getPath());
            return outputFile.getPath();
        } catch (GrobidTimeoutException e) {
            log.warn("Processing of {} timed out", pdfFile.getPath());
        } catch (RuntimeException e) {
            log.error("\t\t error occurred while processing {}", pdfFile.getPath());
            log.error(e.getMessage(), e.getCause());
        }
        return null;
    }
}
