package com.galgame.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.august.DetailActivity;
import com.galgame.august.R;
import com.galgame.model.Hot;
import com.galgame.model.Recent;
import com.galgame.model.Story;
import com.galgame.util.ApiClient;
import com.galgame.util.ImageUtils;
import com.galgame.util.ModelUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HotFragment extends Fragment
{

	private static final int MAX_BANNER_COUNT = 100000;
	private List<Recent> mDataRecent = new ArrayList<>();
	private static final int DURATION_SCROLL = 2000;
	private LayoutInflater inflater;
	private ListView mListView;
	private View layout;
	private HomeAdapter mRecentAdapter;

	public HotFragment()
	{
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.inflater = inflater;
		layout = inflater.inflate(R.layout.fragment_common, container, false);
		initUI();
		initData();
		return layout;
	}

	private void initData()
	{
		// HTTPUtils.get(getContext(), ZHConstants.url.latest, new
		// ResponseListener() {
		ApiClient.getHot(getContext(), new ResponseListener()
		{
			public void onResponse(String arg0)
			{
				if (TextUtils.isEmpty(arg0))
				{
					// 提示错误
					return;
				}
				// 更新列表数据
				Hot hot = GsonUtils.parseJSON(arg0, Hot.class);
				List<Recent> stories = hot.getRecent();
				mDataRecent.addAll(stories);
				mRecentAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("latest onErrorResponse", arg0 == null ? "出错" : arg0.getMessage());
			}
		});
	}

	private void initUI()
	{
		initListView();
	}

	private void initListView()
	{
		mListView = (ListView) layout.findViewById(R.id.listView1);
		mRecentAdapter = new HomeAdapter();
		mListView.setAdapter(mRecentAdapter);
		// TODO
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				Recent recent = mDataRecent.get(position);
				Story story = ModelUtils.convertRecent(recent);
				Intent intent = new Intent(getContext(), DetailActivity.class);
				// Bundle b = new Bundle();
				// b.putSerializable("recent", recent);
				// intent.putExtras(b);
				// intent.putExtra("detail", "recent").putExtra("recent",
				// recent);
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
			return mDataRecent.size();
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
				convertView = inflater.inflate(R.layout.list_item_hot, null);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.imgIcon = (ImageView) convertView.findViewById(R.id.img_icon);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			Recent Recent = mDataRecent.get(position);
			holder.tvTitle.setText(Recent.getTitle());
			String thumbnail = Recent.getThumbnail();
			ImageUtils.displayImage(getContext(), thumbnail, holder.imgIcon);
			return convertView;
		}

	}

}
