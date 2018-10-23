package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class BaseServiceTest {

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void smiStrToMopFile() {
    }

    @Test
    public void smiFileToMopFile() {
    }

    @Test
    public void mopFileToOutFile() {
    }

    @Test
    public void mopFilesToOutFiles() {
    }

    @Test
    public void moveOutFile() {
        String mopFilePath = System.getProperty("user.dir") + "/files/mopfiles/algalchronic/trainfiles";  //暂时规定生成mop文件的路径
        String outFilePath = System.getProperty("user.dir")+"/files/outfiles/algalchronic/trainfiles"; //out文件路径（训练集）
        //String vldOutFilesPath = System.getProperty("user.dir")+"/files/outfiles/algalchronic/vldfiles";  //out文件路径（验证集）

        if (!FileUtil.validateDir(outFilePath)) {
            System.out.println("out目录不合法！");
            return;
        }
        File[] excMopFiles = FileUtil.filterFileExc(".mop",mopFilePath);
        //移动除".mop"外的所有文件
        for (File outFile : excMopFiles) {
            FileUtil.moveFile(outFile, outFilePath);
        }

    }
}