package com.galgame.util;

import java.util.ArrayList;
import java.util.List;

import com.galgame.model.Recent;
import com.galgame.model.Story;
import com.galgame.model.TopStory;

public class ModelUtils
{

	/**
	 * 服务端多种Story类型，通过工具类转换为同一中类型
	 * 
	 * @param topStory
	 * @return
	 */
	public static Story convertTopStory(TopStory topStory)
	{
		Story story = new Story();
		story.setGaPrefix(topStory.getGaPrefix());
		story.setId(topStory.getId());
		story.setTitle(topStory.getTitle());
		story.setType(topStory.getType());
		List<String> images = new ArrayList<>();
		images.add(topStory.getImage());
		story.setImages(images);
		return story;
	}

	public static Story convertRecent(Recent Recent)
	{
		Story story = new Story();
		story.setId(Recent.getNewsId());
		story.setTitle(Recent.getTitle());
		List<String> images = new ArrayList<>();
		images.add(Recent.getThumbnail());
		story.setImages(images);
		return story;
	}

}
