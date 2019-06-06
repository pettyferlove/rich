package com.github.rich.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Mybatis相关资源生成器
 * @author Petty
 */
public class MybatisGenerator {
    public static void main(String[] args) {
        String outputDir = "D:/Pettyfer";
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setSwagger2(true);
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setIdType(IdType.UUID);
        gc.setDateType(DateType.TIME_PACK);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor("Petty");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/rich_attachment?characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);


        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("/templates/entity.java.vm");
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 乐观锁字段
        //strategy.setVersionFieldName("VERSION");
        strategy.setLogicDeleteFieldName("del_flag");
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        // 表名生成策略
        strategy.setInclude();
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityBuilderModel(false);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        mpg.setStrategy(strategy);
        // 配置包路径
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.github.rich.attachment");
        mpg.setPackageInfo(pc);
        mpg.execute();
    }
}

