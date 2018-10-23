package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.DaphniaAcuteDao;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
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
public class DaphniaAcuteService extends BaseService<DaphniaAcute> {
    private static final Logger logger = LoggerFactory.getLogger(DaphniaAcuteService.class);
    @Autowired
    private DaphniaAcuteDao daphniaAcuteDao;

    /**
     * @param: [smiFilesDir]
     * @return: boolean
     * 将溞类急性毒性的smiles表达式转为smi文件
     */
    //TODO 测试该方法
    public boolean getSmiFiles(String smiFilesDir,String dataType) {
        if (!FileUtil.validateDir(smiFilesDir)) {
            logger.warn(smiFilesDir + "目录不合法！");
            return false;
        }
        List<DaphniaAcute> daphniaAcuteList = daphniaAcuteDao.getByDataType(dataType);
        for (DaphniaAcute daphniaAcute : daphniaAcuteList) {
            super.getSmiFile(daphniaAcute.getCasNo(), daphniaAcute.getSmiles(), smiFilesDir);
        }
        return true;
    }
}
