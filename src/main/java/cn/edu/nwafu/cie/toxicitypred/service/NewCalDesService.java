package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.AlgalChronicDao;
import cn.edu.nwafu.cie.toxicitypred.dao.NewCalDesDao;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.NewCalDes;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: SungLee
 * @date: 2018-10-10 09:10
 * @description: 藻慢性毒性业务
 */
@Service
public class NewCalDesService extends BaseService<NewCalDes> {
    @Autowired
    private NewCalDesDao newCalDesDao;

    private static final Logger logger = LoggerFactory.getLogger(NewCalDesService.class);

    /**
     * @param: []
     * @return: void
     * 数据集中所有的记录进行转换(smi -> mop)
     */
    public int smiStrToMopFiles(String mopFilesPath) {
        if (!FileUtil.validateDir(mopFilesPath)) {
            logger.warn(mopFilesPath + "目录不合法！");
            return 0;
        }
        ArrayList<NewCalDes> newCalDesArray = (ArrayList<NewCalDes>) newCalDesDao.getAll();
        int numOfMopFiles = 0;
        for (NewCalDes newCalDes : newCalDesArray) {
            if (smiStrToMopFile(mopFilesPath, newCalDes.getSmiles(), newCalDes.getCasNo())) {
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
    @Override
    public String creatDescription(Object object, String dataType) {
        StringBuilder sb = new StringBuilder();
        NewCalDes newCalDes = (NewCalDes) object;
        sb.append(newCalDes.getCasNo() + ",");
        sb.append(newCalDes.getHats2e() + ",");
        sb.append(newCalDes.getPw3() + ",");
        sb.append(newCalDes.getHoma() + ",");
        sb.append(newCalDes.getRdf035u() + ",");
        sb.append(newCalDes.getNrct() + ",");
        sb.append(newCalDes.getH050() + ",");
        sb.append(newCalDes.getNrcs() + ",");
        sb.append(newCalDes.getG1s());
        sb.append("\n");
        return sb.toString();
    }

}
