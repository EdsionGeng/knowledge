package com.wsd.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("application.properties")
@ComponentScan(basePackages = "com.wsd.knowledge")
public class SystemApplication {



    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
//		SpringApplication application = new SpringApplication(SystemApplication.class);
//		//配置事件监听器
//		application.addListeners(new MyApplicationListener());
//		ConfigurableApplicationContext context =application.run(args);
//		//发布事件
//		context.publishEvent(new MyApplicationEvent(new Object()));
//		context.close();
    }
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/405.html");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//            container.addErrorPages(error401Page, error404Page, error500Page);
//        });
//
//    }
}