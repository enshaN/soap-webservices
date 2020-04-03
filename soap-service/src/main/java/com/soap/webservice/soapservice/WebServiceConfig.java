package com.soap.webservice.soapservice;

import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


//EnableSpringWebServices
@EnableWs
//Spring Config
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter  {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet =new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
	 return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
	}	
	 
	@Bean(name="courses")
	public DefaultWsdl11Definition defaultWsdl11Definition
	(XsdSchema coursesSchema) {
		DefaultWsdl11Definition defnition = new DefaultWsdl11Definition();
		defnition.setPortTypeName("CoursePort");
		defnition.setTargetNamespace("https://soapservicecourse.com/courses");
		defnition.setLocationUri("/ws");
		defnition.setSchema(coursesSchema);
		
		return defnition;
	}
	
	 @Bean
	 public XsdSchema coursesSchema() {
		 
		 return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	 }
	 
	 @Bean
	 public XwsSecurityInterceptor securityInterceptor() {
		 XwsSecurityInterceptor securityInterceptor= new XwsSecurityInterceptor();
		 securityInterceptor.setCallbackHandler(callbackHandler());
		 securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		 
		 return securityInterceptor;
	 }
	 @Bean
	 public SimplePasswordValidationCallbackHandler callbackHandler() {
		// TODO Auto-generated method stub
		 SimplePasswordValidationCallbackHandler handler= new SimplePasswordValidationCallbackHandler();
		 handler.setUsersMap(Collections.singletonMap("user","password"));
		return handler;
	}

	@Override
		public void addInterceptors(List<EndpointInterceptor> interceptors) {
		 interceptors.add(securityInterceptor());
		}
	
}
