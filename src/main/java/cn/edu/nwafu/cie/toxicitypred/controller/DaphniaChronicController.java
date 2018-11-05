package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaChronic;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaChronicService;
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
public class DaphniaChronicController {
    @Autowired
    private DaphniaChronicService daphniaChronicService;
    /**
     * 溞类慢性毒性记录的mop文件存放路径
     **/
    private static String trainMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/daphniachronic/trainfiles";  //暂时规定生成mop文件的路径
    private static String vldMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/daphniachronic/vldfiles";  //暂时规定生成mop文件的路径
    /**
     * 溞类慢性毒性记录的out文件存放路径
     **/
    private static String trainOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/daphniachronic/trainfiles"; //out文件路径（训练集）
    private static String vldOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/daphniachronic/vldfiles";  //out文件路径（验证集）
    /**
     * 溞类慢性毒性记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/daphniachronic/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/daphniachronic/vldfiles";  //smi文件路径（验证集）
    /**
     * 溞类慢性毒性记录的描述符txt文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniachronic/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniachronic/vldfiles";  //smi文件路径（验证集）

    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniachronic/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniachronic/vlddes.csv";
    /*************************************************** smi->mop ****************************************************/
    /**
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 溞类慢性毒性训练集数据转为mop文件
     */
    @RequestMapping("/dapchr/trainmops")
    public Result getTrainMopFiles() {
        int size = daphniaChronicService.smiStrToMopFiles(trainMopFilesPath, "train");
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据在转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 溞类慢性毒性验证集数据转为mop文件
     */
    @RequestMapping("/dapchr/vldmops")
    public Result getVldMopFiles() {
        int size = daphniaChronicService.smiStrToMopFiles(vldMopFilesPath, "validate");
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    /*************************************************** smi->mop ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 溞类慢性毒性训练集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/dapchr/trainouts")
    public Result getTrainOutFiles() {
        int size = daphniaChronicService.mopFilesToOutFiles(trainMopFilesPath);
        int moveSize = daphniaChronicService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 溞类慢性毒性验证集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/dapchr/vldouts")
    public Result getVldOutFiles() {
        int size = daphniaChronicService.mopFilesToOutFiles(vldMopFilesPath);
        int moveSize = daphniaChronicService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * 溞类慢性毒性mop文件经过MOPAC转为out文件
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     */
    @RequestMapping("/dapchr/outs")
    public Result getOutFiles() {
        int vldSize = daphniaChronicService.mopFilesToOutFiles(vldMopFilesPath);
        int vldMoveSize = daphniaChronicService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        int trainSize = daphniaChronicService.mopFilesToOutFiles(trainMopFilesPath);
        int trainMoveSize = daphniaChronicService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据mop文件转为out文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据mop文件转为out文件的数量为0！");
        }
        if (trainMoveSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据移动out文件的数量为0！");
        }
        if (vldMoveSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据移动out文件的数量为0！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("trainMoveSize", trainMoveSize);
        resultMap.put("vldSize", vldSize);
        resultMap.put("vldMoveSize", vldMoveSize);
        return Result.success(resultMap);
    }

    /*************************************************** out->smi ****************************************************/
    @RequestMapping("/dapchr/smivlds")
    public Result getVldSmiFiles() {
        int size = daphniaChronicService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据mop文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/dapchr/smitrains")
    public Result getTrainSmiFiles() {
        int size = daphniaChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据mop文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/dapchr/smifiles")
    public Result getSmiFiles() {
        int trainSize = daphniaChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        int vldSize = daphniaChronicService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据mop文件转为smi文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据mop文件转为smi文件的数量为0！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** smi->描述符 ****************************************************/
    @RequestMapping("/dapchr/destrains")
    public Result getTrainDragonOutFiles() {
        int trainSize = daphniaChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/dapchr/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = daphniaChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/dapchr/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = daphniaChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = daphniaChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据dragon转换出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/dapchr/traindestodb")
    public Result updateTrainDesToDB() {
        int trainUpdateSize = daphniaChronicService.updateDescriptions(trainDragonOutFilesPath, "train");
        if (trainUpdateSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/dapchr/vlddestodb")
    public Result updateVldDesToDB() {
        int vldUpdateSize = daphniaChronicService.updateDescriptions(vldDragonOutFilesPath, "validate");
        if (vldUpdateSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/dapchr/destodb")
    public Result updateDesToDB() {
        int trainUpdateSize = daphniaChronicService.updateDescriptions(trainDragonOutFilesPath, "train");
        if (trainUpdateSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = daphniaChronicService.updateDescriptions(vldDragonOutFilesPath, "validate");
        if (vldUpdateSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }
    /********************************* dragon计算仍有出错，训练集127/322项，验证集32/80项 *************************************/
    /*************************************************** 数据预处理已全部完成，训练集1172，验证集297，只剩knn ****************************************************/
    @RequestMapping("/dapchr/descsv")
    public Result getDesCSV() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = daphniaChronicService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("溞类慢性毒性训练集数据在转为csv文件时出错！");
        }
        int vldSize = daphniaChronicService.getDesFile(vldDesFile, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("溞类慢性毒性验证集数据在转为csv文件时出错！");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

}
