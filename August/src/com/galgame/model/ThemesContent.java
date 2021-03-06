package com.galgame.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ThemesContent {

	@Expose
	private List<Story> stories = new ArrayList<Story>();
	@Expose
	private String description;
	@Expose
	private String background;
	@Expose
	private Integer color;
	@Expose
	private String name;
	@Expose
	private String image;
	@Expose
	private List<Editor> editors = new ArrayList<Editor>();
	@SerializedName("image_source")
	@Expose
	private String imageSource;

	/**
	 * 
	 * @return The stories
	 */
	public List<Story> getStories() {
		return stories;
	}

	/**
	 * 
	 * @param stories
	 *            The stories
	 */
	public void setStories(List<Story> stories) {
		this.stories = stories;
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
	 * @return The background
	 */
	public String getBackground() {
		return background;
	}

	/**
	 * 
	 * @param background
	 *            The background
	 */
	public void setBackground(String background) {
		this.background = background;
	}

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

	/**
	 * 
	 * @return The image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 
	 * @param image
	 *            The image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 
	 * @return The editors
	 */
	public List<Editor> getEditors() {
		return editors;
	}

	/**
	 * 
	 * @param editors
	 *            The editors
	 */
	public void setEditors(List<Editor> editors) {
		this.editors = editors;
	}

	/**
	 * 
	 * @return The imageSource
	 */
	public String getImageSource() {
		return imageSource;
	}

	/**
	 * 
	 * @param imageSource
	 *            The image_source
	 */
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

}
