package spring.webmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "spring.webmvc.dao", "spring.webmvc.service" })
public class RootConfig {

}
