package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
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

    @RequestMapping("/algchr/smitrains")
    public Result getSmiFile(){
        daphniaAcuteService.getSmiFiles(trainSmiFilesPath,"train");
        return Result.successWithoutData();
    }
}
