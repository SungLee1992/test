package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
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
public class AlgalChronicController {
    @Autowired
    private AlgalChronicService algalChronicService;
    /**
     * 藻类慢性毒性记录的mop文件存放路径
     **/
    private static String trainMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/algalchronic/trainfiles";  //暂时规定生成mop文件的路径
    private static String vldMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/algalchronic/vldfiles";  //暂时规定生成mop文件的路径
    /**
     * 藻类慢性毒性记录的out文件存放路径
     **/
    private static String trainOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/algalchronic/trainfiles"; //out文件路径（训练集）
    private static String vldOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/algalchronic/vldfiles";  //out文件路径（验证集）/**
    /**
     * 藻类慢性毒性记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/algalchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/algalchronic/vldfiles";  //smi文件路径（验证集）
    /**
     * 藻类慢性毒性记录的描述符txt文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/vldfiles";  //smi文件路径（验证集）

    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/vlddes.csv";

    /*************************************************** smi->mop ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 藻类慢性毒性训练集数据转为mop文件
     */
    @RequestMapping("/algchr/trainmops")
    public Result getTrainMopFiles() {
        int size = algalChronicService.smiStrToMopFiles(trainMopFilesPath, "train");
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 藻类慢性毒性验证集数据转为mop文件
     */
    @RequestMapping("/algchr/vldmops")
    public Result getVldMopFiles() {
        int size = algalChronicService.smiStrToMopFiles(vldMopFilesPath, "validate");
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    public Result getMopFileBySmiStr() {
        //TODO 先根据casNo判断数据库中是否有该条记录，如果没有再进行转换
        return null;
    }


    /*************************************************** mop->out ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 藻类慢性毒性训练集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/algchr/trainouts")
    public Result getTrainOutFiles() {
        int size = algalChronicService.mopFilesToOutFiles(trainMopFilesPath);
        int moveSize = algalChronicService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 藻类慢性毒性验证集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/algchr/vldouts")
    public Result getVldOutFiles() {
        int size = algalChronicService.mopFilesToOutFiles(vldMopFilesPath);
        int moveSize = algalChronicService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 藻类慢性毒性mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/algchr/outs")
    public Result getOutFiles() {
        int vldSize = algalChronicService.mopFilesToOutFiles(vldMopFilesPath);
        int vldMoveSize = algalChronicService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        int trainSize = algalChronicService.mopFilesToOutFiles(trainMopFilesPath);
        int trainMoveSize = algalChronicService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);

        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据mop文件转为out文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据mop文件转为out文件的数量为0！");
        }
        if (trainMoveSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据移动out文件的数量为0！");
        }
        if (vldMoveSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据移动out文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("trainFlag", trainMoveSize);
        resultMap.put("vldSize", vldSize);
        resultMap.put("vldFlag", vldMoveSize);
        return Result.success(resultMap);
    }

    /*************************************************** out->smi ****************************************************/
    @RequestMapping("/algchr/smivlds")
    public Result getVldSmiFiles() {
        int size = algalChronicService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据mop文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/smitrains")
    public Result getTrainSmiFiles() {
        int size = algalChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据mop文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/smifiles")
    public Result getSmiFiles() {
        int trainSize = algalChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        int vldSize = algalChronicService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据mop文件转为smi文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据mop文件转为smi文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** smi->描述符 ****************************************************/
    @RequestMapping("/algchr/destrains")
    public Result getTrainDragonOutFiles() {
        int trainSize = algalChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/algchr/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = algalChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/algchr/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = algalChronicService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = algalChronicService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据dragon转换出错！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/algchr/traindestodb")
    public Result updateTrainDesToDB(){
        int trainUpdateSize = algalChronicService.updateDescriptions(trainDragonOutFilesPath, AlgalChronic.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("藻类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/algchr/vlddestodb")
    public Result updateVldDesToDB(){
        int vldUpdateSize = algalChronicService.updateDescriptions(vldDragonOutFilesPath, AlgalChronic.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("藻类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/algchr/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = algalChronicService.updateDescriptions(trainDragonOutFilesPath, AlgalChronic.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("藻类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = algalChronicService.updateDescriptions(vldDragonOutFilesPath, AlgalChronic.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("藻类慢性毒性验证集数据的描述符在更新数据库时出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }
    /*************************************************** dragon计算仍有出错，训练集172/438项，验证集47/110项 ****************************************************/
    /*************************************************** 数据预处理已全部完成，训练集1172，验证集297，只剩knn ****************************************************/
    @RequestMapping("/algchr/descsv")
    public Result getDesCSV() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = algalChronicService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据在转为csv文件时出错！");
        }
        int vldSize = algalChronicService.getDesFile(vldDesFile, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据在转为csv文件时出错！");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

}
