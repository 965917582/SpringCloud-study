package com.atguigu.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.springcloud.alibaba.dao") //告诉你dao层跟Mybatis相关
public class MyBatisConfig {
}
