package com.galgame.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recent implements Serializable
{

	@SerializedName("news_id")
	@Expose
	private Integer newsId;
	@Expose
	private String url;
	@Expose
	private String thumbnail;
	@Expose
	private String title;

	/**
	 * 
	 * @return The newsId
	 */
	public Integer getNewsId()
	{
		return newsId;
	}

	/**
	 * 
	 * @param newsId
	 *            The news_id
	 */
	public void setNewsId(Integer newsId)
	{
		this.newsId = newsId;
	}

	/**
	 * 
	 * @return The url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * 
	 * @param url
	 *            The url
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * 
	 * @return The thumbnail
	 */
	public String getThumbnail()
	{
		return thumbnail;
	}

	/**
	 * 
	 * @param thumbnail
	 *            The thumbnail
	 */
	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	/**
	 * 
	 * @return The title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

}
