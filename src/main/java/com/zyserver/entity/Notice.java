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
	// 发布时间
	@Column(name = "status")
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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
}
