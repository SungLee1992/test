package cn.edu.nwafu.cie.toxicitypred.config.mybatisC3P0;

/**
 * @author: SungLee
 * @date: 2018-10-08 18:09
 * @description: mybatis配置（方式二），配置类的方式，此处不用
 */
//@Configuration
public class SessionFactoryConfiguration {
   /*
    *//*mybatis mapper文件所在路径*//*
    @Value("${mapper_path}")
    private String mapperPath;
    *//*mybatis-config.xml配置文件的路径*//*
    @Value("${mybatis_config_file}")
    private String mybatisConfigFilePath;
    *//*实体类所在的package*//*
    @Value("${entity_package}")
    private String entityPackage;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        //设置mybatis configuration的扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);

        return sqlSessionFactoryBean;
    }*/
}
