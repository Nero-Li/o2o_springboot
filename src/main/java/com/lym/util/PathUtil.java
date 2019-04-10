package com.lym.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName PathUtil
 * @Author lyming
 * @Date 2019/1/6 17:33
 **/
@Configuration
public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    private static String winPath;
    private static String linuxPath;
    private static String shopPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = shopPath+shopId + "/";
        return imagePath.replace("/", separator);
    }
}
