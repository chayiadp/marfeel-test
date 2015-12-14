package com.marfeel.app.services.thread;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.marfeel.app.beans.Site;
import com.marfeel.app.parser.SiteParser;
import com.marfeel.app.repository.SiteRepository;

@Component
@Scope("prototype")
public class SiteServiceTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(SiteServiceTask.class);
	
	private static final Set<String> KEY_WORDS = new HashSet<String>(
			Arrays.asList("news","noticias"));
	
	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private SiteParser siteParser;

	private Set<com.marfeel.app.beans.Site> sites;

	public SiteServiceTask() {
		super();
	}

	public SiteServiceTask(Set<Site> sites) {
		this();
		this.sites = sites;
	}


	public void run() {
		String name = new Long(System.currentTimeMillis()).toString();
		logger.debug("Thread: " + name + " running");
		for (Site site : sites) {
			site.setMarfeelizable(isSiteMarfeelizable(site));
			logger.info("Site: " + site.getUrl() + " Marfeelizable: " + site.isMarfeelizable());
			site = siteRepository.save(site);
		}
		logger.debug("Thread: " + name + " finished");
	}


	private String getTitle(Site site) {
		return siteParser.getTitle(site);
	}

	private boolean isSiteMarfeelizable(Site site) {
		String title = getTitle(site);
		return title != null && !title.isEmpty() && KEY_WORDS.contains(title);
	}
	
}
