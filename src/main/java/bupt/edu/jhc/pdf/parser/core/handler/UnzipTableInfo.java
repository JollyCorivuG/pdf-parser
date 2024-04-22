package bupt.edu.jhc.pdf.parser.core.handler;

import bupt.edu.jhc.pdf.parser.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Description: 解压表格信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
@Slf4j
public class UnzipTableInfo {


    /**
     * 解压 dir 目录下的 zip 文件，将 zip 文件的 tables 目录的文件解压到 dir 目录下
     *
     * @param dir
     */
    private static void unzip(String dir, String inputZipFile) {
        var tableIndex = 0;
        try (var zis = new ZipInputStream(new FileInputStream(inputZipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                // 1.获取 zip 实体的名称
                var entryName = zipEntry.getName();
                if (!entryName.startsWith("tables") || !entryName.endsWith(".csv")) {
                    zis.closeEntry();
                    continue;
                }
                log.info("解压表格信息: {}", entryName);

                // 2.创建输出文件并按字节写入
                var outputFile = new File(dir, "table-" + tableIndex++ + ".csv");
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    // 将输入流的数据写入输出文件
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }

                // 3.关闭当前解压实体
                zis.closeEntry();
            }
        } catch (IOException e) {
            log.error("解压表格信息失败", e);
        }
    }

    /**
     * 解压表格信息
     *
     * @param inputZipFile
     */
    public static void exec(String inputZipFile) {
        // 1.先创建输出目录
        var tablesDir = new File(FileUtils.getStoreDir(inputZipFile) + "/tables");
        if (!tablesDir.exists()) {
            if (!tablesDir.mkdirs()) {
                log.error("创建目录失败: {}", tablesDir.getAbsolutePath());
            }
        }

        // 2.进行解压
        log.info("开始解压表格信息: {}", inputZipFile);
        unzip(tablesDir.getAbsolutePath(), inputZipFile);
        log.info("解压表格信息完成");

        // 3.删除原 zip 文件
        log.info("删除原 zip 文件: {}", inputZipFile);
        var zipFile = new File(inputZipFile);
        if (zipFile.exists()) {
            if (!zipFile.delete()) {
                log.error("删除文件失败: {}", zipFile.getAbsolutePath());
            }
        }
        log.info("删除原 zip 文件完成");
    }
}
