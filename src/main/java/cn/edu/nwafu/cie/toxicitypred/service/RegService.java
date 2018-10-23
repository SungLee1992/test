package cn.edu.nwafu.cie.toxicitypred.service;

import cn.edu.nwafu.cie.toxicitypred.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:28
 * @description: 注册业务接口实现
 */
@Service()
public class RegService implements IRegService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean regUser(String id, String uerId, String pwd) {
        Boolean flag;
        try {
            flag = userMapper.insertUsers(id,uerId, pwd);
        } catch (Exception e) {
            return false;
        }
        return flag;
    }
}
