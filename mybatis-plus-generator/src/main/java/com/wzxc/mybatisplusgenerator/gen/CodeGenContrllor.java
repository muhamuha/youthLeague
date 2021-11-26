package com.wzxc.mybatisplusgenerator.gen;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.base.CaseFormat;
import com.wzxc.common.utils.DateUtils;
import com.wzxc.common.utils.PathUtils;
import com.wzxc.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Slf4j
@Component
public class CodeGenContrllor {

    @Autowired
    private FieldColumnMapper fieldColumnMapper;

    @Value("${project.author.name}")
    public String name;
    @Value("${spring.datasource.ds1.url}")
    private String dbUrl;
    @Value("${spring.datasource.ds1.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.ds1.username}")
    private String username;
    @Value("${spring.datasource.ds1.password}")
    private String password;

    @Value("${gen.parent}")
    private String parent;
    @Value("${gen.super-controller-class}")
    private String superControllerClass;
    @Value("${gen.package-name}")
    private String packageName;
    @Value("${gen.datasource}")
    private String datasource;

    @Value("${template.path.mapper}")
    private String templateMapperPath;
    @Value("${template.path.service}")
    private String templateServicePath;
    @Value("${template.path.serviceImpl}")
    private String templateServiceImplPath;
    @Value("${template.path.entity}")
    private String templateEntityPath;
    @Value("${template.path.controller}")
    private String templateControllerPath;
    @Value("${template.path.xml}")
    private String templateXmlPath;

    @Value("${out-dir.mapper}")
    private String outDirMapperPath;
    @Value("${out-dir.service}")
    private String outDirSerivePath;
    @Value("${out-dir.serviceImpl}")
    private String outDirServiceImplPath;
    @Value("${out-dir.entity}")
    private String outDirEntityPath;
    @Value("${out-dir.controller}")
    private String outDirControllerPath;
    @Value("${out-dir.xml}")
    private String outDirXmlPath;


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private void genOne(Class clazz, String dbName, String tableName){
        // 创建代码生成器对象
        AutoGenerator autoGenerator = new AutoGenerator();
        // 1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        globalConfig.setActiveRecord(true) // 是否支持AR模式
                .setAuthor(name)//作者
                .setOpen(false) // 是否打开资源管理器
                .setFileOverride(true) // 生成文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setDateType(DateType.ONLY_DATE);
        autoGenerator.setGlobalConfig(globalConfig);

        // 2.设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dsc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                initCfgObject(map, dbName, tableName);
                this.setMap(map);
            }
        };
        // 模板引擎是 velocity
        List<FileOutConfig> focList = new ArrayList<>();
        // 添加模板文件的路径
        addTemplatePath(focList, clazz);
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 不想重新生成返回 false
                boolean b = new File(filePath).exists();
                if(b && fileType == FileType.OTHER){
                    log.error("文件已经存在 --- " + filePath);
                }
                return !b;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(templateEntityPath);
        templateConfig.setService(templateServicePath);
        templateConfig.setController(templateControllerPath);
        templateConfig.setServiceImpl(templateServiceImplPath);
        templateConfig.setMapper(templateMapperPath);
        templateConfig.setXml(templateXmlPath);
        autoGenerator.setTemplate(templateConfig);

        // 3.包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent(parent);
        autoGenerator.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 设置命名规则，允许驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 设置命名规则，允许列驼峰命名
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setLogicDeleteFieldName("deleted"); // 设置逻辑删除的名字

        // 公共父类
        strategy.setSuperControllerClass(superControllerClass);

        // 自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT); // 设置自动填充创建时间
        TableFill gmtModified = new TableFill("gmt_modified",FieldFill.INSERT_UPDATE); // 设置自动填充修改时间
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        // 乐观锁
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true); // controller层使用rest风格
        strategy.setControllerMappingHyphenStyle(true); // localhost:8080/hello_id_2

        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        autoGenerator.setStrategy(strategy);
        VelocityEngine ve = new VelocityEngine();
//        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        autoGenerator.execute();
    }

    public void gen(Class clazz) {
        String dbName = scanner("请输入数据库名称");
        String[] tableNameList = scanner("请输入表名，多个英文逗号分割").split(",");
        for(String tableName : tableNameList){
            genOne(clazz, dbName, tableName);
        }
    }

    private void addTemplatePath(List<FileOutConfig> focList, Class clazz) {
        // 获取开发根路径
        String devRootPath = PathUtils.currentDevPath(clazz);
        // mapper
        focList.add(new FileOutConfig(templateMapperPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return devRootPath + outDirMapperPath + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });
        // service
        focList.add(new FileOutConfig(templateServicePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return devRootPath + outDirSerivePath + "I" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        // serviceImpl
        focList.add(new FileOutConfig(templateServiceImplPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return devRootPath + outDirServiceImplPath + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });
        // vo
        focList.add(new FileOutConfig(templateEntityPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return devRootPath + outDirEntityPath + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // controller
        focList.add(new FileOutConfig(templateControllerPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return devRootPath + outDirControllerPath + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        // xml
        String resourceRootPath = PathUtils.currentDevResourceRootPath(clazz);
        focList.add(new FileOutConfig(templateXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return resourceRootPath + outDirXmlPath + "common/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
    }

    private List<FieldColumn> queryFieldInfoList(String dbName, String tableName){
        QueryWrapper<FieldColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", tableName)
                .eq("table_schema", dbName);
        List<FieldColumn> fieldColumns = fieldColumnMapper.selectList(queryWrapper);
        return fieldColumns;
    }

    private void initCfgObject(Map<String, Object> map, String dbName, String tableName){
        // 查询表详情
        List<FieldColumn> fieldColumns = queryFieldInfoList(dbName, tableName);
        Iterator<FieldColumn> iterator = fieldColumns.iterator();
        while (iterator.hasNext()) {
            FieldColumn fieldColumn = iterator.next();
            // 处理主键
            if(fieldColumn.getColumnKey().equals("PRI")){
                map.put("primaryColumn", fieldColumn.getFieldName());
                map.put("primaryName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldColumn.getFieldName()));
            }
            // 处理属性名称
            fieldColumn.setFieldName(fieldColumn.getFieldName().toLowerCase());
            // 处理注解
            if(!StringUtils.isEmpty(fieldColumn.getColumnComment())){
                fieldColumn.setColumnComment(fieldColumn.getColumnComment().replaceAll("\r\n", "\t"));
            }
            // 处理类型
            String dataType = fieldColumn.getDataType().toLowerCase();
            switch(dataType){
                case "bigint": case "double": { fieldColumn.setJavaType("Long"); break; }
                case "float": { fieldColumn.setJavaType("Float"); break; }
                case "varchar": case "longtext": case "char": { fieldColumn.setJavaType("String"); break; }
                case "tinyint": case "int": { fieldColumn.setJavaType("Integer"); break; }
                case "datetime": case "date": case "time": { fieldColumn.setJavaType("Date"); break; }
                default: { fieldColumn.setJavaType("String"); break; }
            }
            fieldColumn.setJavaField(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldColumn.getFieldName()));
            // 过滤create_time和update_time
            if(fieldColumn.getFieldName().equals("create_time") || fieldColumn.getFieldName().equals("update_time")){
                iterator.remove();
            }
        }
        String moudleName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        String voName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
        map.put("packageName", packageName);
        map.put("author", name);
        map.put("datetime", DateUtils.getDate());
        map.put("moudleName", moudleName);
        map.put("voName", voName);
        map.put("tableName", tableName);
        map.put("dbName", dbName);
        map.put("fields", fieldColumns);
        map.put("datasource", datasource);
    }

}
