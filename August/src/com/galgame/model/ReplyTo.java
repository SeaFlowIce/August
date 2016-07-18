package com.galgame.model;

import com.google.gson.annotations.Expose;

public class ReplyTo {

	@Expose
	private String content;
	@Expose
	private Integer status;
	@Expose
	private Integer id;
	@Expose
	private String author;
	
	@Override
	public String toString() {
		return "//" + author + "："+content;
	}

	/**
	 * 
	 * @return The content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 *            The content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return The status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @param author
	 *            The author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

}
