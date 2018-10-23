package cn.edu.nwafu.cie.toxicitypred.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author: SungLee
 * @date: 2018-10-10 11:12
 * @description: 配置druid过滤规则，监控所有的页面，排除以下配置的文件
 * 配置文件里配置过了，此处不使用
 */
/*@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        })*/
public class DruidStatFilter extends WebStatFilter {

}