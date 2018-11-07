package cn.edu.nwafu.cie.toxicitypred.dao;

import cn.edu.nwafu.cie.toxicitypred.entities.AlgalChronic;
import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaChronic;
import cn.edu.nwafu.cie.toxicitypred.service.AlgalChronicService;
import cn.edu.nwafu.cie.toxicitypred.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileFilter;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgalChronicDaoTest {

    @Autowired
    private AlgalChronicDao algalChronicDao;
    @Autowired
    private DaphniaChronicDao daphniaChronicDao;
    @Autowired
    private AlgalChronicService algalChronicService;

    @Test
    public void getAll() {
        List<DaphniaChronic> trainList = daphniaChronicDao.getByDataType("train");
        System.out.println(trainList.size());
        List<DaphniaChronic> vldList = daphniaChronicDao.getByDataType("validate");
        System.out.println(vldList.size());
    }

    //@Test
    public void getByCasNo() {
        //AlgalChronic algalChronic = new AlgalChronic();
        //algalChronic = algalChronicDao.getByCasNo("000067-48-1");
        //System.out.println(algalChronic.getSmiles());
    }

    //@Test
    public void get() {
        AlgalChronic algalChronic = new AlgalChronic();
        algalChronic = algalChronicDao.get("000067-48-1");
        System.out.println(algalChronic.getSmiles());
    }

    /**
     * 找出没有被转为mop的记录
     */

    @Test
    public void judge() {
        String path = "D:\\Code\\IDE\\JetBrains\\IdeaWorkSpace\\toxicitypred\\files\\mopfiles\\algalchronic\\trainfiles";        //要遍历的路径
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        ArrayList<String> fileNameList = new ArrayList<>();

        for (int i = 0; i < fs.length; i++) {                    //遍历File[]数组
            if (!fs[i].isDirectory()) {
                System.out.println(fs[i].getName());
                fileNameList.add(fs[i].getName());
            }
        }
        List<DaphniaChronic> trainList = daphniaChronicDao.getByDataType("train");
        for (DaphniaChronic dh : trainList) {
            String casNoName = dh.getCasNo().trim()+".mop";
            if(!fileNameList.contains(casNoName)){
                System.out.println(dh.getCasNo().trim());
            }
        }
    }

    @Test
    public void fileFilterTest(){
        String path = "D:\\Code\\IDE\\JetBrains\\IdeaWorkSpace\\toxicitypred\\files\\mopfiles\\algalchronic\\trainfiles";        //要遍历的路径
        File fileDir = new File(path);        //获取其file对象
        File[] outFiles = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory()){
                    return true;
                }
                String name = pathname.getName();//获取文件的名称
                System.out.println("****************"+pathname);
                return name.endsWith(".out")|| name.endsWith(".arc")|| name.endsWith(".pol");
            }
        });
        System.out.println();
    }

    @Test
    public void updatePreValueByCasNo(){
        algalChronicService.updatePreValueByCasNo("000067-48-1","0","train");
    }
}