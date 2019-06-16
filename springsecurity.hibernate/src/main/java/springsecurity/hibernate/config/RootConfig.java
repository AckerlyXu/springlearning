package springsecurity.hibernate.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@ComponentScan(basePackages = { "springsecurity.hibernate.dao", "springsecurity.hibernate.service" })
@Import(WebSecurityConfig.class)
public class RootConfig {

	@Bean
	public LocalSessionFactoryBean sessionFactory(@Value("${jdbc.drivername}") String drivername,
			@Value("${jdbc.username}") String username, @Value("${jdbc.password}") String password,
			@Value("${jdbc.url}") String url) {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setPackagesToScan("springsecurity.hibernate.model");
		Properties properties = new Properties();
		properties.setProperty("hibernate.connection.driver_class", drivername);
		properties.setProperty("hibernate.connection.password", password);
		properties.setProperty("hibernate.connection.url", url);
		properties.setProperty("hibernate.connection.username", username);
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//		 <property name="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</property>
//		  <property name="hibernate.format_sql">true</property>
//		  <!--   <property name="hibernate.show_sql">true</property> -->
//		  <property name="hibernate.use_sql_comments">true</property>
//		  <property name="hibernate.hbm2ddl.auto">create</property>
		bean.setHibernateProperties(properties);
		return bean;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {

		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setFileEncoding("UTF-8");
		configurer.setLocations(new ClassPathResource("db.properties"));
		return configurer;
	}

}
