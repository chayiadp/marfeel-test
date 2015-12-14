package com.marfeel.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marfeel.app.beans.Site;

public interface SiteRepository extends MongoRepository<Site, String>{

    
}