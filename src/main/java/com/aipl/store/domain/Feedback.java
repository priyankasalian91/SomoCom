package com.aipl.store.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Feedback {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;	
	private String email;
	
	@Column(length=5000000)
	private String comments;

	
	private Timestamp feedbackdt;
	/* sent : true
	 * not  sent : false */
	private boolean mailsent;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public boolean isMailsent() {
		return mailsent;
	}

	public void setMailsent(boolean mailsent) {
		this.mailsent = mailsent;
	}

	
	public Timestamp getFeedbackdt() {
		return feedbackdt;
	}

	public void setFeedbackdt(Timestamp feedbackdt) {
		this.feedbackdt = feedbackdt;
	}
	
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", name=" + name + ", email=" + email + ", comments=" + comments + "]";
	}
	
	
}
