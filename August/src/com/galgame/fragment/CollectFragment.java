package com.galgame.fragment;

import java.util.ArrayList;
import java.util.List;

import com.galgame.august.DetailActivity;
import com.galgame.august.R;
import com.galgame.model.Story;
import com.galgame.util.DBUtils;
import com.galgame.util.ImageUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CollectFragment extends Fragment
{
	private List<Story> mDataStories = new ArrayList<>();
	private LayoutInflater inflater;
	private View layout;
	private ListView mListView;
	private HomeAdapter mRecentAdapter;

	public CollectFragment()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.inflater = inflater;
		layout = inflater.inflate(R.layout.fragment_common, container, false);
		initUI();
		// initData();
		return layout;
	}

	private void initData()
	{
		mDataStories.clear();
		List<Story> storyList = DBUtils.queryAllCollect();
		mDataStories.addAll(storyList);
		mRecentAdapter.notifyDataSetChanged();
	}

	private void initUI()
	{
		initListView();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		initData();
	}

	private void initListView()
	{
		mListView = (ListView) layout.findViewById(R.id.listView1);
		mRecentAdapter = new HomeAdapter();
		mListView.setAdapter(mRecentAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getContext(), "banner item: " + position, Toast.LENGTH_SHORT).show();
				position -= mListView.getHeaderViewsCount();
				Story story = mDataStories.get(position);
				Intent intent = new Intent(getContext(), DetailActivity.class);
				intent.putExtra("story", story);
				startActivity(intent);
			}
		});
	}

	class HomeAdapter extends BaseAdapter
	{
		class ViewHolder
		{
			TextView tvTitle;
			ImageView imgIcon;
		}

		@Override
		public int getCount()
		{
			return mDataStories.size();
		}

		@Override
		public Object getItem(int position)
		{
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.list_item_latest, null);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.imgIcon = (ImageView) convertView.findViewById(R.id.img_icon);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			Story story = mDataStories.get(position);
			holder.tvTitle.setText(story.getTitle());
			List<String> images = story.getImages();
			if (images.size() > 0)
			{
				String imageUrl = images.get(0);
				ImageUtils.displayImage(getContext(), imageUrl, holder.imgIcon);
			}
			return convertView;
		}

	}

}
