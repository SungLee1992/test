package cn.edu.nwafu.cie.toxicitypred.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-19 09:45
 * @description: 文件过滤器
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String val = null;
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); //日期格式yyyy-mm-dd
    private static DecimalFormat df = new DecimalFormat("0");            //数字格式，防止长数字成为科学计数法形式，或者int变为double形式
    private static List<ArrayList<String>> strLists = new ArrayList<ArrayList<String>>(); //存放Excel中的数据

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

    /**
     * @param file
     * @return: java.util.List<java.util.ArrayList                                                               <               java.lang.String>>
     * @description:POI方式解析EXCEL表格(2007以上版本，xlsx)
     */
    public static List<ArrayList<String>> poiReadXExcel(String file) throws FileNotFoundException, IOException {
        FileInputStream input = new FileInputStream(new File(file)); //读取的文件路径
        XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(input));
        XSSFSheet sheet = wb.getSheetAt(0); //获取第一张表

        int rowNum = sheet.getPhysicalNumberOfRows();//得到数据的行数
        System.out.println("行数：" + rowNum);
        strLists.clear();

        //遍历行
        for (int i = 1; i < rowNum; i++) //Excel从第1行开始有数据
        {
            List<String> strList = new ArrayList<String>();
            XSSFRow row = sheet.getRow(i);
            int colNum = row.getPhysicalNumberOfCells();//得到当前行中存在数据的列数
            //遍历列（遍历前两列）
            for (int j = 0; j < 2; j++) {
                XSSFCell cell = row.getCell(j);
                strList.add(getXCellVal(cell));
            }
            strLists.add(i-1, (ArrayList<String>) strList); //存储该行
        }

        //打印
        for (ArrayList<String> stringList : strLists) {
            for (String str : stringList) {
                System.out.print(str + "  ");
            }
            System.out.println();
        }
        wb.close();
        return strLists;
    }

    /**
     * @param cell
     * @return String
     * TODO 获取单元格中的值
     * @author: 李嵩
     */
    private static String getXCellVal(XSSFCell cell) {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    val = fmt.format(cell.getDateCellValue()); //日期型
                } else {
                    val = df.format(cell.getNumericCellValue()); //数字型
                }
                break;
            case STRING: //文本类型
                val = cell.getStringCellValue();
                break;
            case BOOLEAN: //布尔型
                val = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK: //空白
                val = cell.getStringCellValue();
                break;
            case ERROR: //错误
                val = "错误";
                break;
            case FORMULA: //公式
                try {
                    val = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    val = String.valueOf(cell.getNumericCellValue());
                }
                break;
            default:
                val = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
        }
        return val;
    }
}
