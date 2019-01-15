package cn.edu.nwafu.cie.toxicitypred;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ToxicitypredStarter extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意的Application是启动类，就是main方法所属的类
        return builder.sources(ToxicitypredApplication.class);
    }
}
