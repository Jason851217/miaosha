package com.learningcenter.miaosha;

import com.learningcenter.miaosha.dto.Result;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.learningcenter.miaosha.dao") // 通过注解@MapperScan去寻找mybatis mapper，还可以通过@Mapper标示在数据访问接口上

public class MiaoshaApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context =  SpringApplication.run(MiaoshaApplication.class, args);
        System.out.println(context.getBean(DataSource.class)); // 可以看到我们采用的Druid的数据库连接池，而非Tomcat中的jdbc连接池
    }


}
