package com.galgame.fragment;

import com.galgame.august.DetailActivity;
import com.galgame.august.R;
import com.galgame.model.Story;
import com.galgame.model.TopStory;
import com.galgame.util.ImageUtils;
import com.galgame.util.ModelUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BannerItemFragment extends Fragment implements OnClickListener
{

	private TopStory topStory;

	public BannerItemFragment(TopStory topStory)
	{
		this.topStory = topStory;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View layout = inflater.inflate(R.layout.fragment_banner_item, container, false);
		layout.setOnClickListener(this);
		ImageView imageView = (ImageView) layout.findViewById(R.id.img_icon);
		TextView tvTitle = (TextView) layout.findViewById(R.id.tv_desc);
		tvTitle.setText(topStory.getTitle());
		// UILUtils.displayImage(topStory.getImage(), imageView);
		ImageUtils.displayImage(getContext(), topStory.getImage(), imageView);
		return layout;
	}

	@Override
	public void onClick(View v)
	{
		Toast.makeText(getContext(), topStory.getTitle(), Toast.LENGTH_LONG).show();
		Story story = ModelUtils.convertTopStory(topStory);
		Intent intent = new Intent(getContext(), DetailActivity.class);
		intent.putExtra("story", story);
		startActivity(intent);
	}

}
