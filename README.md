# pdf-parser
PDF 解析器，提取 PDF 文件中的表格、图像、文本等

## 快速开始
### 1. 编写配置文件
```yaml
### client credentials ###
clientCredentials.id=c49b23dc656d4d9c8fcd1fb440c32bc6
clientCredentials.secret=p8e-uw_yF-erTV_LpV6zdrEBKRNR5FtSO2TM

### grobid client configuration ###
grobid.serverHost=localhost
grobid.serverPort=8070
grobid.sleepTime=5000
```
> client credentials 来源：https://acrobatservices.adobe.com/dc-integration-creation-app-cdn/main.html?api=pdf-services-api
### 2. 搭建 grobid 环境
```shell
   docker run --rm --init --ulimit core=0 -p 8070:8070 lfoppiano/grobid:0.8.0
```
> 这里采用 docker 构建，需要保证具有 docker 环境
### 3. 主函数
```java
public static void main(String[] args) {
   var dir = "G:\\PDF Data";
   AdobeProcess.exec(dir); // 提取表格
   GrobidProcess.exec(dir); // 提取文本
   SpireProcess.exec(dir); // 提取图片
}
```
> Dir 换成存放 PDF 文件的目录
   
