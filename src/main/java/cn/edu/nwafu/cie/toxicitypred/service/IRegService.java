package cn.edu.nwafu.cie.toxicitypred.service;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:27
 * @description: 注册业务逻辑接口
 */
public interface IRegService {
    boolean regUser(String id, String uerId,String pwd);
}
