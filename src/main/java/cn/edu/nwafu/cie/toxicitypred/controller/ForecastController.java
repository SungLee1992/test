package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.CommandConstant;
import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.dao.AlgalChronicDao;
import cn.edu.nwafu.cie.toxicitypred.dao.DaphniaAcuteDao;
import cn.edu.nwafu.cie.toxicitypred.dao.DaphniaChronicDao;
import cn.edu.nwafu.cie.toxicitypred.dao.FishChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.*;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song_Lee on 2018/10/10.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/forecast")
public class ForecastController {
    @Autowired
    private DaphniaAcuteDao daphniaAcuteDao;
    @Autowired
    private DaphniaChronicDao daphniaChronicDao;
    @Autowired
    private FishChronicDao fishChronicDao;
    @Autowired
    private AlgalChronicDao algalChronicDao;
    private static boolean ISUPDATE = false;

    @Autowired
    private DaphniaChronicService daphniaChronicService;
    @Autowired
    private FishChronicService fishChronicService;
    @Autowired
    private AlgalChronicService algalChronicService;
    @Autowired
    private DaphniaAcuteService daphniaAcuteService;


    @RequestMapping("/pre")
    public Result toxityPred(@RequestParam("type") String type, @RequestParam("casno") String casNo, @RequestParam("smiles") String smiles) throws IOException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String[] typeAry = type.split(",");
        Result result = new Result();
        for (String typeItem : typeAry) {
            switch (typeItem) {
                case "1": // 大型溞慢性毒素 DaphniaChronic
                    List<DaphniaChronic> daphniaChronicList = daphniaChronicService.getByCasNo(casNo);
                    if (daphniaChronicList != null) {
                        return Result.success(daphniaChronicList.get(0));
                    }
                    //TODO 数据处理部分

                    //return Result.success(daphniaChronicService.getByCasNo(casNo));
                    break;
                case "2":
                    try {
                        result = new FishChronicController().pre(casNo, smiles);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
                case "3":
                    List<AlgalChronic> algalChronicList = algalChronicService.getByCasNo(casNo);
                    if (algalChronicList != null) {
                        return Result.success(algalChronicList.get(0));
                    }
                    //TODO 数据处理部分

                    //return Result.success(algalChronicService.getByCasNo(casNo));
                    break;

                case "4":
                    List<DaphniaAcute> daphniaAcuteList = daphniaAcuteService.getByCasNo(casNo);
                    if (daphniaAcuteList != null) {
                        return Result.success(daphniaAcuteList.get(0));
                    }
                    //TODO 数据处理部分

                    //return Result.success(daphniaAcuteService.getByCasNo(casNo));
                    break;
            }

        }

        return null;
    }


    @RequestMapping("/single")
    public Object hello(String type, String CASNO, String SMILESNO) {
        Response response = new Response();
        String[] typeAry = type.split(",");
        Map<String, Object> data = new HashMap<>();
        // 数据预处理:数据库中存在时直接返回，不存在时调用接口进行预处理，将结果存入数据库
        Object object = null;
        for (String typeItem : typeAry) {
            switch (typeItem) {
                case "1":// 大型溞慢性毒素 DaphniaChronic
                    object = daphniaChronicDao.getByCasNo(CASNO);
                    if (object != null && !ISUPDATE) {
                        break;
                    }
                    // TODO 调用数据预处理接口获取描述符
                    object = new DaphniaChronic();
                    // TODO 调用预测接口获取预测值
                    if (ISUPDATE) {
                        //daphniaChronicDao.updateByCasno((DaphniaChronic)object);
                    } else {
                        //daphniaChronicDao.insertRecord((DaphniaChronic) object);
                    }
                    break;
                case "2":// 鱼类慢性毒素 FishChronic
                    object = fishChronicDao.getByCasNo(CASNO);
                    if (object != null) {
                        break;
                    }
                    // TODO 调用数据预处理接口获取描述符
                    object = new FishChronic();
                    // TODO 调用预测接口获取预测值
                    if (ISUPDATE) {
                        //fishChronicDao.updateByCasno((FishChronic)object);
                    } else {
                        //fishChronicDao.insertRecord((FishChronic) object);
                    }
                    break;
                case "3":// 藻类慢性毒素 AlgalChronic
                    object = algalChronicDao.getByCasNo(CASNO);
                    if (object != null) {
                        break;
                    }
                    // TODO 调用数据预处理接口获取描述符
                    object = new AlgalChronic();
                    // TODO 调用预测接口获取预测值
                    if (ISUPDATE) {
                        //algalChronicDao.updateByCasno((AlgalChronic)object);
                    } else {
                        //algalChronicDao.insertRecord((AlgalChronic) object);
                    }
                    break;
                case "4":// 溞类急性毒素 DaphniaAcute
                    object = daphniaAcuteDao.getByCasNo(CASNO);
                    if (object != null) {
                        break;
                    }
                    // TODO 调用数据预处理接口获取描述符
                    object = new DaphniaAcute();
                    // TODO 调用预测接口获取预测值
                    if (ISUPDATE) {
                        //daphniaAcuteDao.updateByCasno((DaphniaAcute)object);
                    } else {
                        // daphniaAcuteDao.insertRecord((DaphniaAcute) object);
                    }
                    break;
                default:

                    break;
            }

            data.put(typeItem, object);
        }
        // 返回结果
        response.data = data;
        return response;
    }
}
