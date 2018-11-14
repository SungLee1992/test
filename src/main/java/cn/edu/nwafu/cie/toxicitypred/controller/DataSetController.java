package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaAcuteService;
import cn.edu.nwafu.cie.toxicitypred.service.DaphniaChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.FishChronicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据集展示控制器
 * Created by Song_Lee on 2018/11/14.
 */
@RestController
@RequestMapping("/data/set")
public class DataSetController {
    @Autowired
    private FishChronicService fishChronicService;
    @Autowired
    private DaphniaAcuteService daphniaAcuteService;
    @Autowired
    private DaphniaChronicService daphniaChronicService;
    @Autowired
    private AlgalChronicService algalChronicService;

    @PostMapping("/daphchr/list")
    public Result daphChrList(@RequestParam String dataType, @RequestParam int pageNo, @RequestParam int pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNo, pageSize);
        List<DaphniaChronic> daphniaChronicList = daphniaChronicService.getByDataType(dataType);
        PageInfo<DaphniaChronic> pageInfo = new PageInfo<>(daphniaChronicList);
        map.put("data", daphniaChronicList);
        map.put("total", pageInfo.getTotal());
        return Result.success(map);
    }

    @PostMapping("/fishchr/list")
    public Result fishChrList(@RequestParam String dataType, @RequestParam int pageNo, @RequestParam int pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNo, pageSize);
        List<FishChronic> fishChronicList = fishChronicService.getByDataType(dataType);
        PageInfo<FishChronic> pageInfo = new PageInfo<>(fishChronicList);
        map.put("data", fishChronicList);
        map.put("total", pageInfo.getTotal());
        return Result.success(map);
    }

    @PostMapping("/algchr/list")
    public Result algChrList(@RequestParam String dataType, @RequestParam int pageNo, @RequestParam int pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNo, pageSize);
        List<AlgalChronic> algalChronicList = algalChronicService.getByDataType(dataType);
        PageInfo<AlgalChronic> pageInfo = new PageInfo<>(algalChronicList);
        map.put("data", algalChronicList);
        map.put("total", pageInfo.getTotal());
        return Result.success(map);
    }

    @PostMapping("/daphacu/list")
    public Result daphAcuList(@RequestParam String dataType, @RequestParam int pageNo, @RequestParam int pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNo, pageSize);
        List<DaphniaAcute> daphniaAcuteList = daphniaAcuteService.getByDataType(dataType);
        PageInfo<DaphniaAcute> pageInfo = new PageInfo<>(daphniaAcuteList);
        map.put("data", daphniaAcuteList);
        map.put("total", pageInfo.getTotal());
        return Result.success(map);
    }
}
