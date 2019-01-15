package cn.edu.nwafu.cie.toxicitypred.utils;

import cn.edu.nwafu.cie.toxicitypred.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author: SungLee
 * @date: 2018-10-15 16:58
 * @description: 执行命令行工具类
 */
public class ExcuteCommandUtil {
    private static Runtime runtime;
    private static Process process;
    private static InputStream inputStream;  //子进程返回的运行结果
    private static BufferedReader inReader;     //缓冲子进程返回的运行结果
    private static InputStream errorInStream; //子进程返回的错误信息
    private static BufferedReader errReader;    //缓冲子进程返回的错误信息
    private static OutputStream outStream;    //用以向子进程提供标准输入
    private static BufferedWriter outWriter;    //缓冲向子进程的输入
    private static String result;

    private static final Logger logger = LoggerFactory.getLogger(ExcuteCommandUtil.class);

    /**
     * @param command
     * @Title: excuteCommand
     * @Description: 执行command 命令
     * @return: boolean
     */
    public static boolean excute(String command) {
        boolean flag = true;
        try {
            //获得子进程
            runtime = Runtime.getRuntime();
            process = runtime.exec(command);

            //子进程的执行结果
            inputStream = new BufferedInputStream(process.getInputStream());
            inReader = new BufferedReader(new InputStreamReader(inputStream));

            //子进程返回的错误信息
            errorInStream = new BufferedInputStream(process.getErrorStream());
            errReader = new BufferedReader(new InputStreamReader(errorInStream));

            //子进程的输入
            outStream = new BufferedOutputStream(process.getOutputStream());
            outWriter = new BufferedWriter(new OutputStreamWriter(outStream));

            //向子进程提供标准输入
            /*System.out.println("------------------向子进程输入 start-----------------");
            while((line = errReader.readLine()) != null){
                System.out.println(line);
            }
            System.out.println("------------------向子进程输入 end-----------------");*/

            //输出命令执行结果
            //System.out.println("--------------------------------------------结果信息 start--------------------------------------------------");
            while ((result = inReader.readLine()) != null) {
                System.out.println(result);
            }
            //System.out.println("--------------------------------------------结果信息 end---------------------------------------------------");

            //输出错误信息
            System.out.println("--------------------------------------------提示信息 start--------------------------------------------------");
            while ((result = errReader.readLine()) != null) {
                System.out.println(result);
                if(result.contains("0 molecules converted")){
                    logger.error("OpenBabel 出错！");
                    return false;
                }
                if(result.contains("Errors encountered during execution of Dragon")){
                    logger.error("Dragon 出错！");
                    return false;
                }
            }
            System.out.println("--------------------------------------------提示信息 end---------------------------------------------------");

            process.waitFor(); //导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。
        } catch (Exception ex) {
            System.out.println("执行命令：" + command + "错误");
            flag = false;
        } finally {
            //最后关闭各种流
            try {
                inputStream.close();
                inReader.close();
                errorInStream.close();
                errReader.close();
                //outStream.close();
                //outWriter.close();
                process.destroy(); //杀掉子进程
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
