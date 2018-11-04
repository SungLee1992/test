package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:21
 * @description: This is a test class
 */
@RestController
public class DaphniaAcuteController {
    @Autowired
    private DaphniaAcuteService daphniaAcuteService;
    /**
     * 溞类急性毒性记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/daphniaacute/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/daphniaacute/vldfiles";  //smi文件路径（验证集）
    /**
     * 溞类急性毒性记录的smi文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/vldfiles";  //smi文件路径（验证集）

    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/vlddes.csv";

    /*************************************************** smiles->smi文件 ****************************************************/
    @RequestMapping("/dapact/smitrains")
    public Result getTrainSmiFile() {
        int trainSize = daphniaAcuteService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类急性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/dapact/smivlds")
    public Result getVldSmiFile() {
        int vldSize = daphniaAcuteService.getSmiFiles(vldSmiFilesPath, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("溞类急性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/dapact/smifiles")
    public Result getSmiFile() {
        int vldSize = daphniaAcuteService.getSmiFiles(vldSmiFilesPath, "validate");
        int trainSize = daphniaAcuteService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类急性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类急性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** smi->描述符 ****************************************************/
    @RequestMapping("/dapact/destrains")
    public Result getTrainDragonOutFiles() {
        int trainSize = daphniaAcuteService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类急性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/dapact/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = daphniaAcuteService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("溞类急性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/dapact/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = daphniaAcuteService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = daphniaAcuteService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类急性毒性训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类急性毒性验证集数据dragon转换出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/dapact/traindestodb")
    public Result updateTrainDesToDB(){
        int trainUpdateSize = daphniaAcuteService.updateDescriptions(trainDragonOutFilesPath, DaphniaAcute.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("溞类急性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/dapact/vlddestodb")
    public Result updateVldDesToDB(){
        int vldUpdateSize = daphniaAcuteService.updateDescriptions(vldDragonOutFilesPath, DaphniaAcute.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("溞类急性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/dapact/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = daphniaAcuteService.updateDescriptions(trainDragonOutFilesPath, DaphniaAcute.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("溞类急性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = daphniaAcuteService.updateDescriptions(vldDragonOutFilesPath, DaphniaAcute.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("溞类急性毒性验证集数据的描述符在更新数据库时出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }

    /*************************************************** 数据预处理已全部完成，训练集1172，验证集297，只剩knn ****************************************************/
    @RequestMapping("/dapact/descsv")
    public Result getDesCSV() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = daphniaAcuteService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类急性毒性训练集数据在转为csv文件时出错！");
        }
        int vldSize = daphniaAcuteService.getDesFile(vldDesFile, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("溞类急性毒性验证集数据在转为csv文件时出错！");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }
}
