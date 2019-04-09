package com.lym.util;

import com.lym.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName ImageUtil
 * @Author lyming
 * @Date 2019/1/6 17:11
 **/
public class ImageUtil {
    /**
     * 根据当前线程的类加载器获取资源路径
     */
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    /**
     * 文件时间格式
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random r = new Random();

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

/*    public static void main(String[] args) {
        try {
            Thumbnails.of(new File("C:\\Users\\lym\\Pictures\\thumb-1920-641968.jpg"))
                    .size(1920, 1080)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(baseBath + "/ciger.png")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile("C:\\Users\\lym\\Pictures\\after.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 处理缩略图,并返回新生成图片的相对路径值
     *
     * @return
     */
    public static String generateThumbnal(ImageHolder imageHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extention = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extention;
        logger.debug("current relativeAddr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage())
                    .size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/ciger.png")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile(dest);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名,当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的5位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sdf.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * CommonsMultipartFile转化成File
     *
     * @param commonsMultipartFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile commonsMultipartFile) {
        File file = new File(commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 判断storePath是文件路径还是目录路径
     * -->如果storePath是文件路径,则删除该文件
     * -->如果storePath是目录路径,则删除该目录下的所有文件
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        //获取全路径
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            //是文件就直接删,是目录先删除目录里的文件,再删除目录本身
            fileOrPath.delete();
        }
    }

    /**
     * 生成详情图片,与生成缩略图方法差别在于像素和压缩比重更大
     *
     * @param imageHolder
     * @param targetAddr
     * @return
     */
    public static String generateNomalImg(ImageHolder imageHolder, String targetAddr) {
        String realFileName = getRandomFileName();
        String extention = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extention;
        logger.debug("current relativeAddr is:" + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imageHolder.getImage())
                    .size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/ciger.png")), 0.25f)
                    .outputQuality(0.9f)
                    .toFile(dest);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }
}
