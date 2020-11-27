package com.aipl.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//
@Entity
@Table(name = "visitors")
public class Visitor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	private Long id;
    private Integer urlId;
    private String ip;
    private String date;
    
    public Visitor() {
    	
    }

    
    public Visitor(Integer urlId, String ip, String date) {
		super();
		this.urlId = urlId;
		this.ip = ip;
		this.date = date;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "urlid")
    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String string) {
        this.date = string;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "urlId=" + urlId +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                '}';
    }
}
