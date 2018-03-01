package com.zyserver.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "t_notice")
public class Notice {
	
	@Id
	@Column(name = "id", updatable = false)
	private Integer id;
	// 后台用户ID
	@Column(name = "user_id")
	private Integer userId;
	// 标题
	@Column(name = "title")
	private String title;
	// 内容
	@Column(name = "content")
	private String content;
	// 发布时间
	@Column(name = "create_time")
	private Date createTime;
	// 后台用户
	@OneToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {

		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public User getUser() {
		return user;
	}
}
