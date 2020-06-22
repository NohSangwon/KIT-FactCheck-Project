package kr.or.kit.factcheck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import kr.or.kit.factcheck.controller.SNUCrawlerThread;

@Configuration
public class SNUCrawlerConfig {
	SNUCrawlerThread crawlerThread;
	
	public SNUCrawlerConfig() {
		crawlerThread = new SNUCrawlerThread();
		crawlerThread.start();
	}
}
