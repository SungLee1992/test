package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:21
 * @description: This is a test class
 */
@RestController
public class FishChronicController {
    @Autowired
    private FishChronicService fishChronicService;
    /**
     * 鱼类急性毒性记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/fishchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/fishchronic/vldfiles";  //smi文件路径（验证集）
    /**
     * 鱼类急性毒性记录的smi文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vldfiles";  //smi文件路径（验证集）

    //用于knn的csv文件
    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vlddes.csv";
    private static String trainAsVldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/trainasvlddes.csv";
    /*************************************************** smiles->smi文件 ****************************************************/
    @RequestMapping("/fishchr/smitrains")
    public Result getTrainSmiFile(){
        int trainSize = fishChronicService.getSmiFiles(trainSmiFilesPath,"train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/smivlds")
    public Result getVldSmiFile(){
        int vldSize = fishChronicService.getSmiFiles(vldSmiFilesPath,"validate");
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/smifiles")
    public Result getSmiFiles(){
        int vldSize = fishChronicService.getSmiFiles(vldSmiFilesPath,"validate");
        int trainSize = fishChronicService.getSmiFiles(trainSmiFilesPath,"train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** smi->描述符 ****************************************************/
    @RequestMapping("/fishchr/destrains")
    public Result getTrainDragonOutFiles(){
        int trainSize = fishChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath,trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/desvlds")
    public Result getVldDragonOutFiles(){
        int vldSize = fishChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath,vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/desfiles")
    public Result getDragonOutFiles(){
        int trainSize = fishChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath,trainDragonOutFilesPath);
        int vldSize = fishChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath,vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据dragon转换出错！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/fishchr/traindestodb")
    public Result updateTrainDesToDB(){
        int trainUpdateSize = fishChronicService.updateDescriptions(trainDragonOutFilesPath,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("鱼类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/fishchr/vlddestodb")
    public Result updateVldDesToDB(){
        int vldUpdateSize = fishChronicService.updateDescriptions(vldDragonOutFilesPath,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("鱼类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/fishchr/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = fishChronicService.updateDescriptions(trainDragonOutFilesPath,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("鱼类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = fishChronicService.updateDescriptions(vldDragonOutFilesPath,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("鱼类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }

    /*************************************************** 读数据库生成描述符的csv件，用于knn ****************************************************/
    @RequestMapping("/fishchr/trainscsv")
    public Result getTrainsCSV(){
        File trainDesFile = new File(trainDesFilePath);
        int trainSize = fishChronicService.getDesFile(trainDesFile,"train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/vldscsv")
    public Result getVldsCSV(){
        File vldDesFile = new File(trainDesFilePath);
        int vldSize = fishChronicService.getDesFile(vldDesFile,"validate");
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据在转为csv文件时出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/descsv")
    public Result getDesCSV(){
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = fishChronicService.getDesFile(trainDesFile,"train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }
        int vldSize = fishChronicService.getDesFile(vldDesFile,"validate");
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据在转为csv文件时出错！");
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    @RequestMapping("/fishchr/trainasvldcsv")
    public Result getTrainAsVldCSV() {
        File trainDesFile = new File(trainAsVldDesFilePath);
        int trainSize = fishChronicService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        return Result.success(resultMap);
    }

    /*************************************************** 数据预处理已全部完成，训练集503，验证集127，只剩knn ****************************************************/
    @RequestMapping("/fishchr/vldknn")
    public Result vldKnnPre(){
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        fishChronicService.runKnn(trainDesFile,vldDesFile);
        return Result.successWithoutData();
    }
}
