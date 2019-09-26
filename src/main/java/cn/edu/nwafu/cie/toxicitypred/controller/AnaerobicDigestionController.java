package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.AnaerobicDigestion;
import cn.edu.nwafu.cie.toxicitypred.service.AnaerobicDigestionService;
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
 * @description: 1=A=有毒,0=I=无毒
 */
@RestController
public class AnaerobicDigestionController {
    private static final Logger logger = LoggerFactory.getLogger(AnaerobicDigestionController.class);

    @Autowired
    private AnaerobicDigestionService anaerobicDigestionService;
    /**
     * 厌氧消化模型记录的smi文件存放路径
     **/
    private static String trainSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/anaerobicdigestion/trainfiles"; //smi文件路径（训练集）
    private static String vldSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/anaerobicdigestion/vldfiles";  //smi文件路径（验证集）

    /**
     * 厌氧消化模型记录的mop文件存放路径
     **/
    private static String trainMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/anaerobicdigestion/trainfiles";  //暂时规定生成mop文件的路径
    private static String vldMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/anaerobicdigestion/vldfiles";  //暂时规定生成mop文件的路径
    /**
     * 厌氧消化模型记录的out文件存放路径
     **/
    private static String trainOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/anaerobicdigestion/trainfiles"; //out文件路径（训练集）
    private static String vldOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/anaerobicdigestion/vldfiles";  //out文件路径（验证集）/**
    /**
     * 厌氧消化模型记录的mol文件存放路径
     **/
    private static String vldMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/anaerobicdigestion/vldfiles";
    private static String trainMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/anaerobicdigestion/trainfiles";
    /**
     * 厌氧消化模型记录的描述符文件存放路径
     **/
    private static String trainDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/trainfiles"; //描述符文件路径（训练集）
    private static String vldDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/vldfiles";  //描述符文件路径（验证集）

    private static String trainDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/traindes.csv";
    private static String vldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/vlddes.csv";
    private static String trainAsVldDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/trainasvlddes.csv";

    /**
     * 厌氧消化模型记录新进文件存放路径
     **/
    private static String newSmiFilesPath = System.getProperty("user.dir") + "/files/smifiles/anaerobicdigestion/new/"; //smi文件路径（新进）
    private static String newDragonOutFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/new/"; //描述符文件路径（新进）
    private static String newDesFilePath = System.getProperty("user.dir") + "/files/dragonoutfiles/anaerobicdigestion/newdes.csv";     //新记录的knn文件
    /*************************************************** smiles->smi文件 ****************************************************/
    /*@RequestMapping("/ad/smitrains")
    public Result getTrainSmiFile() {
        int trainSize = anaerobicDigestionService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/ad/smivlds")
    public Result getVldSmiFile() {
        int vldSize = anaerobicDigestionService.getSmiFiles(vldSmiFilesPath, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/ad/smifiles")
    public Result getSmiFile() {
        int vldSize = anaerobicDigestionService.getSmiFiles(vldSmiFilesPath, "validate");
        int trainSize = anaerobicDigestionService.getSmiFiles(trainSmiFilesPath, "train");
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据写入smi文件的数量为0，检查smi文件的保存目录！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }*/

    /*************************************************** smi->mop ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 厌氧消化模型训练集数据转为mop文件
     */
    @RequestMapping("/ad/trainmops")
    public Result getTrainMopFiles() {
        int size = anaerobicDigestionService.smiStrToMopFiles(trainMopFilesPath, "train");
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 厌氧消化模型验证集数据转为mop文件
     */
    @RequestMapping("/ad/vldmops")
    public Result getVldMopFiles() {
        int size = anaerobicDigestionService.smiStrToMopFiles(vldMopFilesPath, "validate");
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据转为mop文件的数量为0！");
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
     * 厌氧消化模型训练集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/ad/trainouts")
    public Result getTrainOutFiles() {
        int size = anaerobicDigestionService.mopFilesToOutFiles(trainMopFilesPath);
        int moveSize = anaerobicDigestionService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 厌氧消化模型验证集mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/ad/vldouts")
    public Result getVldOutFiles() {
        int size = anaerobicDigestionService.mopFilesToOutFiles(vldMopFilesPath);
        int moveSize = anaerobicDigestionService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * 厌氧消化模型mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/ad/outs")
    public Result getOutFiles() {
        int vldSize = anaerobicDigestionService.mopFilesToOutFiles(vldMopFilesPath);
        int vldMoveSize = anaerobicDigestionService.moveOutFiles(vldMopFilesPath, vldOutFilesPath);
        int trainSize = anaerobicDigestionService.mopFilesToOutFiles(trainMopFilesPath);
        int trainMoveSize = anaerobicDigestionService.moveOutFiles(trainMopFilesPath, trainOutFilesPath);

        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据mop文件转为out文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据mop文件转为out文件的数量为0！");
        }
        if (trainMoveSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据移动out文件的数量为0！");
        }
        if (vldMoveSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据移动out文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("trainFlag", trainMoveSize);
        resultMap.put("vldSize", vldSize);
        resultMap.put("vldFlag", vldMoveSize);
        return Result.success(resultMap);
    }

    /*************************************************** out->smi ****************************************************/
    /*@RequestMapping("/ad/smivlds")
    public Result getVldSmiFiles() {
        int size = anaerobicDigestionService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据out文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/ad/smitrains")
    public Result getTrainSmiFiles() {
        int size = anaerobicDigestionService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据out文件转为smi文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/ad/smifiles")
    public Result getSmiFiles() {
        int trainSize = anaerobicDigestionService.outFilesToSmiFiles(trainOutFilesPath, trainSmiFilesPath);
        int vldSize = anaerobicDigestionService.outFilesToSmiFiles(vldOutFilesPath, vldSmiFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据out文件转为smi文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据out文件转为smi文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }*/

    /*************************************************** out->mol ****************************************************/
    @RequestMapping("/ad/molvlds")
    public Result getVldMolFiles() {
        int size = anaerobicDigestionService.outFilesToMolFiles(vldOutFilesPath, vldMolFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据out文件转为mol文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/ad/moltrains")
    public Result getTrainMolFiles() {
        int size = anaerobicDigestionService.outFilesToMolFiles(trainOutFilesPath, trainMolFilesPath);
        if (size == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据out文件转为mol文件的数量为0！");
        }
        return Result.success(size);
    }

    @RequestMapping("/ad/molfiles")
    public Result getMolFiles() {
        int trainSize = anaerobicDigestionService.outFilesToMolFiles(trainOutFilesPath, trainMolFilesPath);
        int vldSize = anaerobicDigestionService.outFilesToMolFiles(vldOutFilesPath, vldMolFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据out文件转为mol文件的数量为0！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据out文件转为mol文件的数量为0！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** mol->描述符 ****************************************************/
    @RequestMapping("/ad/moldestrains")
    public Result getTrainTxtFiles() {
        int trainSize = anaerobicDigestionService.molFilesToDragonOutFiles(trainMolFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }
    @RequestMapping("/ad/moldesvlds")
    public Result getVldTxtFiles() {
        int vldSize = anaerobicDigestionService.molFilesToDragonOutFiles(vldMolFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/ad/moldesfiles")
    public Result getTxtFiles() {
        int trainSize = anaerobicDigestionService.molFilesToDragonOutFiles(trainMolFilesPath, trainDragonOutFilesPath);
        int vldSize = anaerobicDigestionService.molFilesToDragonOutFiles(vldMolFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据dragon转换出错！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    /*************************************************** smi->描述符 ****************************************************/
    /*@RequestMapping("/ad/destrains")
    public Result getTrainDragonOutFiles() {
        int trainSize = anaerobicDigestionService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    @RequestMapping("/ad/desvlds")
    public Result getVldDragonOutFiles() {
        int vldSize = anaerobicDigestionService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据dragon转换出错！");
        }
        return Result.success(vldSize);
    }

    @RequestMapping("/ad/desfiles")
    public Result getDragonOutFiles() {
        int trainSize = anaerobicDigestionService.smiFilesToDragonOutFiles(trainSmiFilesPath, trainDragonOutFilesPath);
        int vldSize = anaerobicDigestionService.smiFilesToDragonOutFiles(vldSmiFilesPath, vldDragonOutFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据dragon转换出错！");
        }
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据dragon转换出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }*/

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/ad/traindestodb")
    public Result updateTrainDesToDB(){
        int trainUpdateSize = anaerobicDigestionService.updateDescriptions(trainDragonOutFilesPath, AnaerobicDigestion.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("厌氧消化模型训练集数据的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/ad/vlddestodb")
    public Result updateVldDesToDB(){
        int vldUpdateSize = anaerobicDigestionService.updateDescriptions(vldDragonOutFilesPath,AnaerobicDigestion.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("厌氧消化模型验证集数据的描述符在更新数据库时出错！");
        }
        return Result.success(vldUpdateSize);
    }

    @RequestMapping("/ad/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = anaerobicDigestionService.updateDescriptions(trainDragonOutFilesPath,AnaerobicDigestion.class,"train");
        if(trainUpdateSize==0){
            return Result.errorMsg("厌氧消化模型训练集数据的描述符在更新数据库时出错！");
        }
        int vldUpdateSize = anaerobicDigestionService.updateDescriptions(vldDragonOutFilesPath,AnaerobicDigestion.class,"validate");
        if(vldUpdateSize==0){
            return Result.errorMsg("厌氧消化模型验证集数据的描述符在更新数据库时出错！");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainUpdateSize", trainUpdateSize);
        resultMap.put("vldUpdateSize", vldUpdateSize);
        return Result.success(resultMap);
    }

    /*************************************************** 数据预处理已全部完成，只剩knn ****************************************************/
    @RequestMapping("/ad/descsv")
    public Result getDesCSV() {
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        int trainSize = anaerobicDigestionService.getDesFile(trainDesFile, "train");
        if (trainSize == 0) {
            return Result.errorMsg("厌氧消化模型训练集数据在转为csv文件时出错！");
        }
        int vldSize = anaerobicDigestionService.getDesFile(vldDesFile, "validate");
        if (vldSize == 0) {
            return Result.errorMsg("厌氧消化模型验证集数据在转为csv文件时出错！");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("trainSize", trainSize);
        resultMap.put("vldSize", vldSize);
        return Result.success(resultMap);
    }

    @RequestMapping("/ad/trainasvldcsv")
    public Result getTrainAsVldCSV() {
        try {
            File trainAsVldDesFile = anaerobicDigestionService.getTrainAsVldCSV(trainAsVldDesFilePath,trainDesFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.errorMsg("厌氧消化模型训练集数据在转为csv文件时出错！");
        }
        return Result.successWithoutData();
    }

    @RequestMapping("/ad/vldknn")
    public Result vldKnnPre(){
        File trainDesFile = new File(trainDesFilePath);
        File vldDesFile = new File(vldDesFilePath);
        Map<String, String> knnMap = anaerobicDigestionService.runKnn(trainDesFile,vldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += anaerobicDigestionService.updatePreValueByCasNo(entry.getKey(),entry.getValue(),"validate");
        }
        return Result.success(numOfUpdatePreValues);
    }

    @RequestMapping("/ad/trainknn")
    public Result trainKnnPre(){
        File trainDesFile = new File(trainDesFilePath);
        File trainAsVldDesFile = new File(trainAsVldDesFilePath);

        Map<String, String> knnMap = anaerobicDigestionService.runKnn(trainDesFile,trainAsVldDesFile);
        int numOfUpdatePreValues = 0;
        for (Map.Entry<String, String> entry : knnMap.entrySet()) {
            numOfUpdatePreValues += anaerobicDigestionService.updatePreValueByCasNo(entry.getKey(),entry.getValue(),"train");
        }
        return Result.success(numOfUpdatePreValues);
    }

    /*************************************************** 新进化合物的处理方法 ****************************************************/
    @RequestMapping("/ad/knn")
    public Result pre(@RequestParam("casno") String casNo, @RequestParam("smiles") String smiles) throws Exception {
        List<AnaerobicDigestion> anaerobicDigestionList = anaerobicDigestionService.getByCasNo(casNo);
        /*if (AnaerobicDigestionList != null) {
            return Result.success(AnaerobicDigestionList.get(0));
        }*/
        if (!FileUtil.validateDir(newSmiFilesPath)) {
            return Result.errorMsg("新化合物的smi文件存储目录错误！");
        }
        if (!FileUtil.validateDir(newDragonOutFilesPath)) {
            return Result.errorMsg("新化合物的描述符文件存储目录错误！");
        }
        //生成smi文件
        File newSmiFile = new File(newSmiFilesPath + casNo + ".smi");
        boolean flag = anaerobicDigestionService.writeFile(newSmiFile, smiles, false);
        if (!flag) {
            logger.error(casNo+"生成smi文件出错！");
            return null;
        }
        //进入dragon，转为描述符文件
        flag = anaerobicDigestionService.smiFileToDragonOutFile(newSmiFile, newDragonOutFilesPath);
        if(!flag){
            logger.error(casNo+" dragon生成描述符文件出错!");
            return null;
        }
        //提取描述符到实体中
        AnaerobicDigestion anaerobicDigestion = null;
        File newDragonOutFile = new File(newDragonOutFilesPath + casNo + ".txt");
        anaerobicDigestion = anaerobicDigestionService.getDescription(newDragonOutFile, AnaerobicDigestion.class);
        anaerobicDigestion.setDatatype("new");
        anaerobicDigestion.setSmiles(smiles);
        //构造用于knn的新csv文件
        flag = anaerobicDigestionService.getDesFile(new File(newDesFilePath), anaerobicDigestion);
        if(!flag){
            return Result.errorMsg("生成csv文件时出错！");
        }
        //knn
        Map<String, String> knnMap = anaerobicDigestionService.runKnn(new File(trainDesFilePath), new File(newDesFilePath));
        anaerobicDigestion.setPreValue(knnMap.get(casNo));
        //新纪录插入至数据库
        anaerobicDigestionService.insert(anaerobicDigestion);
        return Result.success(anaerobicDigestion);
    }
}
