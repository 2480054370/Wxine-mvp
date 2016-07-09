package com.example.wxine_mvp.data;

import java.sql.Timestamp;

public class Comment implements java.io.Serializable {
	private static final long serialVersionUID = -233199321703147471L;
	private String id;
	private Info info;
	private User user;
	private String content;
	private Integer score;
	private Integer ilike;
	private String status;
	private Timestamp ctime;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(String id, String content, Integer score, Integer ilike, String status, Timestamp ctime) {
		this.id = id;
		this.content = content;
		this.score = score;
		this.ilike = ilike;
		this.status = status;
		this.ctime = ctime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Info getInfo() {
		return this.info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getIlike() {
		return this.ilike;
	}

	public void setIlike(Integer ilike) {
		this.ilike = ilike;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCtime() {
		return this.ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

}