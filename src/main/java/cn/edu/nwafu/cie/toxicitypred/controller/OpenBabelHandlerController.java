package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SungLee
 * @date: 2018-10-15 16:49
 * @description: openbabel软件的相关处理
 */
@RestController
public class OpenBabelHandlerController {
    @RequestMapping("/mop")
    public Result smiToMop(){
        return null;
    }
}
