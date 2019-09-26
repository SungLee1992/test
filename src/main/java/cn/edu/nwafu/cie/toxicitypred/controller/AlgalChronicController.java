package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
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
public class AlgalChronicController {
    private static final Logger logger = LoggerFactory.getLogger(AlgalChronicController.class);
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
     * 藻类慢性毒性记录的mol文件存放路径
     **/
    private static String vldMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/algalchronic/vldfiles";
    private static String trainMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/algalchronic/trainfiles";
    /**
     * 藻类慢性毒性记录的描述符txt文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/trainfiles"; //txt文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/vldfiles";  //txt文件路径（验证集）

    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/vlddes.csv";
    private static String trainAsVldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/trainasvlddes.csv";
    /**
     * 藻类慢性毒性记录新进文件存放路径
     **/
    private static String newMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/algalchronic/new/"; //mop文件路径（新进）
    private static String newOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/algalchronic/new/"; //out文件路径（新进）
    private static String newMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/algalchronic/new/"; //mol文件路径（新进）
    private static String newDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/new/"; //描述符文件路径（新进）
    private static String newDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/algalchronic/newdes.csv";     //新记录的knn文件

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
            return Result.errorMsg("藻类慢性毒性验证集数据out文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/smitrains")
    public Result getTrainSmiFiles() {
        int size = algalChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据out文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/smifiles")
    public Result getSmiFiles() {
        int trainSize = algalChronicService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        int vldSize = algalChronicService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据out文件转为smi文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据out文件转为smi文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** out->mol ****************************************************/
    @RequestMapping("/algchr/molvlds")
    public Result getVldMolFiles() {
        int size = algalChronicService.outFilesToMolFiles(vldOutFilesPath, vldMolFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据out文件转为mol文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/moltrains")
    public Result getTrainMolFiles() {
        int size = algalChronicService.outFilesToMolFiles(trainOutFilesPath, trainMolFilesPath);
        if (size == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据out文件转为mol文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/algchr/molfiles")
    public Result getMolFiles() {
        int trainSize = algalChronicService.outFilesToMolFiles(trainOutFilesPath, trainMolFilesPath);
        int vldSize = algalChronicService.outFilesToMolFiles(vldOutFilesPath, vldMolFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据out文件转为mol文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据out文件转为mol文件的数量为0！");
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

    /*************************************************** mol->描述符 ****************************************************/
    @RequestMapping("/algchr/moldestrains")
    public Result getTrainTxtFiles() {
        int trainSize = algalChronicService.molFilesToDragonOutFiles(trainMolFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("藻类慢性毒性训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }
    @RequestMapping("/algchr/moldesvlds")
    public Result getVldTxtFiles() {
        int vldSize = algalChronicService.molFilesToDragonOutFiles(vldMolFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("藻类慢性毒性验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/algchr/moldesfiles")
    public Result getTxtFiles() {
        int trainSize = algalChronicService.molFilesToDragonOutFiles(trainMolFilesPath, trainDragonOutFilesPath);
        int vldSize = algalChronicService.molFilesToDragonOutFiles(vldMolFilesPath, vldDragonOutFilesPath);
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
        int trainUpdateSize = algalChronicService.updateDescriptions(trainDragonOutFilesPath,AlgalChronic.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("藻类慢性毒性训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/algchr/vlddestodb")
    public Result updateVldDesToDB(){
        int vldUpdateSize = algalChronicService.updateDescriptions(vldDragonOutFilesPath,AlgalChronic.class,"validate");
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
        int vldUpdateSize = algalChronicService.updateDescriptions(vldDragonOutFilesPath,AlgalChronic.class,"validate");
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

    @RequestMapping("/algchr/trainasvldcsv")
    public Result getTrainAsVldCSV() {
        try {
            File trainAsVldDesFile = algalChronicService.getTrainAsVldCSV(trainAsVldDesFilePath,trainDesFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorMsg("藻类慢性毒性训练集数据在转为csv文件时出错！");
        }
        return Result.successWithoutData();
    }

    @RequestMapping("/algchr/vldknn")
    public Result vldKnnPre() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        Map<String, String> knnMap = algalChronicService.runKnn(trainDesFile, vldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += algalChronicService.updatePreValueByCasNo(entry.getKey(), entry.getValue(), "validate");
        }
        return Result.success(numOfUpdatePreValues);
    }

    @RequestMapping("/algchr/trainknn")
    public Result trainKnnPre() {
        File trainDesFile = new File(trainDesFilePath);
        File trainAsVldDesFile = new File(trainAsVldDesFilePath);

        Map<String, String> knnMap = algalChronicService.runKnn(trainDesFile, trainAsVldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += algalChronicService.updatePreValueByCasNo(entry.getKey(), entry.getValue(), "train");
        }
        return Result.success(numOfUpdatePreValues);
    }

    /***************************************************新进化合物的处理方法****************************************************/
    @RequestMapping("/algalchr/knn")
    public Result pre(@RequestParam("casno") String casNo, @RequestParam("smiles") String smiles) throws Exception {
        List<AlgalChronic> algalChronicList = algalChronicService.getByCasNo(casNo);
        /*if (daphniaAcuteList != null) {
            return Result.success(daphniaAcuteList.get(0));
        }*/
        if (!FileUtil.validateDir(newMopFilesPath)) {
            return Result.errorMsg("新化合物的mop文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newOutFilesPath)) {
            return Result.errorMsg("新化合物的out文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newMolFilesPath)) {
            return Result.errorMsg("新化合物的mol文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newDragonOutFilesPath)) {
            return Result.errorMsg("新化合物的描述符文件存储目录错误！");
        }
        //进入OpenBabel，生成mop文件
        boolean flag = algalChronicService.smiStrToMopFile(newMopFilesPath,smiles,casNo);
        if (!flag) {
            logger.error(casNo+" OpenBabel生成mop文件出错！");
            return null;
        }
        //进入Mopac，生成out文件
        File newMopFile = new File(newMopFilesPath + casNo + ".mop");
        flag = algalChronicService.mopFileToOutFile(newMopFile);
        int moveSize = algalChronicService.moveOutFiles(newMopFilesPath, newOutFilesPath);     //把生成的out文件及附属文件移动到新out目录中
        if(!flag || moveSize<1){
            logger.error(casNo+" Mopac计算出错！");
            return null;
        }
        //进入Openbabel，生成mol文件
        File newOutFile = new File(newOutFilesPath + casNo + ".out");
        flag = algalChronicService.outFileToMolFile(newOutFile,newMolFilesPath);
        if(!flag){
            logger.error(casNo+" OpenBabel生成mol文件出错!");
            return null;
        }
        //mol文件进入dragon，生成txt文件
        File newMolFile = new File(newMolFilesPath + casNo + ".mol");
        flag = algalChronicService.molFileToDragonOutFile(newMolFile,newDragonOutFilesPath);
        if(!flag){
            logger.error(casNo+" dragon生成描述符文件出错!");
            return null;
        }
        //提取描述符到实体中
        AlgalChronic newAlgalChronic =null;
        File newDragonOutFile = new File(newDragonOutFilesPath + casNo + ".txt");
        newAlgalChronic = algalChronicService.getDescription(newDragonOutFile, AlgalChronic.class);
        newAlgalChronic.setDatatype("new");
        newAlgalChronic.setSmiles(smiles);
        //构造用于knn的新csv文件
        flag = algalChronicService.getDesFile(new File(newDesFilePath), newAlgalChronic);
        if(!flag){
            return Result.errorMsg("生成csv文件时出错！");
        }
        //knn
        Map<String, String> knnMap = algalChronicService.runKnn(new File(trainDesFilePath), new File(newDesFilePath));
        newAlgalChronic.setPreValue(knnMap.get(casNo));
        //新纪录插入至数据库
        algalChronicService.insert(newAlgalChronic);
        return Result.success(newAlgalChronic);
    }

    @RequestMapping("/algalchr/calculator")
    public Result calculate() throws Exception {
        HashMap<String,Object> modelMap = new HashMap<>();
        modelMap.put("trainModel",algalChronicService.calculateModel("train"));
        modelMap.put("validateModel",algalChronicService.calculateModel("validate"));
        return Result.success(modelMap);
    }

}
