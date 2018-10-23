package cn.edu.nwafu.cie.toxicitypred.dao;

import cn.edu.nwafu.cie.toxicitypred.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:25
 * @description: 用户mapper接口
 */
public interface UserMapper {
    @Select("select * from user where userId = #{userId}")
    User findUserByUserid(@Param("userId") String userId);
    @Insert("insert into user (id,userId,pwd) values (#{id},#{userId},#{pwd})")
    boolean insertUsers(@Param("id") String id, @Param("userId") String userId, @Param("pwd") String pwd);
}

