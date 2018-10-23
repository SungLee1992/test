package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.AlgalChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.ExcuteCommandUtil;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

/**
 * @author: SungLee
 * @date: 2018-10-10 09:10
 * @description: 藻慢性毒性业务
 */
@Service
public class AlgalChronicService extends BaseService<AlgalChronic> {
    @Autowired
    private AlgalChronicDao algalChronicDao;

    private static final Logger logger = LoggerFactory.getLogger(AlgalChronicService.class);

    /**
     * @param: []
     * @return: void
     * 数据集中所有的记录进行转换(smi -> mop)
     */
    public int smiStrToMopFiles(String mopFilesPath, String dataType) {
        if (!FileUtil.validateDir(mopFilesPath)) {
            logger.warn(mopFilesPath + "目录不合法！");
            return 0;
        }
        ArrayList<AlgalChronic> algalChronicArray = (ArrayList<AlgalChronic>) algalChronicDao.getByDataType(dataType);
        int numOfMopFiles = 0;
        for (AlgalChronic algalChronic : algalChronicArray) {
            if (smiStrToMopFile(mopFilesPath, algalChronic.getSmiles(), algalChronic.getCasNo())) {
                numOfMopFiles++;
            }
        }
        return numOfMopFiles;
    }
    /* *//**
     * @param: [outFilePath, smiPath]
     * @return: int
     * 数据集中所有的记录进行转换（out -> smi）
     *//*
    public int getSmiFiles(String outPath,String smiPath){
        if (!FileUtil.validateDir(smiPath)) {
            logger.warn(smiPath+"smi目录不合法！");
            return 0;
        }
        return outFilesToSmiFiles(outPath, smiPath);
    }*/

}
