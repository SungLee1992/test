package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.FishChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-23 14:28
 * @description: 溞类急性毒性
 */
@Service
public class FishChronicService extends BaseService<FishChronic> {
    private static final Logger logger = LoggerFactory.getLogger(FishChronicService.class);
    @Autowired
    private FishChronicDao fishChronicDao;

    /**
     * @param: [smiFilesDir]
     * @return: boolean
     * 将鱼类慢性毒性的smiles表达式转为smi文件
     */
    public int getSmiFiles(String smiFilesDir, String dataType) {
        if (!FileUtil.validateDir(smiFilesDir)) {
            logger.warn(smiFilesDir + "目录不合法！");
            return 0;
        }
        ArrayList<FishChronic> fishChronicList = (ArrayList<FishChronic>) fishChronicDao.getByDataType(dataType);
        int numOfFishChronics = 0;
        for (FishChronic fishChronic : fishChronicList) {
            if (super.getSmiFile(fishChronic.getCasNo(), fishChronic.getSmiles(), smiFilesDir)) {
                numOfFishChronics++;
            }
        }
        return numOfFishChronics;
    }
}
