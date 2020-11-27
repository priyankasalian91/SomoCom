package com.aipl.store.domain;

import javax.persistence.*;

@Entity
@Table(name = "affilate_links")
public class Url {

    private Integer urlId;
    private String url;
    private Long date;
    private String link;
    private String affilateUser;
    
    
    public Url() {
    	
    }



	public Url(Integer urlId, String url, Long date, String link, String affilateUser) {
		super();
		this.urlId = urlId;
		this.url = url;
		this.date = date;
		this.link = link;
		this.affilateUser = affilateUser;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urlid")
    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


	public String getAffilateUser() {
		return affilateUser;
	}



	public void setAffilateUser(String affilateUser) {
		this.affilateUser = affilateUser;
	}



	@Override
    public String toString() {
        return "Url{" +
                "urlId=" + urlId +
                ", url='" + url + '\'' +
                ", date=" + date +
                ", link='" + link + '\'' +
                '}';
    }
}
