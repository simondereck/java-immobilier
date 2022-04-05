package com.utudo.hwwd;

import com.utudo.hwwd.filter.PartnerBussinessFilter;
import com.utudo.hwwd.helpers.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@SpringBootApplication
@EnableScheduling
public class HwwdApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(HwwdApplication.class, args);
		WebSocketServer.setApplicationContext(configurableApplicationContext);
		PartnerBussinessFilter.setApplicationContext(configurableApplicationContext);
	}

//	@Override//为了打包springboot项目
//	protected SpringApplicationBuilder configure(
//			SpringApplicationBuilder builder) {
//		return builder.sources(this.getClass());
//	}


	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
