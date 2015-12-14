package com.marfeel.app.beans;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Site implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String url;
	private boolean marfeelizable;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isMarfeelizable() {
		return marfeelizable;
	}
	public void setMarfeelizable(boolean marfeelizable) {
		this.marfeelizable = marfeelizable;
	}
	
	public Site() {
		super();
	}
	public Site(String url, boolean marfeelizable) {
		super();
		this.url = url;
		this.marfeelizable = marfeelizable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (marfeelizable ? 1231 : 1237);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Site other = (Site) obj;
		if (marfeelizable != other.marfeelizable)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	
	
}