package com.galgame.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.august.R;
import com.galgame.model.Editor;
import com.galgame.model.Other;
import com.galgame.model.Story;
import com.galgame.model.ThemesContent;
import com.galgame.util.ApiClient;
import com.galgame.util.ImageUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ThemesContentFragment extends Fragment
{

	private List<Story> mDataStories = new ArrayList<>();
	private LayoutInflater inflater;
	private ListView mListView;
	private View layout;
	private View mHeader;
	protected boolean isUserDrag;
	protected boolean hasDestroy;
	private HomeAdapter mStoryAdapter;
	private Other other;
	private TextView mTvDesc;
	private ImageView mImgIcon;
	private LinearLayout mEditorLayout;

	public ThemesContentFragment(Other other)
	{
		this.other = other;
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
		int id = other.getId();

		ApiClient.getThemesContent(getContext(), id, new ResponseListener()
		{
			private Editor editor;

			public void onResponse(String arg0)
			{
				if (TextUtils.isEmpty(arg0))
				{
					// 提示错误
					return;
				}
				// 更新列表数据
				ThemesContent content = GsonUtils.parseJSON(arg0, ThemesContent.class);
				List<Story> stories = content.getStories();
				mDataStories.addAll(stories);
				mStoryAdapter.notifyDataSetChanged();

				// 更新HeaderView数据
				mTvDesc.setText(content.getDescription());
				ImageUtils.displayImage(getContext(), content.getImage(), mImgIcon);
				// 动态向布局中添加主编圆形头像
				List<Editor> editors = content.getEditors();
				for (int i = 0; i < editors.size(); i++)
				{
					editor = editors.get(i);
					View child = inflater.inflate(R.layout.header_item_icon, null);
					ImageView imgAvatar = (ImageView) child.findViewById(R.id.img_avatar);
					// UILUtils.displayCircleImage(editor.getAvatar(),
					// imgAvatar);
					ImageUtils.displayCircleImage(getContext(), editor.getAvatar(), imgAvatar);
					mEditorLayout.addView(child);
					imgAvatar.setOnClickListener(new OnClickListener()
					{

						@Override
						public void onClick(View v)
						{
							Toast.makeText(getContext(), "onclick" + editor.getName(), Toast.LENGTH_SHORT).show();
							;
						}
					});
				}
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

	private void initHeader()
	{
		mHeader = inflater.inflate(R.layout.header_theme, null);
		mImgIcon = (ImageView) mHeader.findViewById(R.id.img_icon);
		mTvDesc = (TextView) mHeader.findViewById(R.id.tv_desc);
		mEditorLayout = (LinearLayout) mHeader.findViewById(R.id.editor_layout);
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

	private void initListView()
	{
		mListView = (ListView) layout.findViewById(R.id.listView1);
		// TODO 2016-06-02 复用
		initHeader();
		mListView.addHeaderView(mHeader, null, false);
		mStoryAdapter = new HomeAdapter();
		mListView.setAdapter(mStoryAdapter);
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
				holder.imgIcon.setVisibility(View.VISIBLE);
				String imageUrl = images.get(0);
				// 当有的行有图片有的行没有图片时，需要使用无动画的方法
				// UILUtils.displayImageNoAnim(imageUrl, holder.imgIcon);
				ImageUtils.displayImageNoAnim(getContext(), imageUrl, holder.imgIcon);
			} else
			{
				holder.imgIcon.setVisibility(View.GONE);
			}
			return convertView;
		}

	}

}
