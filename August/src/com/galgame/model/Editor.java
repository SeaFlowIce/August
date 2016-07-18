package com.galgame.model;

import com.google.gson.annotations.Expose;


public class Editor {

	@Expose
	private String url;
	@Expose
	private String bio;
	@Expose
	private Integer id;
	@Expose
	private String avatar;
	@Expose
	private String name;

	/**
	 * 
	 * @return The url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 *            The url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return The bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * 
	 * @param bio
	 *            The bio
	 */
	public void setBio(String bio) {
		this.bio = bio;
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
	 * @return The avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * 
	 * @param avatar
	 *            The avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
