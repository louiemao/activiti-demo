package com.chinasws.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram","com.chinasws.web"})
@MapperScan("com.chinasws.web.dao")
// 关闭security功能。
// 主要是因为activiti的依赖中含有spring security的jar包，所以springboot会自动配置安全功能，访问时就需要输入密码。
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }


    //初始化模拟数据
    @Bean
    public CommandLineRunner init() {
        return new CommandLineRunner() {
            public void run(String... strings) throws Exception {
//                if (personRepository.findAll().size() == 0) {
//                    personRepository.save(new Person("wtr"));
//                    personRepository.save(new Person("wyf"));
//                    personRepository.save(new Person("admin"));
//                }
//                if (compRepository.findAll().size() == 0) {
//                    Comp group = new Comp("great company");
//                    compRepository.save(group);
//                    Person admin = personRepository.findByPersonName("admin");
//                    Person wtr = personRepository.findByPersonName("wtr");
//                    admin.setComp(group);
//                    wtr.setComp(group);
//                    personRepository.save(admin);
//                    personRepository.save(wtr);
//                }
            }
        };
    }

}
