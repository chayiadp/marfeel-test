package com.marfeel.app.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.marfeel.app.beans.Site;
import com.marfeel.app.repository.SiteRepository;
import com.marfeel.app.services.thread.SiteServiceTask;


@Service("crawlerService")
public class SiteServiceImpl implements SiteService{
	
	private static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);
	
	@Autowired 
	SiteRepository siteRepository;

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	ApplicationContext context;
	
	
	public void checkSites(Set<Site> sites) {
		
		for (SiteServiceTask task : createTasks(sites)) {
			taskExecutor.execute(task);
		}
		
		if(logger.isDebugEnabled()){
			int activeCount = taskExecutor.getActiveCount();
			while (activeCount > 0) {
				logger.debug("Active Threads: " + activeCount);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				activeCount = taskExecutor.getActiveCount();
			}
			
		}
		
	}
	
	private List<SiteServiceTask> createTasks(Set<Site> sites) {

		List<SiteServiceTask> tasks = new ArrayList<SiteServiceTask>();
		int numberOfSitesPerTask = getNumberOfSitesPerTask(sites);
		int count = 1;
		
		Set<Site> taskSites = new HashSet<Site>();
		for (Site site : sites) {
			taskSites.add(site);
			if(count == numberOfSitesPerTask){
				tasks.add((SiteServiceTask) 
						context.getBean(SiteServiceTask.class, taskSites));
				taskSites = new HashSet<Site>();
				count=1;
			}else{
				count++;
			}
		}
		
		if(taskSites.size() > 0){
			tasks.add((SiteServiceTask) 
					context.getBean(SiteServiceTask.class, taskSites));
		}
		
		logger.debug(tasks.size() + " tasks created");
		
		return tasks;
	}

	private int getNumberOfSitesPerTask(Set<Site> sites) {
		int maxPool = taskExecutor.getMaxPoolSize();
		return sites.size() > maxPool ? sites.size() / maxPool : 1;
		
	}

	public List<Site> getAllSites() {
		return siteRepository.findAll();
	}


}
