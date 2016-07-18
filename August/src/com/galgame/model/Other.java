package com.galgame.model;

import com.google.gson.annotations.Expose;


public class Other {

	@Expose
	private Integer color;
	@Expose
	private String thumbnail;
	@Expose
	private String description;
	@Expose
	private Integer id;
	@Expose
	private String name;

	/**
	 * 
	 * @return The color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * 
	 * @param color
	 *            The color
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * 
	 * @return The thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * 
	 * @param thumbnail
	 *            The thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	public void setDescription(String description) {
		this.description = description;
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
