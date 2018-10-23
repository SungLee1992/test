package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.FishChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-23 14:28
 * @description: 溞类急性毒性
 */
@Service
public class FishChronicService extends BaseService<FishChronic>{
    private static final Logger logger = LoggerFactory.getLogger(FishChronicService.class);
    @Autowired
    private FishChronicDao fishChronicDao;

    /**
     * @param: [smiFilesDir]
     * @return: boolean
     * 将鱼类慢性毒性的smiles表达式转为smi文件
     */
    //TODO 测试该方法
    public boolean getSmiFiles(String smiFilesDir) {
        if (!FileUtil.validateDir(smiFilesDir)) {
            logger.warn(smiFilesDir + "目录不合法！");
            return false;
        }
        List<FishChronic> fishChronicList = fishChronicDao.getAll();
        for (FishChronic fishChronic : fishChronicList) {
            super.getSmiFile(fishChronic.getCasNo(), fishChronic.getSmiles(), smiFilesDir);
        }
        return true;
    }
}
