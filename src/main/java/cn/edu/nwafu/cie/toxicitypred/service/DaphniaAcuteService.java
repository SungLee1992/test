package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.DaphniaAcuteDao;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaAcute;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

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

    public int getSmiFiles(String smiFilesDir, String dataType) {
        if (!FileUtil.validateDir(smiFilesDir)) {
            logger.warn(smiFilesDir + "目录不合法！");
            return 0;
        }
        ArrayList<DaphniaAcute> daphniaAcuteList = (ArrayList<DaphniaAcute>) daphniaAcuteDao.getByDataType(dataType);
        int numOfdaphniaAcutes = 0;
        for (DaphniaAcute daphniaAcute : daphniaAcuteList) {
            //构造smi文件
            File smiFile = new File(smiFilesDir + "/" + daphniaAcute.getCasNo().trim() + ".smi");
            if (super.writeFile(smiFile, daphniaAcute.getSmiles(), false)) {
                numOfdaphniaAcutes++;
            }
        }
        return numOfdaphniaAcutes;
    }

    @Override
    public String creatDescription(Object object, String dataType) {
        StringBuilder sb = new StringBuilder();
        DaphniaAcute daphniaAcute = (DaphniaAcute) object;
        sb.append(daphniaAcute.getCasNo() + ",");
        sb.append(daphniaAcute.getNcrq() + ",");
        sb.append(daphniaAcute.getF04ns() + ",");
        sb.append(daphniaAcute.getBo4oo() + ",");
        sb.append(daphniaAcute.getF08oo() + ",");
        sb.append(daphniaAcute.getEig08Aeabo() + ",");
        sb.append(daphniaAcute.getB02ncl());
        sb.append("train".equals(dataType) ? "," + daphniaAcute.getExpValue() : "");
        sb.append("\n");
        return sb.toString();
    }
}
