package cn.edu.nwafu.cie.toxicitypred.common;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author: SungLee————借鉴https://github.com/leechenxiang/imooc-springboot-starter
 * @date: 2018-10-12 14:55
 * @description: 统一返回的JsonResult
 * 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */

public class Result {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 响应业务状态
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;

    public static Result build(Integer status, String msg, Object data) {
        return new Result(status, msg, data);
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    public static Result successWithoutData() {
        return new Result(null);
    }

    public static Result error(int status, String msg) {
        return new Result(status, msg, null);
    }

    public static Result error(int status, String msg, Object errorData) {
        return new Result(status, msg, errorData);
    }

    public static Result errorMsg(String msg) {
        return new Result(500, msg, null);
    }

    public static Result errorMap(Object data) {
        return new Result(501, "error", data);
    }

    public static Result errorTokenMsg(String msg) {
        return new Result(502, msg, null);
    }

    public static Result errorException(String msg) {
        return new Result(555, msg, null);
    }

    public Result() {

    }

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isSuccess() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: 将json结果集转化为Result对象
     * 需要转换的对象是一个类
     */
    public static Result formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Result.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     * @Description: 没有object对象的转化
     */
    public static Result format(String json) {
        try {
            return MAPPER.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: Object是集合转化
     * 需要转换的对象是一个list
     */
    public static Result formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
