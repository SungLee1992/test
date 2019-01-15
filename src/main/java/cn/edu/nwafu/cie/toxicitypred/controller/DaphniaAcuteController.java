package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
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
public class DaphniaAcuteController {
    private static final Logger logger = LoggerFactory.getLogger(DaphniaAcuteController.class);

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
    private static String trainAsVldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/trainasvlddes.csv";

    /**
     * 溞类急性毒性记录新进文件存放路径
     **/
    private static String newSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/daphniaacute/new/"; //smi文件路径（新进）
    private static String newDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/new/"; //描述符文件路径（新进）
    private static String newDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/daphniaacute/newdes.csv";     //新记录的knn文件
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
        int vldUpdateSize = daphniaAcuteService.updateDescriptions(vldDragonOutFilesPath,DaphniaAcute.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("溞类急性毒性验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/dapact/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = daphniaAcuteService.updateDescriptions(trainDragonOutFilesPath,DaphniaAcute.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("溞类急性毒性训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = daphniaAcuteService.updateDescriptions(vldDragonOutFilesPath,DaphniaAcute.class,"validate");
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

    @RequestMapping("/dapact/trainasvldcsv")
    public Result getTrainAsVldCSV() {
        try {
            File trainAsVldDesFile = daphniaAcuteService.getTrainAsVldCSV(trainAsVldDesFilePath,trainDesFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorMsg("溞类急性毒性训练集数据在转为csv文件时出错！");
        }
        return Result.successWithoutData();
    }

    @RequestMapping("/dapact/vldknn")
    public Result vldKnnPre(){
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        Map<String, String> knnMap = daphniaAcuteService.runKnn(trainDesFile,vldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += daphniaAcuteService.updatePreValueByCasNo(entry.getKey(),entry.getValue(),"validate");
        }
        return Result.success(numOfUpdatePreValues);
    }

    @RequestMapping("/dapact/trainknn")
    public Result trainKnnPre(){
        File trainDesFile = new File(trainDesFilePath);
        File trainAsVldDesFile = new File(trainAsVldDesFilePath);

        Map<String, String> knnMap = daphniaAcuteService.runKnn(trainDesFile,trainAsVldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += daphniaAcuteService.updatePreValueByCasNo(entry.getKey(),entry.getValue(),"train");
        }
        return Result.success(numOfUpdatePreValues);
    }

    /*************************************************** 新进化合物的处理方法 ****************************************************/
    @RequestMapping("/dapact/knn")
    public Result pre(@RequestParam("casno") String casNo, @RequestParam("smiles") String smiles) throws Exception {
        List<DaphniaAcute> daphniaAcuteList = daphniaAcuteService.getByCasNo(casNo);
        /*if (daphniaAcuteList != null) {
            return Result.success(daphniaAcuteList.get(0));
        }*/
        if (!FileUtil.validateDir(newSmiFilesPath)) {
            return Result.errorMsg("新化合物的smi文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newDragonOutFilesPath)) {
            return Result.errorMsg("新化合物的描述符文件存储目录错误！");
        }
        //生成smi文件
        File newSmiFile = new File(newSmiFilesPath + casNo + ".smi");
        boolean flag = daphniaAcuteService.writeFile(newSmiFile, smiles, false);
        if (!flag) {
            logger.error(casNo+"生成smi文件出错！");
            return null;
        }
        //进入dragon，转为描述符文件
        flag = daphniaAcuteService.smiFileToDragonOutFile(newSmiFile, newDragonOutFilesPath);
        if(!flag){
            logger.error(casNo+" dragon生成描述符文件出错!");
            return null;
        }
        //提取描述符到实体中
        DaphniaAcute newDaphniaAcute = null;
        File newDragonOutFile = new File(newDragonOutFilesPath + casNo + ".txt");
        newDaphniaAcute = daphniaAcuteService.getDescription(newDragonOutFile, DaphniaAcute.class);
        newDaphniaAcute.setDatatype("new");
        newDaphniaAcute.setSmiles(smiles);
        //构造用于knn的新csv文件
        flag = daphniaAcuteService.getDesFile(new File(newDesFilePath), newDaphniaAcute);
        if(!flag){
            return Result.errorMsg("生成csv文件时出错！");
        }
        //knn
        Map<String, String> knnMap = daphniaAcuteService.runKnn(new File(trainDesFilePath), new File(newDesFilePath));
        newDaphniaAcute.setPreValue(knnMap.get(casNo));
        //新纪录插入至数据库
        daphniaAcuteService.insert(newDaphniaAcute);
        return Result.success(newDaphniaAcute);
    }
}
