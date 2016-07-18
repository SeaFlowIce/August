package com.galgame.util;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.galgame.model.Story;
import com.galgame.model.StoryImage;

public class DBUtils
{
	public static void collect(Story story)
	{
		// 需求：克隆
		if (story != null)
		{
			Story storyNew = story.clone();
			storyNew.save();
			// 保存图片地址
			List<String> images = storyNew.getImages();
			for (int i = 0; i < images.size(); i++)
			{
				String imageUrl = images.get(i);
				StoryImage storyImage = new StoryImage(storyNew.getStoryId(), imageUrl);
				storyImage.save();
			}
		}
	}

	public static void cancelCollect(Story story)
	{
		if (story != null)
		{
			new Delete().from(Story.class).where("story_id=?", story.getStoryId()).execute();
		}
	}

	public static boolean hasCollected(Story story)
	{
		if (story != null)
		{
			Story queryStory = new Select().from(Story.class).where("story_id=?", story.getStoryId()).executeSingle();
			return queryStory != null;
		}
		return false;
	}

	public static List<Story> queryAllCollect()
	{
		List<Story> stories = new Select().from(Story.class).execute();
		for (int i = 0; i < stories.size(); i++)
		{
			Story story = stories.get(i);
			// 查询每一条story数据的图片地址
			StoryImage storyImage = new Select().from(StoryImage.class).where("story_id=?", story.getStoryId())
					.executeSingle();
			if (storyImage != null)
			{
				List<String> images = new ArrayList<>();
				images.add(storyImage.getImage());
				story.setImages(images);
			}
			// List<StoryImage> imageList = new
			// Select().from(StoryImage.class).where("story_id=?",
			// story.getStoryId())
			// .execute();
			// for (int j = 0; j < imageList.size(); j++) {
			// StoryImage storyImage = imageList.get(i);
			// if (storyImage != null) {
			// List<String> images = new ArrayList<>();
			// images.add(storyImage.getImage());
			// story.setImages(images);
			// }
			// }
		}
		return stories;
	}

}
