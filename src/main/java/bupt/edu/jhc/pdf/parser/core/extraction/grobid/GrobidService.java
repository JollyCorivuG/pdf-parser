package bupt.edu.jhc.pdf.parser.core.extraction.grobid;

import bupt.edu.jhc.pdf.parser.config.ConfigFactory;
import bupt.edu.jhc.pdf.parser.core.extraction.grobid.exceptions.GrobidTimeoutException;
import bupt.edu.jhc.pdf.parser.core.extraction.grobid.exceptions.UnreachableGrobidServiceException;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 通过 api 调用 grobid web 服务
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/23
 */
@Slf4j
public class GrobidService {
    /**
     * 构建 Grobid 服务 url
     *
     * @return
     */
    private static String buildGrobidServiceUrl() {
        return "http://" + ConfigFactory.getGrobidClientConfig().getServerHost() + ":" + ConfigFactory.getGrobidClientConfig().getServerPort() +  "/api/processFulltextDocument";
    }

    /**
     * Call the Grobid full text extraction service on server.
     *
     * @param pdfFile InputStream of the PDF file to be processed
     * @return the resulting TEI document as a String or null if the service
     * failed
     */
    public static String runGrobid(File pdfFile) {
        String tei = null;
        HttpURLConnection conn = null;
        try {
            // 1.创建 url
            var uri = URI.create(buildGrobidServiceUrl());
            conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            // 2.创建 multipart 实体
            var multipartEntity = MultipartEntityBuilder.create()
                    .addPart("input", new FileBody(pdfFile))
                    .build();
            conn.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());

            // 3.将 multipart 实体写入输出流
            try (var out = conn.getOutputStream()) {
                multipartEntity.writeTo(out);
            }

            // 4.处理响应
            if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAVAILABLE) {
                throw new HttpRetryException("Failed : HTTP error code : " + conn.getResponseCode(), conn.getResponseCode());
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                tei = "";
            } else if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " " + IOUtils.toString(conn.getErrorStream(), "UTF-8"));
            } else {
                var in = conn.getInputStream();
                tei = IOUtils.toString(in, StandardCharsets.UTF_8);
                IOUtils.closeQuietly(in);
            }
        } catch (ConnectException e) {
            log.error(e.getMessage(), e.getCause());
            try {
                Thread.sleep(20000);
                runGrobid(pdfFile);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } catch (HttpRetryException e) {
            log.error(e.getMessage(), e.getCause());
            try {
                Thread.sleep(ConfigFactory.getGrobidClientConfig().getSleepTime());
                runGrobid(pdfFile);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } catch (SocketTimeoutException e) {
            throw new GrobidTimeoutException("Grobid processing timed out.");
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return tei;
    }

    /**
     * 构建检查 Grobid 是否存活的 url
     *
     * @return
     */
    private static String buildCheckGrobidIsAliveUrl() {
        return "http://" + ConfigFactory.getGrobidClientConfig().getServerHost() + ":" + ConfigFactory.getGrobidClientConfig().getServerPort() + "/api/isalive";
    }
    /**
     * Checks if Grobid service is responding and local tmp directory is
     * available.
     *
     * @return boolean
     */
    public static boolean isGrobidOk() throws UnreachableGrobidServiceException {
        log.info("Checking Grobid service...");
        int respCode;
        var url = buildCheckGrobidIsAliveUrl();
        try (var resp = HttpRequest.get(url).execute()){
            respCode = resp.getStatus();
        }
        if (respCode != 200) {
            throw new UnreachableGrobidServiceException(respCode);
        }
        log.info("Grobid service is ok and can be used.");
        return true;
    }
}
