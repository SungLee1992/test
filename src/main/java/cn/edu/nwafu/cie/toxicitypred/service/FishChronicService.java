package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.FishChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
     * @param: [smiFilesDir, dataType]
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
            //构造smi文件
            File smiFile = new File(smiFilesDir + "/" + fishChronic.getCasNo().trim() + ".smi");
            if (super.writeFile(smiFile, fishChronic.getSmiles(), false)) {
                numOfFishChronics++;
            }
        }
        return numOfFishChronics;
    }

    /**
     * @param desFile
     * @param dataType
     * @return: int
     * @description:
     */
    public int getDesFile(File desFile, String dataType) {
        ArrayList<FishChronic> fishChronicList = (ArrayList<FishChronic>) fishChronicDao.getByDataType(dataType);
        int numOfDesRecords = 0;
        String content;
        switch (dataType) {
            case "train":
                for (FishChronic fishChronic : fishChronicList) {
                    //构造描述符+实验值
                    content = fishChronic.getCasNo() + "," + fishChronic.getSpmaxaEadm() + "," + fishChronic.getMpc07() + "," + fishChronic.getCats2d05Ll() + "," + fishChronic.getExpValue() + "\n";
                    if (super.writeFile(desFile, content, true)) {
                        numOfDesRecords++;
                    }
                }
                break;
            case "validate":
                for (FishChronic fishChronic : fishChronicList) {
                    //构造描述符
                    content = fishChronic.getCasNo() + "," + fishChronic.getSpmaxaEadm() + "," + fishChronic.getMpc07() + "," + fishChronic.getCats2d05Ll() + "\n";
                    if (super.writeFile(desFile, content, true)) {
                        numOfDesRecords++;
                    }
                }
                break;
        }
        return numOfDesRecords;
    }

}