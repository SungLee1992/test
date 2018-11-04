package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.DaphniaChronicDao;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.ExcuteCommandUtil;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: SungLee
 * @date: 2018-10-16 10:47
 * @description: 溞慢性毒性业务
 */
@Service
public class DaphniaChronicService extends BaseService<DaphniaChronic> {
    @Autowired
    private DaphniaChronicDao daphniaChronicDao;
    private static final Logger logger = LoggerFactory.getLogger(DaphniaChronicService.class);

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
        ArrayList<DaphniaChronic> daphniaChronicArray = (ArrayList<DaphniaChronic>) daphniaChronicDao.getByDataType(dataType);
        int numOfMopFiles = 0;
        for (DaphniaChronic daphniaChronic : daphniaChronicArray) {
            if (smiStrToMopFile(mopFilesPath, daphniaChronic.getSmiles(), daphniaChronic.getCasNo())) {
                numOfMopFiles++;
            }
        }
        return numOfMopFiles;
    }

    @Override
    public String creatDescription(Object object, String dataType) {
        StringBuilder sb = new StringBuilder();
        DaphniaChronic daphniaChronic = (DaphniaChronic) object;
        sb.append(daphniaChronic.getCasNo() + ",");
        sb.append(daphniaChronic.getMlogp() + ",");
        sb.append(daphniaChronic.getSpmaxEari() + ",");
        sb.append(daphniaChronic.getMor04s() + ",");
        sb.append(daphniaChronic.getSm02Aeadm() + ",");
        sb.append(daphniaChronic.getRdf075s() + ",");
        sb.append("train".equals(dataType) ? "," + daphniaChronic.getExpValue() : "");
        sb.append("\n");
        return sb.toString();
    }
}
