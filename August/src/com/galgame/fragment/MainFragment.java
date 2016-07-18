package com.galgame.fragment;

import com.astuetz.PagerSlidingTabStrip;
import com.galgame.august.R;
import com.galgame.util.DateUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment
{
	private String[] mPageTitles;
	private ViewPager mPager;
	private PagerSlidingTabStrip tabs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mPageTitles = getResources().getStringArray(R.array.main_fragment_title);
		// 修改每个标题
		// 特例：计算昨天的日期标题
		// Calendar dateToGetUrl = Calendar.getInstance();
		// dateToGetUrl.add(Calendar.DAY_OF_YEAR, -1);
		// String date =
		// ZHConstants.Dates.simpleDateFormat.format(dateToGetUrl.getTime());
		// mPageTitles[2] = date;
		// // 特例：计算前天的日期标题
		// Calendar dateToGetUrl1 = Calendar.getInstance();
		// dateToGetUrl1.add(Calendar.DAY_OF_YEAR, -2);
		// String date1 =
		// ZHConstants.Dates.simpleDateFormat.format(dateToGetUrl1.getTime());
		// mPageTitles[3] = date1;
		// // 特例：计算大前天的日期标题
		// Calendar dateToGetUrl2 = Calendar.getInstance();
		// dateToGetUrl2.add(Calendar.DAY_OF_YEAR, -3);
		// String date2 =
		// ZHConstants.Dates.simpleDateFormat.format(dateToGetUrl2.getTime());
		// mPageTitles[4] = date2;

		// 规律
		for (int i = 0; i < mPageTitles.length - 2; i++)
		{
			String date = DateUtils.getFormatDate(-1 - i);
			mPageTitles[i + 2] = date;
		}

		View layout = inflater.inflate(R.layout.fragment_main, null);
		mPager = (ViewPager) layout.findViewById(R.id.pager);
		// ��Fragment�б���ʹ��childFragmentManager()
		FragmentManager fm = getChildFragmentManager();
		mPager.setAdapter(new MyAdapter(fm));

		// ������pager��������֮�����
		tabs = (PagerSlidingTabStrip) layout.findViewById(R.id.tabs);
		tabs.setViewPager(mPager);
		// �ı�ViewPager�������
		mPager.setOffscreenPageLimit(3);
		return layout;
	}

	class MyAdapter extends FragmentPagerAdapter
	{
		public MyAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return mPageTitles[position];
		}

		public Fragment getItem(int position)
		{
			if (position == 0)
			{
				return new HotFragment();
			} else
			{
				return new BannerFragment(position);
			}
			// switch (position) {
			// case 0:// 热门
			// return new HotFragment();
			// case 1:// 最新
			// return new BannerFragment(position);
			// case 2:// 昨天
			// return new BannerFragment(position);
			// case 3:// 前天
			// return new BannerFragment(position);
			// case 4:// 大前天
			// return new BannerFragment(position);
			// default:
			// return new BannerFragment(position);
			// }
			// return null;
		}

		public int getCount()
		{
			return mPageTitles.length;
		}

	}
}
