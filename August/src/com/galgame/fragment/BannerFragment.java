package com.galgame.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.august.DetailActivity;
import com.galgame.august.R;
import com.galgame.model.Latest;
import com.galgame.model.Story;
import com.galgame.model.TopStory;
import com.galgame.util.ApiClient;
import com.galgame.util.DateUtils;
import com.galgame.util.ImageUtils;
import com.galgame.util.ViewPagerIndicator;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BannerFragment extends Fragment
{

	private static final int MAX_BANNER_COUNT = 100000;
	private List<Story> mDataStories = new ArrayList<>();
	private List<TopStory> mDataTopStories = new ArrayList<>();
	private static final int DURATION_SCROLL = 2000;
	private LayoutInflater inflater;
	private ListView mListView;
	// private int[] mDrawerResIDs = new int[] { R.drawable.banner00,
	// R.drawable.banner01, R.drawable.banner02,
	// R.drawable.banner03, R.drawable.banner04 };
	private View layout;
	private View bannerHeader;
	private ViewPager mPager;
	protected boolean isUserDrag;
	protected boolean hasDestroy;
	private HomeAdapter mStoryAdapter;
	private BannerAdapter mBannerAdapter;
	private int position;
	protected boolean isLatest;
	private ViewPagerIndicator mIndicator;

	public BannerFragment()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public BannerFragment(int position)
	{
		this.position = position;
		// 如果position是1，为最新页面
		// 如果position是2，为昨天页面
		isLatest = position == 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		this.inflater = inflater;
		Log.e("BannerFragment", "onCreateView");
		layout = inflater.inflate(R.layout.fragment_common, container, false);
		initUI();
		initData();
		return layout;
	}

	private void initData()
	{
		ResponseListener listener = new ResponseListener()
		{
			public void onResponse(String arg0)
			{
				if (TextUtils.isEmpty(arg0))
				{
					// 提示错误
					return;
				}
				// 更新列表数据
				Latest latest = GsonUtils.parseJSON(arg0, Latest.class);
				List<Story> stories = latest.getStories();
				mDataStories.addAll(stories);
				mStoryAdapter.notifyDataSetChanged();
				// 当前页面为最新时更新Banner数据
				// TODO 2016-06-02 复用
				if (isLatest)
				{
					List<TopStory> topStories = latest.getTopStories();
					mDataTopStories.addAll(topStories);
					mBannerAdapter.notifyDataSetChanged();
					mPager.setCurrentItem(mDataTopStories.size() * MAX_BANNER_COUNT / 2);
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("latest onErrorResponse", arg0 == null ? "出错" : arg0.getMessage());
			}
		};

		if (isLatest)
		{
			ApiClient.getLatest(getContext(), listener);
		} else
		{
			String date = DateUtils.getFormatDate(1 - position);
			ApiClient.getBefore(getContext(), date, listener);
		}
		// else {
		//
		// // 过往
		// ApiClient.getBefore(getContext(), position, new ResponseListener() {
		//
		// @Override
		// public void onResponse(String arg0) {
		// if (TextUtils.isEmpty(arg0)) {
		// // 提示错误
		// return;
		// }
		// // 更新列表数据
		// Latest latest = GsonUtils.parseJSON(arg0, Latest.class);
		// List<Story> stories = latest.getStories();
		// mDataStories.addAll(stories);
		// mStoryAdapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onErrorResponse(VolleyError arg0) {
		// Log.e("latest onErrorResponse", arg0 == null ? "出错" :
		// arg0.getMessage());
		// }
		// });
		// }
	}

	private void initUI()
	{
		initListView();
	}

	private void initBannerHeader()
	{
		bannerHeader = inflater.inflate(R.layout.header_banner, null);
		mPager = (ViewPager) bannerHeader.findViewById(R.id.pager);
		mIndicator = (ViewPagerIndicator) bannerHeader.findViewById(R.id.indicator);
		FragmentManager fm = getChildFragmentManager();
		mBannerAdapter = new BannerAdapter(fm);
		mPager.setAdapter(mBannerAdapter);
		mPager.addOnPageChangeListener(new OnPageChangeListener()
		{
			public void onPageScrollStateChanged(int state)
			{
				switch (state)
				{
				case ViewPager.SCROLL_STATE_DRAGGING:
					isUserDrag = true;
					break;
				case ViewPager.SCROLL_STATE_IDLE:
					isUserDrag = false;
					break;
				case ViewPager.SCROLL_STATE_SETTLING:
					isUserDrag = false;
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageSelected(int position)
			{
				// TODO
				if (mDataTopStories.size() > 0)
				{
					position %= mDataTopStories.size();
					mIndicator.moveIndicator(position);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
				// TODO
				if (mDataTopStories.size() > 0)
				{
					position %= mDataTopStories.size();
					mIndicator.moveIndicator(position, positionOffset);
				}
			}
		});
		// 自动滚动
		autoScroll();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.e("BannerFragment", "onCreate");
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		hasDestroy = true;
		// 销毁时取消自动滚动
		Log.e("BannerFragment", "onDestroy");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		Log.e("BannerFragment", "onDestroyView");
	}

	private void autoScroll()
	{
		mPager.postDelayed(new Runnable()
		{
			public void run()
			{
				int item = mPager.getCurrentItem() + 1;
				// 必须同时符合两个条件才自动滚动：
				// 1) 不是用户手动拖拽
				// 2) 当前Fragment没有销毁
				if (!isUserDrag && !hasDestroy)
				{
					mPager.setCurrentItem(item);
				}
				if (!hasDestroy)
				{
					mPager.postDelayed(this, DURATION_SCROLL);
				}
			}
		}, DURATION_SCROLL);
	}

	private void initListView()
	{
		mListView = (ListView) layout.findViewById(R.id.listView1);
		// TODO 2016-06-02 复用
		if (isLatest)
		{
			initBannerHeader();
			mListView.addHeaderView(bannerHeader, null, false);
		}
		mStoryAdapter = new HomeAdapter();
		mListView.setAdapter(mStoryAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getContext(), "banner item: " + position, Toast.LENGTH_SHORT).show();
				position -= mListView.getHeaderViewsCount();
				Story story = mDataStories.get(position);
				Intent intent = new Intent(getContext(), DetailActivity.class);
				// Bundle b = new Bundle();
				// b.putSerializable("story", story);
				// intent.putExtras(b);
				// intent.putExtra("detail", "story").putExtra("story", story);
				intent.putExtra("story", story);
				startActivity(intent);
			}
		});
	}

	class BannerAdapter extends FragmentStatePagerAdapter
	{

		public BannerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			position %= mDataTopStories.size();
			TopStory topStory = mDataTopStories.get(position);
			return new BannerItemFragment(topStory);
		}

		@Override
		public int getCount()
		{
			return mDataTopStories.size() * MAX_BANNER_COUNT;
		}
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
