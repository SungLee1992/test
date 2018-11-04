package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    public void readVldDesFileTest(){
        FishChronicService fishChronicService = new FishChronicService();
        File file = new File(System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vlddes.csv");
        Map<String,Object> map = (Map<String, Object>) fishChronicService.readVldDesFile(file);
        System.out.println(map.size());
    }

    @Test
    public void readExcel(){
        try {
            List<ArrayList<String>> daphniaChronicExcels = FileUtil.poiReadXExcel("C:\\Users\\Administrator\\Desktop\\daphnia_chronic-cheichei-20181103.xlsx");
            List<ArrayList<String>> algalChronicExcels = FileUtil.poiReadXExcel("C:\\Users\\Administrator\\Desktop\\algal_chronic-cheichei-20181103.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}