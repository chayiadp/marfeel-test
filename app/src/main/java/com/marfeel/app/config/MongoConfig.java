package com.marfeel.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.marfeel.app.repository.SiteRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackageClasses=SiteRepository.class)

public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	Environment env;
	
	@Override
	public String getDatabaseName() {
		return env.getProperty("mongo.db");
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		//No user/pass to simplify
		return new MongoClient(env.getProperty("mongo.host"));
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}

}