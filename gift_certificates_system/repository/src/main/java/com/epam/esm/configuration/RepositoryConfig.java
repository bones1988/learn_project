package com.epam.esm.configuration;


import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class of property data holder
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.epam.esm")
@MapperScan(basePackages = {"com.epam.esm"}, annotationClass = Mapper.class)
public class RepositoryConfig {
}
