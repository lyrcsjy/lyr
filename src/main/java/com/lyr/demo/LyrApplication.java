package com.lyr.demo;

import com.lyr.demo.repository.impl.BaseRepositoryImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @Author: lyr
 * @Description: 启动器
 * @Date: 2020/03/12 0:0 下午
 * @Version: 1.0
 **/
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EntityScan(
        basePackageClasses = {LyrApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lyr.demo.**.mapper")
@RefreshScope
public class LyrApplication {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LyrApplication.class, args);
    }
}
