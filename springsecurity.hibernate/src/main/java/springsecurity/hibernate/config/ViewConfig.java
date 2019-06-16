package springsecurity.hibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ViewConfig {
	@Autowired
	private WebApplicationContext webApplicationContext;

	// 配置templateResolver
	@Bean
	public SpringResourceTemplateResolver templateResolver() {

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
	public
	// ThymeleafViewResolver
	ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;

//		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//		viewResolver.setTemplateEngine(templateEngine());
//		viewResolver.setCharacterEncoding("UTF-8");
//		return viewResolver;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("Messages");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
}
