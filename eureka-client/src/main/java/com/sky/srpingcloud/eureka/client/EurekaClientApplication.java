package com.sky.srpingcloud.eureka.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学习地址：https://blog.csdn.net/forezp/article/details/70148833
 */
@EnableEurekaClient
@SpringBootApplication
@RestController
@Scope("prototype")
public class EurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}

	@Value("${server.port}")
	String port;

	private Integer a = 0;
	@RequestMapping("/hi")
	public String home(@RequestParam String name) {
		a++;
		return "hi "+name+",i am from port:" +port+"!!! a="+a;
	}

}
