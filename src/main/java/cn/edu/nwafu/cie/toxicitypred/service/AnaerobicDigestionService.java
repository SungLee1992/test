package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.AnaerobicDigestionDao;
import cn.edu.nwafu.cie.toxicitypred.dao.NewCalDesDao;
import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.AnaerobicDigestion;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.NewCalDes;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

/**
 * @author: SungLee
 * @date: 2019-09-17 09:03
 * @description: 厌氧消化模型业务逻辑接口
 **/
@Service
public class AnaerobicDigestionService extends BaseService<AnaerobicDigestion>{

    @Autowired
    private AnaerobicDigestionDao anaerobicDigestionDao;

    private static final Logger logger = LoggerFactory.getLogger(NewCalDesService.class);

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
        ArrayList<AnaerobicDigestion> anaerobicDigestionArray = (ArrayList<AnaerobicDigestion>) anaerobicDigestionDao.getByDataType(dataType);
        int numOfMopFiles = 0;
        for (AnaerobicDigestion algalChronic : anaerobicDigestionArray) {
            if (smiStrToMopFile(mopFilesPath, algalChronic.getSmiles(), algalChronic.getCasNo())) {
                numOfMopFiles++;
            }
        }
        return numOfMopFiles;
    }

    /**
     * @param: [smiFilesDir]
     * @return: boolean
     * 将厌氧消化模型数据的smiles表达式转为smi文件
     */
    public int getSmiFiles(String smiFilesDir, String dataType) {
        if (!FileUtil.validateDir(smiFilesDir)) {
            logger.warn(smiFilesDir + "目录不合法！");
            return 0;
        }
        ArrayList<AnaerobicDigestion> anaerobicDigestionList = (ArrayList<AnaerobicDigestion>) anaerobicDigestionDao.getByDataType(dataType);
        int numOfanaerobicDigestions = 0;
        for (AnaerobicDigestion anaerobicDigestion : anaerobicDigestionList) {
            //构造smi文件
            File smiFile = new File(smiFilesDir + "/" + anaerobicDigestion.getCasNo().trim() + ".smi");
            if (super.writeFile(smiFile, anaerobicDigestion.getSmiles(), false)) {
                numOfanaerobicDigestions++;
            }
        }
        return numOfanaerobicDigestions;
    }

    @Override
    public String creatDescription(Object object, String dataType) {
        StringBuilder sb = new StringBuilder();
        AnaerobicDigestion anaerobicDigestions = (AnaerobicDigestion) object;
        sb.append(anaerobicDigestions.getCasNo() + ",");
        sb.append(anaerobicDigestions.getdISPm() + ",");
        sb.append(anaerobicDigestions.getMor15m() + ",");
        sb.append(anaerobicDigestions.gethATSe() + ",");
        sb.append(anaerobicDigestions.getO060());
        sb.append("train".equals(dataType) ? "," + anaerobicDigestions.getExpValue() : "");
        sb.append("\n");
        return sb.toString();
    }
}
