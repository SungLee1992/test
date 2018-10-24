package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.BaseDao;
import cn.edu.nwafu.cie.toxicitypred.common.CommandConstant;
import cn.edu.nwafu.cie.toxicitypred.entities.FishChronic;
import cn.edu.nwafu.cie.toxicitypred.knn.KNN;
import cn.edu.nwafu.cie.toxicitypred.utils.ExcuteCommandUtil;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-10 08:37
 * @description: 基础service的抽象类
 */
public abstract class BaseService<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    protected BaseDao<T> baseDao;

    public T get(Object id) {
        return baseDao.get(id);
    }

    public List<T> getAll() {
        return baseDao.getAll();
    }

    public int insert(T t) {
        return baseDao.insertRecord(t);
    }

    public int update(T t) {
        return baseDao.updateRecourd(t);
    }

    public int delete(T t) {
        return baseDao.deleteRecord(t);
    }

    public String getCasNo(File toxicityFile) {
        String toxicityFileName = toxicityFile.getName();
        String casNo = toxicityFileName.substring(0, toxicityFileName.lastIndexOf("."));
        return casNo;
    }

    /**
     * @param: [casNo, smiles, smiFilesDir]
     * @return: java.io.File
     * 将smiles表达式转为smi文件（供溞急性毒性记录和鱼类慢性记录使用）
     */
    public boolean getSmiFile(String casNo, String smiles, String smiFilesDir) {
        File smiFile = new File(smiFilesDir + "/" + casNo.trim() + ".smi");
        try {
            //将smiles写入文件中
            BufferedWriter bfw = new BufferedWriter(new FileWriter(smiFile));
            bfw.write(smiles);
            bfw.close();
            System.out.println(casNo.trim() + ".smi 写入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(casNo.trim() + ".smi 写入失败！");
            return false;
        }
        return true;
    }

    /***********************************  smi -> mop  ***********************************/
    /**
     * @param: [casNo, smiles, mopFilePath]
     * @return: boolean
     * 输入smiles表达式字符串，经过OpenBabel转为mop文件
     */
    public boolean smiStrToMopFile(String mopFilePath, String smiles, String casNo) {
        String cmd = CommandConstant.smiStrToMopFileCmd(mopFilePath, smiles, casNo);
        return ExcuteCommandUtil.excute(cmd);
    }

    /**
     * @param: [smiFile, mopPath]
     * @return: boolean
     * 输入smi文件，经过OpenBabel转为mop文件
     */
    public boolean smiFileToMopFile(File smiFile, String mopPath) {
        String casNo = this.getCasNo(smiFile);
        String cmd = CommandConstant.smiFileToMopFileCmd(smiFile.getPath(), mopPath, casNo);
        return ExcuteCommandUtil.excute(cmd);
    }

    /***********************************  mop -> out  ***********************************/
    /**
     * @param: [mopFile]
     * @return: boolean
     * 输入单个mop文件，经过MOPAC转出为out文件
     */
    public boolean mopFileToOutFile(File mopFile) {
        String cmd = CommandConstant.mopFileToOutFileCmd(mopFile.getPath());
        return ExcuteCommandUtil.excute(cmd);
    }

    /**
     * @param: [outPath, dataType]
     * @return: int
     * 输入mop文件目录，经过MOPAC，批量转出为out文件
     */
    public int mopFilesToOutFiles(String mopDir) {
        File[] mopFiles = FileUtil.filterFile("mop", mopDir);
        if (mopFiles == null || mopFiles.length == 0) {
            return 0;
        }
        int numOfOutFiles = 0;
        for (File mopFile : mopFiles) {
            if (mopFileToOutFile(mopFile)) {
                numOfOutFiles++;
            }
        }
        return numOfOutFiles;
    }

    /**
     * @param: [mopFilePath, outFilePath]
     * @return: int
     * 由于MOPAC软件生成的out文件（包括arc、pol等文件）在mop文件目录下，因此需要移动到out目录。
     */
    public int moveOutFiles(String mopDir, String outDir) {
        if (!FileUtil.validateDir(outDir)) {
            logger.warn(outDir + "目录不合法！");
            return 0;
        }
        File[] excMopFiles = FileUtil.filterFileExc(".mop", mopDir);
        if (excMopFiles == null || excMopFiles.length == 0) {
            return 0;
        }
        int numOfMoveFile = 0;
        //移动除".mop"外的所有文件
        for (File outFile : excMopFiles) {
            FileUtil.moveFile(outFile, outDir);
            numOfMoveFile++;
        }

        return numOfMoveFile;
    }

    /***********************************  out -> smi  ***********************************/
    /**
     * @param: [outFile, smiPath, casNo]
     * @return: boolean
     * 输入单个out文件，经过openbabel转出为smi文件
     */
    public boolean outFileToSmiFile(File outFile, String smiDir) {
        String casNo = this.getCasNo(outFile);
        String cmd = CommandConstant.outFileToSmiFileCmd(outFile.getPath(), smiDir, casNo);
        return ExcuteCommandUtil.excute(cmd);
    }


    /**
     * @param: [outFilePath, smiPath]
     * @return: int
     * 输入out文件目录，经过openbabel转为smi文件
     */
    public int outFilesToSmiFiles(String outDir, String smiDir) {
        if (!FileUtil.validateDir(smiDir)) {
            logger.warn(smiDir + "***********smi目录不合法！");
            return 0;
        }
        File[] outFiles = FileUtil.filterFile(".out", outDir);
        if (outFiles == null || outFiles.length == 0) {
            return 0;
        }
        int numOfSmiFiles = 0;
        for (File outFile : outFiles) {
            if (outFileToSmiFile(outFile, smiDir)) {
                numOfSmiFiles++;
            }
        }
        return numOfSmiFiles;
    }

    /***********************************  smi -> 描述符  ***********************************/
    /**
     * @param: []
     * @return: boolean
     * 首先将输入文件中的smiles表达式读入到模板规定的输入文件中，然后执行dragon，再将生成的文件复制到dragonoutfiles目录中
     */
    public boolean smiFileToDragonOutFile(File smiFile, String dragonOutFilesDir) {
       /* if (!FileUtil.validateDir(dragonOutFilesDir)) {
            logger.warn(dragonOutFilesDir + "***********描述符文件保存的目标目录不合法！");
            return false;
        }*/
        //首先将输入文件中的表达式读入到模板规定的输入文件中
        File dragonInput = CommandConstant.getDragonInput();
        boolean flag = FileUtil.copyFile(smiFile, dragonInput.getParent(), dragonInput.getName());
        if (!flag) {
            logger.warn(getCasNo(smiFile) + "***********smiles表达式读入到模板文件中失败！");
            return false;
        }
        //只有当smiles读入模板中成功后，才执行dragon计算描述符
        else {
            String cmd = CommandConstant.smiToDescriptionFileCmd();
            if (!ExcuteCommandUtil.excute(cmd)) {
                logger.warn("dragon执行出错！");
                return false;
            }
        }

        //将dragon计算得到的文件再复制回描述符文件所在的目录
        File dragonoutput = CommandConstant.getDragonOutput();
        flag = FileUtil.copyFile(dragonoutput, dragonOutFilesDir, getCasNo(smiFile) + ".txt");
        if (!flag) {
            logger.warn(getCasNo(smiFile) + "***********描述符文件保存的目标目录失败！");
            return false;
        }
        return true;
    }

    /**
     * @param: [smiDir, dragonOutFilesDir]
     * @return: int
     * 输入smi文件到dragon，计算得出描述符文件的批量执行方法
     */
    public int smiFilesToDragonOutFiles(String smiDir, String dragonOutFilesDir) {
        if (!FileUtil.validateDir(dragonOutFilesDir)) {
            logger.warn(smiDir + "***********描述符文件保存的目标目录不合法！");
            return 0;
        }
        File[] smiFiles = FileUtil.filterFile(".smi", smiDir);
        if (smiFiles == null || smiFiles.length == 0) {
            return 0;
        }
        int numOfdragonOutFiles = 0;
        for (File smiFile : smiFiles) {
            if (smiFileToDragonOutFile(smiFile, dragonOutFilesDir)) {
                numOfdragonOutFiles++;
            }
        }
        return numOfdragonOutFiles;
    }

    /***********************************  knn相关的操作  ***********************************/
    //TODO knn操作读文件

    /**
     * @param dataFile 数据文件
     * @return 返回数据集
     * 从数据文件中读取数据
     */
    public List<List<Double>> read(File dataFile) {
        List<List<Double>> datas = new ArrayList<List<Double>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            String data = br.readLine();
            List<Double> desList = null;
            while (data != null) {
                String description[] = data.split(",");
                desList = new ArrayList<Double>();
                //从第1列开始（第0列为casNo）
                for (int i = 1; i < description.length; i++) {
                    desList.add(Double.parseDouble(description[i]));
                }
                datas.add(desList);
                data = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @param: []
     * @return: void
     */
    public String runKnn(File trainFile, File vldFile) {
        List<List<Double>> trainDatas = this.read(trainFile);
        List<List<Double>> vldDatas = this.read(vldFile);
        KNN knn = new KNN();
        String preValue = null;
        for (int i = 0; i < vldDatas.size(); i++) {
            List<Double> vldData = vldDatas.get(i);
            preValue = knn.knn(trainDatas, vldData, 3); //这里规定K取值

            /********* 在生产环境中，以下可以不执行**********/
            System.out.print("测试元组: ");
            for (int j = 0; j < vldData.size(); j++) {
                System.out.print(vldData.get(j) + " ");
            }
            System.out.print("类别为: ");
            System.out.println(preValue);
            /********* 在生产环境中，以上可以不执行**********/
        }
        return preValue;
    }

    //TODO 描述符写入文件中供knn用，文件存放在各自的dragonoutfiles目录下（在各自的Service中写）


    //TODO 新化合物进入系统后的处理逻辑（在各自的Controller中写）
}
