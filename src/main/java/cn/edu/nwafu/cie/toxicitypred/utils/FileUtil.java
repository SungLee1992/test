package cn.edu.nwafu.cie.toxicitypred.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author: SungLee
 * @date: 2018-10-19 09:45
 * @description: 文件过滤器
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取suffix后缀的文件
     */
    public static File[] filterFile(String suffix, String filePath) {
        if (!FileUtil.validateDir(filePath)) {
            logger.warn(filePath + "目录不合法！");
            return null;
        }
        File fileDir = new File(filePath);    //文件所在的目录
        File[] destFiles = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileName = file.getName();//获取文件的名称
                return fileName.endsWith(suffix);
            }
        });
        return destFiles;
    }

    /**
     * 获取除suffix后缀的文件
     */
    public static File[] filterFileExc(String suffix, String filePath) {
        if (!FileUtil.validateDir(filePath)) {
            logger.warn(filePath + "目录不合法！");
            return null;
        }
        File fileDir = new File(filePath);    //文件所在的目录
        File[] destFiles = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileName = file.getName();//获取文件的名称
                return !fileName.endsWith(suffix);
            }
        });
        return destFiles;
    }

    /**
     * @param: [file, destPath]
     * @return: boolean
     * 移动文件的操作
     */
    public static boolean moveFile(File file, String destDir) {
        //移动文件
        //NIO的方式复制文件
        try (   //这种写法可以不用显示关闭流
                FileChannel fromChannel = new FileInputStream(file).getChannel();
                FileChannel toChannel = new FileOutputStream(destDir + "/" + file.getName()).getChannel();) {
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            System.out.println(file.getName() + "移动成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //复制成功后，删除源文件（写在finally中是为了防止因为流占用文件导致不能删除）
            if (!file.delete()) {
                logger.warn(file.getName() + "源文件删除失败");
            }
            System.out.println(file.getName() + "移动成功！");
        }
        return true;
    }

    /**
     * @param: [file, destPath]
     * @return: boolean
     * 复制文件的操作
     */
    public static boolean copyFile(File file, String destDir, String fileName) {
        //NIO的方式复制文件
        try (   //这种写法可以不用显示关闭流
                FileChannel fromChannel = new FileInputStream(file).getChannel();
                FileChannel toChannel = new FileOutputStream(destDir + "/" + fileName).getChannel()
        ) {
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            System.out.println(file.getName() + "复制成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @param: [destPath]
     * @return: boolean
     * 验证目标路径是个合法目录
     */
    public static boolean validateDir(String destPath) {
        File destDir = new File(destPath);    //目标目录
        //检查目标路径是否合法
        if (destDir.isFile()) {
            logger.error("目标路径是个文件，请检查目标路径！");
            return false;
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return true;
    }

}
