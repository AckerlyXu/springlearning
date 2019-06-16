package springsecurity.xml.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration()
@EnableWebMvc

@ComponentScan(basePackages = { "springsecurity.xml.controller" })
@Import(value = { ViewConfig.class })

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addFormatters(registry);

		registry.addFormatter(new DateFormatter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}

//	@Bean
//	public DelegatingFilterProxy delegatingFilterProxy() {
////		@Override
////		protected Filter[] getServletFilters() {
////			// TODO Auto-generated method stub
////			// return super.getServletFilters();
////			Filter filter = new DelegatingFilterProxy();
////			return new Filter[] { filter };
////		}
//		return new DelegatingFilterProxy();
//	}

}
