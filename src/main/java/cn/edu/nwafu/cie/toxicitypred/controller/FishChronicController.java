package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:21
 * @description: This is a test class
 */
@RestController
public class FishChronicController {
    private static final Logger logger = LoggerFactory.getLogger(FishChronicController.class);

    @Autowired
    private FishChronicService fishChronicService;
    /**
     * 鱼类急性毒性记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/fishchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/fishchronic/vldfiles";  //smi文件路径（验证集）
    /**
     * 鱼类急性毒性记录的描述符文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vldfiles";  //smi文件路径（验证集）

    /**
     * 用于knn的csv文件
     **/
    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vlddes.csv";
    private static String trainAsVldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/trainasvlddes.csv";

    /**
     * 鱼类急性毒性记录新进文件存放路径
     **/
    private static String newSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/fishchronic/new/"; //smi文件路径（新进）
    private static String newDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/new/"; //描述符文件路径（新进）
    private static String newDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/newdes.csv";     //新记录的knn文件

    /*************************************************** smiles->smi文件 ****************************************************/
    @RequestMapping("/fishchr/smitrains")
    public Result getTrainSmiFile() {
        int trainSize = fishChronicService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/smivlds")
    public Result getVldSmiFile() {
        int vldSize = fishChronicService.getSmiFiles(vldSmiFilesPath, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/smifiles")
    public Result getSmiFiles() {
        int vldSize = fishChronicService.getSmiFiles(vldSmiFilesPath, "validate");
        int trainSize = fishChronicService.getSmiFiles(trainSmiFilesPath, "train");
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
    public Result getTrainDragonOutFiles() {
        int trainSize = fishChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = fishChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = fishChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = fishChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
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
    public Result updateTrainDesToDB() {
        int trainUpdateSize = fishChronicService.updateDescriptions(trainDragonOutFilesPath, FishChronic.class, "train");
        if (trainUpdateSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/fishchr/vlddestodb")
    public Result updateVldDesToDB() {
        int vldUpdateSize = fishChronicService.updateDescriptions(vldDragonOutFilesPath, FishChronic.class, "validate");
        if (vldUpdateSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/fishchr/destodb")
    public Result updateDesToDB() {
        int trainUpdateSize = fishChronicService.updateDescriptions(trainDragonOutFilesPath, FishChronic.class, "train");
        if (trainUpdateSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = fishChronicService.updateDescriptions(vldDragonOutFilesPath, FishChronic.class, "validate");
        if (vldUpdateSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }

    /*************************************************** 读数据库生成描述符的csv件，用于knn ****************************************************/
    @RequestMapping("/fishchr/trainscsv")
    public Result getTrainsCSV() {
        File trainDesFile = new File(trainDesFilePath);
        int trainSize = fishChronicService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/fishchr/vldscsv")
    public Result getVldsCSV() {
        File vldDesFile = new File(trainDesFilePath);
        int vldSize = fishChronicService.getDesFile(vldDesFile, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("鱼类慢性毒性验证集数据在转为csv文件时出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/fishchr/descsv")
    public Result getDesCSV() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = fishChronicService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }
        int vldSize = fishChronicService.getDesFile(vldDesFile, "validate");
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
        try {
            File trainAsVldDesFile = fishChronicService.getTrainAsVldCSV(trainAsVldDesFilePath,trainDesFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorMsg("鱼类慢性毒性训练集数据在转为csv文件时出错！");
        }
        return Result.successWithoutData();
    }

    /*************************************************** 数据预处理已全部完成，训练集503，验证集127，只剩knn ****************************************************/
    @RequestMapping("/fishchr/vldknn")
    public Result vldKnnPre() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        Map<String, String> knnMap = fishChronicService.runKnn(trainDesFile, vldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += fishChronicService.updatePreValueByCasNo(entry.getKey(), entry.getValue(), "validate");
        }
        return Result.success(numOfUpdatePreValues);
    }

    @RequestMapping("/fishchr/trainknn")
    public Result trainKnnPre() {
        File trainDesFile = new File(trainDesFilePath);
        File trainAsVldDesFile = new File(trainAsVldDesFilePath);

        Map<String, String> knnMap = fishChronicService.runKnn(trainDesFile, trainAsVldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += fishChronicService.updatePreValueByCasNo(entry.getKey(), entry.getValue(), "train");
        }
        return Result.success(numOfUpdatePreValues);
    }

    /*************************************************** 新进化合物的处理方法 ****************************************************/
    @RequestMapping("/fishchr/knn")
    public Result pre(@RequestParam("casno") String casNo, @RequestParam("smiles") String smiles) throws Exception {
        List<FishChronic> fishChronicList = fishChronicService.getByCasNo(casNo);
        /*if (fishChronicList != null) {
            return Result.success(fishChronicList.get(0));
        }*/
        if (!FileUtil.validateDir(newSmiFilesPath)) {
            return Result.errorMsg("新化合物的smi文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newDragonOutFilesPath)) {
            return Result.errorMsg("新化合物的描述符文件存储目录错误！");
        }
        //生成smi文件
        File newSmiFile = new File(newSmiFilesPath + casNo + ".smi");
        boolean flag = fishChronicService.writeFile(newSmiFile, smiles, false);
        if (!flag) {
            logger.error(casNo+"生成smi文件出错！");
            return null;
        }
        //进入dragon，转为描述符文件
        flag = fishChronicService.smiFileToDragonOutFile(newSmiFile, newDragonOutFilesPath);
        if(!flag){
            logger.error(casNo+" dragon生成描述符文件出错!");
            return null;
        }
        //提取描述符到实体中
        FishChronic newFishChronic = null;
        File newDragonOutFile = new File(newDragonOutFilesPath + casNo + ".txt");
        newFishChronic = fishChronicService.getDescription(newDragonOutFile, FishChronic.class);
        newFishChronic.setDatatype("new");
        newFishChronic.setSmiles(smiles);
        //构造用于knn的新csv文件
        flag = fishChronicService.getDesFile(new File(newDesFilePath), newFishChronic);
        if(!flag){
            return Result.errorMsg("生成csv文件时出错！");
        }
        //knn
        Map<String, String> knnMap = fishChronicService.runKnn(new File(trainDesFilePath), new File(newDesFilePath));
        newFishChronic.setPreValue(knnMap.get(casNo));
        //新纪录插入至数据库
        fishChronicService.insert(newFishChronic);
        return Result.success(newFishChronic);
    }

    @RequestMapping("/fishchr/calculator")
    public Result calculate() throws Exception {
        HashMap<String,Object> modelMap = new HashMap<>();
        modelMap.put("trainModel",fishChronicService.calculateModel("train"));
        modelMap.put("validateModel",fishChronicService.calculateModel("validate"));
        return Result.success(modelMap);
    }
}
