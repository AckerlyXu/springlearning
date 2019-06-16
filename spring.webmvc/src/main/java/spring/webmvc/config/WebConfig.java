package spring.webmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
//四的话使用这个
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templatemode.TemplateMode;

import spring.webmvc.controller.conversion.VarietyFormatter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "spring.webmvc.controller")
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private WebApplicationContext webApplicationContext;

	// 配置templateResolver
	@Bean
	public SpringResourceTemplateResolver templateResolver() {

//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setPrefix("/WEB-INF/jsp/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;

		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setApplicationContext(webApplicationContext);
		resolver.setSuffix(".html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(1);
		resolver.setTemplateMode(TemplateMode.HTML);

		resolver.setCacheable(false);
		return resolver;

	}

	// 配置模板引擎
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(templateResolver());
		// 如果要好的兼容性就设置成false,默认是false
		springTemplateEngine.setEnableSpringELCompiler(true);

		return springTemplateEngine;
	}

	// 配置viewResolver viewResolver -> templateEngine -> templateResolver
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 跨域方式2: 配置跨域策略
//		registry.addMapping("/").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
//				// .exposedHeaders("header1", "header2")
//				.allowCredentials(true).maxAge(3600);

		// Add more mappings...
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addFormatters(registry);
		registry.addFormatter(new VarietyFormatter());
		registry.addFormatter(new DateFormatter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**").addResourceLocations("/images/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("Messages");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}

}
