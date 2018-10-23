package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
     * 溞类急性毒性记录的smi文件存放路径
     **/
    private static String trainDragonOutPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/trainfiles"; //smi文件路径（训练集）
    private static String vldDragonOutPath = System.getProperty("user.dir") + "/files/dragonoutfiles/fishchronic/vldfiles";  //smi文件路径（验证集）

}
