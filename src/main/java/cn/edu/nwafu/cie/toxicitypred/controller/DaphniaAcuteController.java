package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
import javafx.beans.binding.ObjectExpression;
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

    /*************************************************** smiles->smi文件 ****************************************************/
    @RequestMapping("/dapact/smitrains")
    public Result getTrainSmiFile() {
        int trainSize = daphniaAcuteService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/dapact/smivlds")
    public Result getVldSmiFile() {
        int vldSize = daphniaAcuteService.getSmiFiles(vldSmiFilesPath, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/dapact/smifiles")
    public Result getSmiFile() {
        int vldSize = daphniaAcuteService.getSmiFiles(vldSmiFilesPath, "validate");
        int trainSize = daphniaAcuteService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
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
            return Result.errorMsg("溞类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/dapact/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = daphniaAcuteService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/dapact/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = daphniaAcuteService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = daphniaAcuteService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据dragon转换出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

/*************************************************** dragon计算仍有出错，训练集12/1184，验证集2/299 ****************************************************/
    /*溞类急性
    训练集
        000061-82-5
        004286-23-1
        008044-71-1
        011141-17-6
        027589-33-9
        029457-72-5
        041267-43-0
        050957-96-5
        055635-13-7
        067233-85-6
        103639-04-9
        199119-58-9
    验证集
        000154-42-7
        079917-90-1
    */
}
