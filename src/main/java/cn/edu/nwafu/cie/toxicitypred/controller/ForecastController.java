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

    @Autowired
    private FishChronicController fishChronicController;
    @Autowired
    private DaphniaChronicController daphniaChronicController;
    @Autowired
    private AlgalChronicController algalChronicController;
    @Autowired
    private DaphniaAcuteController daphniaAcuteController;


    @RequestMapping("/pre")
    public Result toxityPred(@RequestParam("type") String[] typeAry, @RequestParam("casNo") String casNo, @RequestParam("smiles") String smiles) throws IOException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Map<String, Object> data = new HashMap<>();
        for (String typeItem : typeAry) {// 数据预处理:数据库中存在时直接返回，不存在时调用接口进行预处理，将结果存入数据库
            Object object = null;
            switch (typeItem) {
                case "1":
                    // 查询数据库
                    List<DaphniaChronic> daphniaChronicList = daphniaChronicService.getByCasNo(casNo);
                    if (daphniaChronicList != null && daphniaChronicList.size() > 0) {
                        object = daphniaChronicList.get(0);
                        break;
                    }
                    try {// 数据库不存在时预测
                        object = daphniaChronicController.pre(casNo, smiles).getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    // 查询数据库
                    List<FishChronic> fishChronicList = fishChronicService.getByCasNo(casNo);
                    if (fishChronicList != null && fishChronicList.size() > 0) {
                        object = fishChronicList.get(0);
                        break;
                    }
                    try {// 数据库不存在时预测
                        object = fishChronicController.pre(casNo, smiles).getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    // 查询数据库
                    List<AlgalChronic> algalChronicList = algalChronicService.getByCasNo(casNo);
                    if (algalChronicList != null && algalChronicList.size() > 0) {
                        object = algalChronicList.get(0);
                        break;
                    }
                    try {// 数据库不存在时预测
                        object = algalChronicController.pre(casNo, smiles).getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    // 查询数据库
                    List<DaphniaAcute> daphniaAcuteList = daphniaAcuteService.getByCasNo(casNo);
                    if (daphniaAcuteList != null && daphniaAcuteList.size() > 0) {
                        object = daphniaAcuteList.get(0);
                        break;
                    }
                    try {// 数据库不存在时预测
                        object = daphniaAcuteController.pre(casNo, smiles).getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (object != null) {
                data.put(typeItem, object);
            }
        }

        return Result.success(data);
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
