/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.galgame.august;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.fragment.CollectFragment;
import com.galgame.fragment.MainFragment;
import com.galgame.fragment.ThemesContentFragment;
import com.galgame.model.Other;
import com.galgame.model.Themes;
import com.galgame.util.ApiClient;
import com.galgame.util.SPUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainDrawerActivity extends AppCompatActivity implements OnClickListener
{
	private List<Other> mOthesMenu = new ArrayList<>();
	private Fragment mFragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawer;
	private ActionBarHelper mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	private MenuAdapter mMenuAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		isNight(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_layout);
		initUI();
		initData();
	}

	public static void isNight(Context context)
	{
		// 动态切换主题
		boolean isNight = SPUtils.isNight(context);
		if (isNight)
		{
			// 在super.onCreate()之前
			context.setTheme(R.style.night);
		} else
		{
			context.setTheme(R.style.light);
		}
	}

	private void initData()
	{
		ApiClient.getThemes(this, new ResponseListener()
		{
			@Override
			public void onResponse(String arg0)
			{
				Themes themes = GsonUtils.parseJSON(arg0, Themes.class);
				List<Other> others = themes.getOthers();
				mOthesMenu.addAll(others);
				mMenuAdapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
			}
		});
	}

	private void initUI()
	{
		initDrawerLayout();
		initDrawerMenu();
		initActionbar();
		initDrawerToggle();
		mFragment = new MainFragment();
		replaceFragment(mFragment);
	}

	private void initDrawerToggle()
	{
		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
	}

	private void initActionbar()
	{
		mActionBar = createActionBarHelper();
		mActionBar.init();
	}

	private void initDrawerMenu()
	{

		mDrawer = (ListView) findViewById(R.id.start_drawer);
		// 设置ListView可以选中，必须设置为单选模式
		mDrawer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		View headerView = getLayoutInflater().inflate(R.layout.zhihu_drawer_header, null);
		headerView.findViewById(R.id.drawer_user).setOnClickListener(this);
		headerView.findViewById(R.id.drawer_home_layout).setOnClickListener(this);
		headerView.findViewById(R.id.drawer_favorite).setOnClickListener(this);
		mDrawer.addHeaderView(headerView, null, false);
		mMenuAdapter = new MenuAdapter();
		mDrawer.setAdapter(mMenuAdapter);
		mDrawer.setOnItemClickListener(new DrawerItemClickListener());
	}

	class MenuAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return mOthesMenu.size();
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
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.list_item_menu, null);
			TextView tvMenuTitle = (TextView) layout.findViewById(R.id.tv_menu_title);
			Other other = mOthesMenu.get(position);
			tvMenuTitle.setText(other.getName());
			return layout;
		}

	}

	@Override
	public void onClick(View v)
	{
		mDrawerLayout.closeDrawer(mDrawer);
		switch (v.getId())
		{
		case R.id.drawer_user:
			mActionBar.setTitle("登录");
			startActivity(new Intent(MainDrawerActivity.this, LoginActivity.class));
			break;
		case R.id.drawer_home_layout:
			mActionBar.setTitle("首页");
			mFragment = new MainFragment();
			replaceFragment(mFragment);
			break;
		case R.id.drawer_favorite:
			mActionBar.setTitle("我的收藏");
			mFragment = new CollectFragment();
			replaceFragment(mFragment);
			break;

		default:
			break;
		}
	}

	private void initDrawerLayout()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerListener(new DemoDrawerListener());
		// The drawer title must be set in order to announce state changes when
		// accessibility is turned on. This is typically a simple description,
		// e.g. "Navigation".
		mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		/*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
		// mDrawerToggle
		if (mDrawerToggle.onOptionsItemSelected(item))
		{
			return true;
		}
		// TODO
		switch (item.getItemId())
		{
		case R.id.action_settings:
			startActivity(new Intent(this, SettingActivity.class));
			break;
		case R.id.action_dark_mode:
			nightMode();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			position -= mDrawer.getHeaderViewsCount();// hardcode
			Other other = mOthesMenu.get(position);
			mActionBar.setTitle(other.getName());
			mDrawerLayout.closeDrawer(mDrawer);
			mFragment = new ThemesContentFragment(other);
			replaceFragment(mFragment);
		}

	}

	private void replaceFragment(Fragment fragment)
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.container, fragment);
		ft.commit();
	}

	/**
	 * A drawer listener can be used to respond to drawer events such as
	 * becoming fully opened or closed. You should always prefer to perform
	 * expensive operations such as drastic relayout when no animation is
	 * currently in progress, either before or after the drawer animates.
	 *
	 * When using ActionBarDrawerToggle, all DrawerLayout listener methods
	 * should be forwarded if the ActionBarDrawerToggle is not used as the
	 * DrawerLayout listener directly.
	 */
	private class DemoDrawerListener implements DrawerLayout.DrawerListener
	{
		@Override
		public void onDrawerOpened(View drawerView)
		{
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView)
		{
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset)
		{
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState)
		{
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

	/**
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */
	private ActionBarHelper createActionBarHelper()
	{
		return new ActionBarHelper();
	}

	/**
	 * Action bar helper for use on ICS and newer devices.
	 */
	private class ActionBarHelper
	{
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		ActionBarHelper()
		{
			Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(toolbar);
			mActionBar = getSupportActionBar();
		}

		public void init()
		{
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(false);
			mTitle = mDrawerTitle = getTitle();
		}

		/**
		 * When the drawer is closed we restore the action bar state reflecting
		 * the specific contents in view.
		 */
		public void onDrawerClosed()
		{
			mActionBar.setTitle(mTitle);
			Log.e("mTitle", "Toolbar标题" + mTitle);
		}

		/**
		 * When the drawer is open we set the action bar to a generic title. The
		 * action bar should only contain data relevant at the top level of the
		 * nav hierarchy represented by the drawer, as the rest of your content
		 * will be dimmed down and non-interactive.
		 */
		public void onDrawerOpened()
		{
			mActionBar.setTitle(mDrawerTitle);
			Log.e("mDrawerTitle", "Toolbar标题" + mDrawerTitle);
		}

		public void setTitle(CharSequence title)
		{
			Log.e("title", "Toolbar标题" + title);
			mTitle = title;
			mActionBar.setTitle(mTitle);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_dark_mode);
		boolean isNight = SPUtils.isNight(this);
		if (isNight)
		{
			item.setTitle("日间模式");
		}
		return true;
	}

	public void nightMode()
	{
		boolean isNight = SPUtils.isNight(this);
		SPUtils.setMode(this, !isNight);
		finish();
		startActivity(new Intent(this, MainDrawerActivity.class));
		// 取消Activity切换动画
		overridePendingTransition(0, 0);
	}
}
