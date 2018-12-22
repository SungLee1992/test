package cn.edu.nwafu.cie.toxicitypred.dao;

import cn.edu.nwafu.cie.toxicitypred.entities.NewCalDes;

public interface NewCalDesDao extends BaseDao<NewCalDes>{
    int deleteByPrimaryKey(Integer id);

    int insert(NewCalDes record);

    int insertSelective(NewCalDes record);

    NewCalDes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewCalDes record);

    int updateByPrimaryKey(NewCalDes record);
}