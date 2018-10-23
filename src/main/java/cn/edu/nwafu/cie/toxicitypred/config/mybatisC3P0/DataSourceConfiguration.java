package cn.edu.nwafu.cie.toxicitypred.config.mybatisC3P0;

//import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author: SungLee
 * @date: 2018-10-08 17:47
 * @description: DataSource的配置，c3p0的方式，此处不用
 */
//@Configuration
//配置mybatis mapper的扫描路径
//@MapperScan("cn.edu.nwafu.cie.toxicitypred.dao")
public class DataSourceConfiguration {
    /*
    @Value("${jdbc.driver}")
    private String JDBCDriver;
    @Value("${jdbc.url}")
    private String JDBCUrl;
    @Value("${jdbc.username}")
    private String JDBCUserName;
    @Value("${jdbc.password}")
    private String JDBCPassWord;

    @Bean(name="dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(JDBCDriver);
        dataSource.setJdbcUrl(JDBCUrl);
        dataSource.setUser(JDBCUserName);
        dataSource.setPassword(JDBCPassWord);
        //关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        return dataSource;
    }*/
}
