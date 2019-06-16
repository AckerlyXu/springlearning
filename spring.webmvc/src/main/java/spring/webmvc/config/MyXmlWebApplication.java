package spring.webmvc.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

//第二种方式，如果只要配置dispatcherServlet，可以直接继承AbstractDispatcherServletInitializer
//或者AbstractAnnotationConfigDispatcherServletInitializer
//AbstractDispatcherServletInitializer继承自WebApplicationInitializer
public class MyXmlWebApplication
//extends AbstractDispatcherServletInitializer 
{

	// @Override
	protected WebApplicationContext createServletApplicationContext() {
		// TODO Auto-generated method stub
		XmlWebApplicationContext context = new XmlWebApplicationContext();
		context.setConfigLocation("/WEB-INF/spring/Springmvc.xml");
		return context;
	}

	// @Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	// @Override
	protected WebApplicationContext createRootApplicationContext() {
		return null;
	}

	// @Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
		// 设置log所有信息
		// DEBUG and TRACE logging may log sensitive information.
		// This is why request parameters and headers are masked by default and
		// their logging in full must be enabled explicitly through the
		// enableLoggingRequestDetails property on DispatcherServlet.
		registration.setInitParameter("enableLoggingRequestDetails", "true");
	}

}
