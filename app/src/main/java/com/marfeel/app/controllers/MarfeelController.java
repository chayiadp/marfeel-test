package com.marfeel.app.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marfeel.app.beans.Site;
import com.marfeel.app.services.SiteService;

@RestController
@RequestMapping("/")
public class MarfeelController {
	
	private static final Logger logger = LoggerFactory.getLogger(MarfeelController.class);
	
	public final static String SERVER_OK = "Server Online";
	public final static String OK = "OK";
	
	@Autowired 
	SiteService crawlerService;
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Callable<String> checkSites(@RequestBody final List<Site> sites) {
		
		return new Callable<String>() {
		    public String call() throws Exception {
		    	if(logger.isDebugEnabled()){
					StringBuilder stringB = new StringBuilder();
					stringB.append("Checking sites: ");
					for (Site site : sites) {
						stringB.append(" " + site.getUrl() + " \n");
					}
					logger.debug(stringB.toString());
				}
				crawlerService.checkSites(new HashSet<Site>(sites));
				
				return OK;
		    }
		  };
		
	}
	
	
	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public @ResponseBody List<Site> getSites() {
		return crawlerService.getAllSites();
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String test() {
		return SERVER_OK;
	}


}
