package bupt.edu.jhc.pdf.parser.core.extraction.adobe;

import bupt.edu.jhc.pdf.parser.config.ConfigFactory;
import bupt.edu.jhc.pdf.parser.util.FileUtils;
import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials;
import com.adobe.pdfservices.operation.exception.SDKException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.pdfjobs.jobs.ExtractPDFJob;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.ExtractElementType;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.ExtractPDFParams;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.ExtractRenditionsElementType;
import com.adobe.pdfservices.operation.pdfjobs.params.extractpdf.TableStructureType;
import com.adobe.pdfservices.operation.pdfjobs.result.ExtractPDFResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @Description: 提取 PDF 中的表格信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
@Slf4j
public class ExtractTableInfoFromPDF {

    /**
     * 创建输出文件路径
     *
     * @param fileName 文件名
     * @return 输出文件路径
     * @throws IOException
     */
    private static String createOutputFilePath(String fileName) throws IOException {
        Files.createDirectories(Paths.get("output/" + fileName));
        return ("output/" + fileName + "/tables.zip");
    }

    /**
     * 提取 PDF 中的表格信息
     *
     * @param inputPath
     * @return 输出文件路径
     */
    public static String exec(String inputPath) {
        try (var inputStream = Files.newInputStream(new File(inputPath).toPath())) {
            // 1.Initial setup, create credentials instance
            Credentials credentials = new ServicePrincipalCredentials(ConfigFactory.getClientCredentials().getId(), ConfigFactory.getClientCredentials().getSecret());

            // 2.Creates a PDF Services instance
            var pdfServices = new PDFServices(credentials);

            // 3.Creates an asset(s) from source file(s) and upload
            var asset = pdfServices.upload(inputStream, PDFServicesMediaType.PDF.getMediaType());

            // 4.Create parameters for the job
            var extractPDFParams = ExtractPDFParams.extractPDFParamsBuilder()
                    .addElementsToExtract(Arrays.asList(ExtractElementType.TEXT, ExtractElementType.TABLES))
                    .addElementToExtractRenditions(ExtractRenditionsElementType.TABLES)
                    .addTableStructureFormat(TableStructureType.CSV)
                    .build();

            // 5.Creates a new job instance
            var extractPDFJob = new ExtractPDFJob(asset).setParams(extractPDFParams);

            // 6.Submit the job and gets the job result
            var location = pdfServices.submit(extractPDFJob);
            var pdfServicesResponse = pdfServices.getJobResult(location, ExtractPDFResult.class);

            // 7.Get content from the resulting asset(s)
            var resultAsset = pdfServicesResponse.getResult().getResource();
            var streamAsset = pdfServices.getContent(resultAsset);

            // 8.Creates an output stream and copy stream asset's content to it
            String outputFilePath = createOutputFilePath(FileUtils.getNameFromPath(inputPath, false));
            log.info(String.format("Saving asset at %s", outputFilePath));

            // 9.Copy the content to the output file
            var outputStream = Files.newOutputStream(new File(outputFilePath).toPath());
            IOUtils.copy(streamAsset.getInputStream(), outputStream);
            outputStream.close();

            // 10.Return the output file path
            return outputFilePath;
        } catch (ServiceApiException | IOException | SDKException | ServiceUsageException e) {
            log.error("Exception encountered while executing operation", e);
        }
        return null;
    }
}
