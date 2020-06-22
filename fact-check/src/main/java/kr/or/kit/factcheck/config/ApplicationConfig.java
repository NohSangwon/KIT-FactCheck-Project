package kr.or.kit.factcheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kr.or.kit.factcheck.dao",  "kr.or.kit.factcheck.service"})
@Import({ DBConfig.class, SNUCrawlerConfig.class })
public class ApplicationConfig {

}