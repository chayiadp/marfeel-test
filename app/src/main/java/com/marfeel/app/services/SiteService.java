package com.marfeel.app.services;

import java.util.List;
import java.util.Set;

import com.marfeel.app.beans.Site;


public interface SiteService {
	
	public void checkSites(Set<Site> sites);

	public List<Site> getAllSites();
}
