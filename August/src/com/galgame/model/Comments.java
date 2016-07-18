package com.galgame.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Comments
{

	@Expose
	private List<Comment> comments = new ArrayList<Comment>();

	/**
	 * 
	 * @return The comments
	 */
	public List<Comment> getComments()
	{
		return comments;
	}

	/**
	 * 
	 * @param comments
	 *            The comments
	 */
	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}

}
