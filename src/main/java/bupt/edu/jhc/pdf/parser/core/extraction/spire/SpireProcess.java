package bupt.edu.jhc.pdf.parser.core.extraction.spire;

import bupt.edu.jhc.pdf.parser.core.extraction.grobid.exceptions.UnreachableGrobidServiceException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: Spire Process
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/24
 */
@Slf4j
public class SpireProcess {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void exec(String storageDir) {
        try {
            // 1.得到所有需要解析的 PDF 文件
            var dirInputPath = new File(storageDir);
            var refFiles = dirInputPath.listFiles((dir, name) -> name.endsWith(".pdf"));
            if (refFiles == null) {
                throw new IllegalStateException("Folder " + dirInputPath.getAbsolutePath()  + " does not seem to contain any PDF file");
            }
            log.info("{} PDF files", refFiles.length);

            // 2.根据 PDF 文件个数创建 worker, 并交给线程池执行
            for (var pdfFile : refFiles) {
                executor.execute(() -> ExtractFiguresFromPDF.exec(pdfFile.getAbsolutePath()));
            }

            // 3.等待所有线程执行完毕后关闭线程池
            try {
                log.info("wait for thread completion");
                executor.shutdown();
                while (!executor.isTerminated()) {
//                        log.info("waiting for all threads to finish");
                }
            } finally {
                if (!executor.isTerminated()) {
                    log.error("cancel all non-finished workers");
                }
                executor.shutdownNow();
            }

            log.info("Finished all threads");
        } catch (UnreachableGrobidServiceException ugse) {
            log.error(ugse.getMessage());
        }
    }
}
