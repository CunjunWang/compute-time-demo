package com.cunjun.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.cunjun.demo"})
public class ComputeTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputeTimeApplication.class, args);
    }

}
