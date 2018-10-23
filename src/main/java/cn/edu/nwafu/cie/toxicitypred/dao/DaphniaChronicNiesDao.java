package cn.edu.nwafu.cie.toxicitypred.dao;

import cn.edu.nwafu.cie.toxicitypred.entities.DaphniaChronicNies;

public interface DaphniaChronicNiesDao extends BaseDao<DaphniaChronicNies>{
    int deleteByPrimaryKey(Integer id);

    int insert(DaphniaChronicNies record);

    int insertSelective(DaphniaChronicNies record);

    DaphniaChronicNies selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DaphniaChronicNies record);

    int updateByPrimaryKey(DaphniaChronicNies record);
}