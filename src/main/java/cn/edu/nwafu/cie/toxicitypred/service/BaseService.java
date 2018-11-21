package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.BaseDao;
import cn.edu.nwafu.cie.toxicitypred.common.CommandConstant;
import cn.edu.nwafu.cie.toxicitypred.knn.KNN;
import cn.edu.nwafu.cie.toxicitypred.utils.ExcuteCommandUtil;
import cn.edu.nwafu.cie.toxicitypred.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author: SungLee
 * @date: 2018-10-10 08:37
 * @description: 基础service的抽象类
 */
public abstract class BaseService<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    private T t;
    @Autowired
    protected BaseDao<T> baseDao;

    public T get(Object id) {
        return baseDao.get(id);
    }

    public List<T> getByCasNo(String casNo) {
        return baseDao.getByCasNo(casNo);
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

    public int updateByCasNo(T t) {
        return baseDao.updateByCasNo(t);
    }

    public int delete(T t) {
        return baseDao.deleteRecord(t);
    }

    public int updatePreValueByCasNo(String casNo, String preValue, String dataType) {
        return baseDao.updatePreValueByCasNo(casNo, preValue, dataType);
    }

    public String getCasNo(File toxicityFile) {
        String toxicityFileName = toxicityFile.getName();
        String casNo = toxicityFileName.substring(0, toxicityFileName.lastIndexOf("."));
        return casNo;
    }

    public List<T> getByDataType(String dataType) {
        return baseDao.getByDataType(dataType);
    }

    /**
     * @param: [file, content，append] append表示写文件的方式，true为追加，false为覆盖
     * @return: boolean
     * 1.将smiles表达式转为smi文件（供溞急性毒性记录和鱼类慢性记录使用）
     * 2.将数据库中的记录（描述符）写入des文件中
     */
    public boolean writeFile(File file, String content, boolean append) {
        try {
            //将content写入文件中
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file, append));
            bfw.write(content);
            bfw.close();
            System.out.println(content + "写入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(content + "写入失败！");
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

    /***********************************  out -> mol  ***********************************/
    /**
     * @param: [outFile, smiPath, casNo]
     * @return: boolean
     * 输入单个out文件，经过openbabel转出为mol文件
     */
    public boolean outFileToMolFile(File outFile, String molDir) {
        String casNo = this.getCasNo(outFile);
        String cmd = CommandConstant.outFileToMolFileCmd(outFile.getPath(), molDir, casNo);
        return ExcuteCommandUtil.excute(cmd);
    }


    /**
     * @param: [outFilePath, smiPath]
     * @return: int
     * 输入out文件目录，经过openbabel转为smi文件
     */
    public int outFilesToMolFiles(String outDir, String molDir) {
        if (!FileUtil.validateDir(molDir)) {
            logger.warn(molDir + "***********mol目录不合法！");
            return 0;
        }
        File[] outFiles = FileUtil.filterFile(".out", outDir);
        if (outFiles == null || outFiles.length == 0) {
            return 0;
        }
        int numOfMolFiles = 0;
        for (File outFile : outFiles) {
            if (outFileToMolFile(outFile, molDir)) {
                numOfMolFiles++;
            }
        }
        return numOfMolFiles;
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
        File dragonOutPutFile = CommandConstant.getDragonOutput();
        flag = FileUtil.copyFile(dragonOutPutFile, dragonOutFilesDir, getCasNo(smiFile) + ".txt");
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

    /***********************************  mol -> 描述符  ***********************************/
    /**
     * @param: []
     * @return: boolean
     * 首先将输入文件中的smiles表达式读入到模板规定的输入文件中，然后执行dragon，再将生成的文件复制到dragonoutfiles目录中
     */
    public boolean molFileToDragonOutFile(File molFile, String dragonOutFilesDir) {
       /* if (!FileUtil.validateDir(dragonOutFilesDir)) {
            logger.warn(dragonOutFilesDir + "***********描述符文件保存的目标目录不合法！");
            return false;
        }*/
        //首先将输入文件中的mol表达式读入到模板规定的输入文件中
        File molInput = CommandConstant.getMolInput();
        boolean flag = FileUtil.copyFile(molFile, molInput.getParent(), molInput.getName());
        if (!flag) {
            logger.warn(getCasNo(molFile) + "***********mol读入到模板文件中失败！");
            return false;
        }
        //只有当mol读入模板中成功后，才执行dragon计算描述符
        else {
            String cmd = CommandConstant.molToDescriptionFileCmd();
            if (!ExcuteCommandUtil.excute(cmd)) {
                logger.warn("dragon执行出错！");
                return false;
            }
        }

        //将dragon计算得到的文件再复制回描述符文件所在的目录
        File dragonOutputFile = CommandConstant.getDragonOutput();
        flag = FileUtil.copyFile(dragonOutputFile, dragonOutFilesDir, getCasNo(molFile) + ".txt");
        if (!flag) {
            logger.warn(getCasNo(molFile) + "***********描述符文件保存的目标目录失败！");
            return false;
        }
        return true;
    }

    /**
     * @param: [smiDir, dragonOutFilesDir]
     * @return: int
     * 输入smi文件到dragon，计算得出描述符文件的批量执行方法
     */
    public int molFilesToDragonOutFiles(String molDir, String dragonOutFilesDir) {
        if (!FileUtil.validateDir(dragonOutFilesDir)) {
            logger.warn(dragonOutFilesDir + "***********描述符文件保存的目标目录不合法！");
            return 0;
        }
        File[] molFiles = FileUtil.filterFile(".mol", molDir);
        if (molFiles == null || molFiles.length == 0) {
            return 0;
        }
        int numOfdragonOutFiles = 0;
        for (File molFile : molFiles) {
            if (molFileToDragonOutFile(molFile, dragonOutFilesDir)) {
                numOfdragonOutFiles++;
            }
        }
        return numOfdragonOutFiles;
    }

    /***********************************  knn相关的操作  ***********************************/
    /**
     * @param dataFile 数据文件
     * @return 返回数据集
     * 从数据文件中读取数据
     */
    public List<List<Double>> readTrainDesFile(File dataFile) {
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
     * @param dataFile 数据文件
     * @return 返回数据集
     * 从数据文件中读取数据
     */
    public Map<String, Object> readVldDesFile(File dataFile) {
        HashMap<String, Object> dataMap = new HashMap<>();
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
                dataMap.put(description[0], desList);
                data = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * @param: []
     * @return: void
     */
    public Map<String, String> runKnn(File trainFile, File vldFile) {
        List<List<Double>> trainDatas = this.readTrainDesFile(trainFile);
        Map<String, Object> vldDatasMap = this.readVldDesFile(vldFile);
        Map<String, String> result = new HashMap<>();
        KNN knn = new KNN();
        for (Map.Entry<String, Object> entry : vldDatasMap.entrySet()) {
            Integer preValue = -1;
            List<Double> vldData = (List<Double>) entry.getValue();
            //preValue = Math.round(Float.parseFloat((knn.knn(trainDatas, vldData, 3)))); //这里规定K取值
            preValue = knn.knn(trainDatas, vldData, 3);
            result.put(entry.getKey(), String.valueOf(preValue));
            //********* 在生产环境中，以下可以不执行**********//*
            System.out.print("测试元组: " + entry.getKey());
            System.out.print("类别为: ");
            System.out.println(preValue);
            //********** 在生产环境中，以上可以不执行**********//*
        }
        System.out.println(result.size());
        return result;
    }

    private static Map<String, String> map = new HashMap<>();// 实体类中描述符与文件中描述符对应关系

    static {
        // 藻慢性描述符
        map.put("spmax6Bhm", "SpMax6_Bh(m)");
        map.put("gats5i", "GATS5i");
        map.put("mor15s", "Mor15s");
        map.put("logkow", "ALOGP");
        map.put("ats6m", "ATS6m");
        // 溞急性描述符
        map.put("ncrq", "nCrq");
        map.put("f04ns", "F04[N-S]");
        map.put("bo4oo", "B04[O-O]");
        map.put("f08oo", "F08[O-O]");
        map.put("eig08Aeabo", "Eig08_AEA(bo)");
        map.put("b02ncl", "B02[N-Cl]");
        // 溞慢性描述符
        map.put("mlogp", "MLOGP");
        map.put("spmaxEari", "SpMax_EA(ri)");
        map.put("mor04s", "Mor04s");
        map.put("sm02Aeadm", "SM02_AEA(dm)");
        map.put("rdf075s", "RDF075s");
        // 鱼慢性描述符
        map.put("spmaxaEadm", "SpMaxA_EA(dm)");
        map.put("mpc07", "MPC07");
        map.put("cats2d05Ll", "CATS2D_05_LL");
    }

    /**
     * 从描述符文件中提取特定描述符
     *
     * @param file
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     * @throws NoSuchFieldException
     * @auther Sung_Lee
     */
    /*public <T> T getDescription(File file, Class<T> clazz) throws IllegalAccessException, InstantiationException, IOException, NoSuchFieldException {
        T t = clazz.newInstance();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // 读取描述符标题
        String title = reader.readLine();
        List<String> titleList = Arrays.asList(title.trim().split("\\s{2,}|\t"));//将多余空格或Tab键都转为一个空格
        // 读取描述符内容
        String content = reader.readLine();
        String[] contentAry = content.trim().split("\\s{2,}|\t");
        reader.close();
        // 获取对象属性
        Field[] fields = clazz.getDeclaredFields();
        // 获取需要的描述符
        for (Field field : fields) {
            field.setAccessible(true);
            if (!map.keySet().contains(field.getName())) {// 不是描述符属性
                continue;
            }
            if (!titleList.contains(map.get(field.getName()))) {// 文件中没有该描述符
                logger.error(clazz + ":" + field.getName() + "对应的描述符值未找到！");
                continue;
            }
            // 取描述符的值
            String value = contentAry[titleList.indexOf(map.get(field.getName()))];
            if ("NA".equalsIgnoreCase(value)) {
                field.set(t, -1d);
            } else {
                field.set(t, Double.parseDouble(value));
            }
        }
        // 设置casNo
        Field casNoField = clazz.getDeclaredField("casNo");
        casNoField.setAccessible(true);
        casNoField.set(t, file.getName().split("\\.")[0]);
        return t;
    }*/
    public T getDescription(File file, Class<T> clazz) throws IllegalAccessException, InstantiationException, IOException, NoSuchFieldException {
        t = clazz.newInstance();
        //Class clazz = t.getClass();
        //t = (T) clazz.newInstance();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // 读取描述符标题
        String title = reader.readLine();
        List<String> titleList = Arrays.asList(title.trim().split("\\s{2,}|\t"));//将多余空格或Tab键都转为一个空格
        // 读取描述符内容
        String content = reader.readLine();
        String[] contentAry = content.trim().split("\\s{2,}|\t");
        reader.close();
        // 获取对象属性
        Field[] fields = clazz.getDeclaredFields();
        // 获取需要的描述符
        for (Field field : fields) {
            field.setAccessible(true);
            if (!map.keySet().contains(field.getName())) {// 不是描述符属性
                continue;
            }
            if (!titleList.contains(map.get(field.getName()))) {// 文件中没有该描述符
                logger.error(clazz + ":" + field.getName() + "对应的描述符值未找到！");
                continue;
            }
            // 取描述符的值
            String value = contentAry[titleList.indexOf(map.get(field.getName()))];
            if ("NA".equalsIgnoreCase(value)) {
                field.set(t, -1d);
            } else {
                field.set(t, Double.parseDouble(value));
            }
        }
        // 设置casNo
        Field casNoField = clazz.getDeclaredField("casNo");
        casNoField.setAccessible(true);
        casNoField.set(t, file.getName().split("\\.")[0]);
        return t;
    }


    /**
     * @param desDir
     * @return: int
     * @description: 读取描述符txt目录，批量更新
     */
    public int updateDescriptions(String desDir, Class<T> clazz, String dataType) {
        if (!FileUtil.validateDir(desDir)) {
            logger.warn(desDir + "***********描述符文件保存的目标目录不合法！");
            return 0;
        }
        int numOfUpdateDes = 0;
        File files[] = new File(desDir).listFiles();
        //批量更新
        for (File desFile : files) {
            if (updateDescription(desFile, clazz, dataType)) {
                numOfUpdateDes++;
            }
        }
        return numOfUpdateDes;
    }

    /**
     * @param desFile
     * @param dataType
     * @return: boolean
     * @description: 读取单个dragon生成的描述符txt文件，取得对应描述符，更新数据库
     */
    public boolean updateDescription(File desFile, Class<T> clazz, String dataType) {
        try {
            //得到描述符至实体中
            t = getDescription(desFile, clazz);
            // 设置dataType
            //Class clazz = t.getClass();
            Field dataTypeField = clazz.getDeclaredField("datatype");
            dataTypeField.setAccessible(true);
            dataTypeField.set(t, dataType);
            //更新
            return this.updateByCasNo(t) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param desFile
     * @param dataType
     * @return: int
     * @description: 从数据库中读取knn所需的描述符，写入到文件中
     */
    public int getDesFile(File desFile, String dataType) {
        List<T> list = baseDao.getByDataType(dataType);
        int numOfDesRecords = 0;
        for (T t : list) {// 构造描述符+实验值
            if (writeFile(desFile, creatDescription(t, dataType), true)) {
                numOfDesRecords++;
            }
        }
        return numOfDesRecords;
    }

    /**
     * @param desFile
     * @return: int
     * @description: 根据新进记录的实体读取knn所需的描述符，写入到文件中
     */
    public boolean getDesFile(File desFile, T t) {
        if (!writeFile(desFile, creatDescription(t, "new"), false)) {
            return false;
        }
        return true;
    }

    /**
     * @param trainAsVldDesFilePath
     * @param trainFilePath
     * @return: java.io.File
     * @description: 训练集作为测试集进行knn
     */
    public File getTrainAsVldCSV(String trainAsVldDesFilePath, String trainFilePath) throws Exception {
        File trainAsVldDesFile = new File(trainAsVldDesFilePath);
        File trainDesFile = new File(trainFilePath);

        BufferedReader br = new BufferedReader(new FileReader(trainDesFile));
        StringBuffer sb = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            str = str.substring(0, str.lastIndexOf(","));
            sb.append(str + "\n");
        }

        // write string to file
        FileWriter writer = new FileWriter(trainAsVldDesFile);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(sb.toString());

        br.close();
        bw.close();
        writer.close();
        return trainAsVldDesFile;
    }

    public abstract String creatDescription(Object t, String dataType);

}
