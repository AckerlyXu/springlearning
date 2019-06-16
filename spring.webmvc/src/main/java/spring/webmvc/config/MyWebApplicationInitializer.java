package spring.webmvc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//初始化dispatcherservlete第一种方式，实现WebApplicationInitializer，配置dispatcherservlet
//在onStartup方法中加载配置文件
public class MyWebApplicationInitializer // implements WebApplicationInitializer
{

//	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		// 使用xml
		/*
		 * XmlWebApplicationContext context = new XmlWebApplicationContext();
		 * context.setConfigLocation("classpath:Springmvc.xml"); ; context.refresh();
		 */

		// 使用java配置
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);
		context.refresh();
		Dynamic registration = servletContext.addServlet("app", new DispatcherServlet(context));
		registration.setLoadOnStartup(1);
		registration.addMapping("/");

	}

}
