package springsecurity.xml.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//当rootConfig里面的bean有关于spring security相关的配置的时候,spring-security.xml也要加载进相同的applicationContext,而不能加载进子applicationContext
@ComponentScan(basePackages = "springsecurity.xml.service")
@Configuration
@ImportResource(locations = "classpath:spring-security.xml")
public class RootConfig {

}
