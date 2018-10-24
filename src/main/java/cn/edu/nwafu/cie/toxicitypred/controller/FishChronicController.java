package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** 数据预处理已全部完成，训练集503，验证集127，只剩knn ****************************************************/
}
