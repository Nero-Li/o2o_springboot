package com.lym.util;

/**
 * @ClassName PathUtil
 * @Author lyming
 * @Date 2019/1/6 17:33
 **/
public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/o2o/image/";
        } else {
            basePath = "/Users/lym/Pictures/o2o/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = shopId + "/";
        return imagePath.replace("/", separator);
    }
}
