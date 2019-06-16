package springsecurity.hibernate.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//这个类是启用springsecurity必须的，这个类配合enablewebsecurity就能注册 springSecurityFilterChain
//enablewebsecurity是个配置类，需要在AnnotationWebApplication中读取
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
