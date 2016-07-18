package com.galgame.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "story_image")
public class StoryImage extends Model {
	@Column
	private Integer story_id;
	@Column
	private String image;

	public StoryImage() {
		super();
	}

	public StoryImage(Integer story_id, String image) {
		super();
		this.story_id = story_id;
		this.image = image;
	}

	@Override
	public String toString() {
		return "StoryImage [story_id=" + story_id + ", image=" + image + "]";
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStory_id() {
		return story_id;
	}

	public void setStory_id(Integer story_id) {
		this.story_id = story_id;
	}
}
