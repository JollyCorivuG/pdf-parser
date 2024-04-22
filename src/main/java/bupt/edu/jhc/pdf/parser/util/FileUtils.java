package bupt.edu.jhc.pdf.parser.util;

/**
 * @Description: 文件工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2024/4/22
 */
public class FileUtils {
    /**
     * 从路径中获取文件名
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getNameFromPath(String path) {
        var index = path.lastIndexOf("/");
        if (index == -1) {
            index = path.lastIndexOf("\\");
        }
        return path.substring(index + 1);
    }

    /**
     * 从路径中获取文件名
     *
     * @param path 路径
     * @param isPrefix 是否包含后缀
     * @return 文件名
     */
    public static String getNameFromPath(String path, boolean isPrefix) {
        var name = getNameFromPath(path);
        return !isPrefix ? name.substring(0, name.lastIndexOf(".")) : name;
    }

    /**
     * 获取传入文件的存储目录
     *
     * @param path 文件路径
     * @return 存储目录
     */
    public static String getStoreDir(String path) {
        var index = path.lastIndexOf("/");
        if (index == -1) {
            index = path.lastIndexOf("\\");
        }
        return path.substring(0, index);
    }
}
