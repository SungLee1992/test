package cn.edu.nwafu.cie.toxicitypred.common;

import java.io.File;

/**
 * @author: SungLee
 * @date: 2018-10-15 17:00
 * @description: 命令常量
 */
public class CommandConstant {

    private static File dragonInput = new File("D:\\nies\\Dragon 7\\data\\dragoninput.smi");
    private static File dragonOutput = new File("D:\\nies\\Dragon 7\\data\\dragonoutput.txt");
    private static String dragonTemplate = System.getProperty("user.dir") + "/files/dragontemplate.drs";

    public static File getDragonInput() {
        return dragonInput;
    }

    public static void setDragonInput(File dragonInput) {
        CommandConstant.dragonInput = dragonInput;
    }

    public static File getDragonOutput() {
        return dragonOutput;
    }

    public static void setDragonOutput(File dragonOutput) {
        CommandConstant.dragonOutput = dragonOutput;
    }

    public static String getDragonTemplate() {
        return dragonTemplate;
    }

    public static void setDragonTemplate(String dragonTemplate) {
        CommandConstant.dragonTemplate = dragonTemplate;
    }


    /** openbabel-smi文件生成mop文件的命令 */
    public static String smiFileToMopFileCmd(String smiPath, String mopPath, String casNo){
        //String cmd = "obabel "+smiPath+"/"+(casNo+".smi")+" -O "+mopPath+"/"+(casNo+".mop")+" -xk PM6 eps=78.6 CHARGE=0 EF GNORM=0.01 POLAR MULLIK SHIFT=80 --gen2d";
        String cmd = "obabel "+smiPath+"/"+(casNo+".smi")+" -O "+mopPath+"/"+(casNo+".mop")+" -xk PM6 eps=78.6 CHARGE=0 EF ISCF GNORM=0.0100 MULLIK POLAR DEBUG SHIFT=80 --gen3d";
        System.out.println("cmd="+cmd);
        return cmd;
    }

    /** openbabel-smi字符串生成mop文件的命令 */
    public static String smiStrToMopFileCmd(String mopPath, String smiles,String casNo){
        //String cmd = "obabel -:"+smiles+" -O "+mopPath+"/"+(casNo+".mop")+" -xk PM6 eps=78.6 CHARGE=0 EF GNORM=0.01 POLAR MULLIK SHIFT=80 --gen2d";
        String cmd = "obabel -:"+smiles+" -O "+mopPath+"/"+(casNo+".mop")+" -xk PM6 eps=78.6 CHARGE=0 EF ISCF GNORM=0.0100 MULLIK POLAR DEBUG SHIFT=80 --gen3d";
        System.out.println("cmd="+cmd);
        return cmd;
    }

    /* MOPAC-mop文件生成out文件的命令*/
    public static String mopFileToOutFileCmd(String mopPath){
        String cmd = "MOPAC2016 "+mopPath;
        System.out.println(cmd);
        return cmd;
    }


    /* openbabel-out文件生成smi文件的命令*/
    public static String outFileToSmiFileCmd(String outFile, String smiDir,String casNo){
        //String cmd = "obabel "+outFile+" -O "+smiDir+"/"+casNo+".smi --gen2d -xn";  //-xn表示在生成的文件中去掉文件路径
        String cmd = "obabel "+outFile+" -O "+smiDir+"/"+casNo+".smi --gen3d -xn";  //-xn表示在生成的文件中去掉文件路径
        System.out.println(cmd);
        return cmd;
    }

    /* dragon-txt文件生成描述符文件的命令*/
    public static String smiToDescriptionFileCmd(){
        String cmd = "dragon7shell -s "+getDragonTemplate();
        System.out.println(cmd);
        return cmd;
    }

}
