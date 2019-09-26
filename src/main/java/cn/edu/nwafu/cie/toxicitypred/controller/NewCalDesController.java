package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.NewCalDes;
import cn.edu.nwafu.cie.toxicitypred.service.NewCalDesService;
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
public class NewCalDesController {
    @Autowired
    private NewCalDesService newCalDesService;
    /**
     * 藻类慢性毒性记录的mop文件存放路径
     **/
    private static String newCalDesMopFilesPath = System.getProperty("user.dir") + "/files/mopfiles/newcaldes";  //暂时规定生成mop文件的路径
    /**
     * 藻类慢性毒性记录的out文件存放路径
     **/
    private static String newCalDesOutFilesPath = System.getProperty("user.dir") + "/files/outfiles/newcaldes"; //out文件路径
    /**
     * 藻类慢性毒性记录的mol文件存放路径
     **/
    private static String newCalDesMolFilesPath = System.getProperty("user.dir") + "/files/molfiles/newcaldes";
    /**
     * 藻类慢性毒性记录的描述符txt文件存放路径
     **/
    private static String newCalDesTxtFilesPath = System.getProperty("user.dir") + "/files/dragonoutfiles/newcaldes"; //txt文件路径

    /*************************************************** smi->mop ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * smi字符串转为mop文件
     */
    @RequestMapping("/newcaldes/mops")
    public Result getNewCalDesMopFiles() {
        int size = newCalDesService.smiStrToMopFiles(newCalDesMopFilesPath);
        if (size == 0) {
            return Result.errorMsg("60条新纪录转为mop文件的数量为0！");
        }
        return Result.success(size);
    }

    /*************************************************** mop->out ****************************************************/
    /**
     * @param: []
     * @return: cn.edu.nwafu.cie.toxicitypred.common.Result
     * mop文件经过MOPAC转为out文件
     */
    @RequestMapping("/newcaldes/outs")
    public Result getNewCalDesOutFiles() {
        int size = newCalDesService.mopFilesToOutFiles(newCalDesMopFilesPath);
        int moveSize = newCalDesService.moveOutFiles(newCalDesMopFilesPath, newCalDesOutFilesPath);
        if (size == 0) {
            return Result.errorMsg("60条新纪录mop文件转为out文件的数量为0！");
        }
        if (moveSize == 0) {
            return Result.errorMsg("60条新纪录移动out文件的数量为0！");
        }
        return Result.success(size);
    }

    /*************************************************** out->mol ****************************************************/
    @RequestMapping("/newcaldes/mols")
    public Result getNewCalDesMolFiles() {
        int size = newCalDesService.outFilesToMolFiles(newCalDesOutFilesPath, newCalDesMolFilesPath);
        if (size == 0) {
            return Result.errorMsg("60条新纪录out文件转为mol文件的数量为0！");
        }
        return Result.success(size);
    }

    /*************************************************** mol->描述符 ****************************************************/
    @RequestMapping("/newcaldes/txts")
    public Result getNewCalDesTxtFiles() {
        int trainSize = newCalDesService.molFilesToDragonOutFiles(newCalDesMolFilesPath, newCalDesTxtFilesPath);
        if (trainSize == 0) {
            return Result.errorMsg("60条新纪录dragon转换出错！");
        }
        return Result.success(trainSize);
    }

    /*************************************************** 将dragon生成的描述符提取出来，更新数据库中的记录 ****************************************************/
    @RequestMapping("/newcaldes/destodb")
    public Result updateDesToDB(){
        int trainUpdateSize = newCalDesService.updateDescriptions(newCalDesTxtFilesPath, NewCalDes.class,null);
        if(trainUpdateSize==0){
            return Result.errorMsg("60条新纪录的描述符在更新数据库时出错！");
        }
        return Result.success(trainUpdateSize);
    }

    @RequestMapping("/newcaldes/calculator")
    public Result calculate() throws Exception {
        HashMap<String,Object> modelMap = new HashMap<>();
        modelMap.put("trainModel",newCalDesService.calculateModel("train"));
        modelMap.put("validateModel",newCalDesService.calculateModel("validate"));
        return Result.success(modelMap);
    }
}
