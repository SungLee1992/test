package cn.edu.nwafu.cie.toxicitypred.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-09 08:27
 * @description: 公共Dao类
 */
public interface BaseDao<T> {

    /**
     * @param: []
     * @return: java.util.List<T>
     * 根据id查找，@Param("id")指定该参数的名称为id，sql中#{id}即可取到该参数的值
     */
    public T get(@Param("id") Object id);

    /**
     * @param: []
     * @return: java.util.List<T>
     * 返回所有记录
     */
    public List<T> getAll();

    /**
     * @param: []
     * @return: T
     * 通过casNo或者id获取相应的记录
     */
    public T getByCasNo(String casNo);

    /**
     * @param: []
     * @return: int
     * 插入一条记录
     */
    public int insertRecord(T t);

    /**
     * @param: []
     * @return: String
     * 更新一条记录
     */
    public int updateRecourd(T t);

    /**
     * @param: []
     * @return: int
     * 根据casNo更新一条记录
     */
    public int updateByCasNo(T t);

    /**
     * @param: [casNo]
     * @return: String
     * 根据casNo号删除一条记录
     */
    public int deleteRecord(T t);

    /**
     * 根据dataType获取所有训练集or验证集
     */
    public List<T> getByDataType(String dataType);

    /**
     * 根据casNo更新预测值
     */
    public int updatePreValueByCasNo(@Param("casNo") String casNo, @Param("preValue") String preValue, @Param("dataType") String dataType);
}
