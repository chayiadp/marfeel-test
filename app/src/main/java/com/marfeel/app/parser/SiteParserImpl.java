package com.marfeel.app.parser;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marfeel.app.beans.Site;

public class SiteParserImpl implements SiteParser {

	private static final Logger logger = LoggerFactory.getLogger(SiteParserImpl.class);


	public String getTitle(Site site) {
		String title = null;
		try {
			title = Jsoup.connect(site.getUrl()).get().title();
			logger.debug("The title of " + site.getUrl() + " is " + title);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return title;
	}

}
