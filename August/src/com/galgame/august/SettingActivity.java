package com.galgame.august;

import com.galgame.util.SPUtils;
import com.galgame.util.UpdateDialog;
import com.galgame.util.UpdateUtil;
import com.galgame.util.UpdateDialog.OnConfirmListener;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SettingActivity extends AppCompatActivity implements OnClickListener, OnCheckedChangeListener
{

	private CheckBox mCheckNoPic;
	private CheckBox mCheckBigText;
	private Toolbar toolbar;
	String apkUrl = "http://192.168.1.100:8080/a.apk";
	int newCode = 2;
	private UpdateUtil util;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		MainDrawerActivity.isNight(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initUI();

	}

	private void initUI()
	{
		initToolbar();
		initNoPic();
		initBigTextsize();
		initClearCache();
		initSendEmail();
		initUpData();
	}

	private void initUpData()
	{
		findViewById(R.id.layout_updata).setOnClickListener(this);

	}

	private void initToolbar()
	{
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setTitle("设置");
	}

	private void initNoPic()
	{
		findViewById(R.id.layout_nopic).setOnClickListener(this);
		mCheckNoPic = (CheckBox) findViewById(R.id.check_nopic);
		boolean isNoPic = SPUtils.isNoPic(this);
		mCheckNoPic.setChecked(isNoPic);
		mCheckNoPic.setOnCheckedChangeListener(this);
	}

	private void initBigTextsize()
	{
		findViewById(R.id.layout_bigtext).setOnClickListener(this);
		mCheckBigText = (CheckBox) findViewById(R.id.check_bigtext);
		boolean isBigText = SPUtils.isBigText(this);
		mCheckBigText.setChecked(isBigText);
		mCheckBigText.setOnCheckedChangeListener(this);
	}

	private void initClearCache()
	{
		findViewById(R.id.layout_clear_cache).setOnClickListener(this);
	}

	private void initSendEmail()
	{
		findViewById(R.id.layout_send_email).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.layout_nopic:
			boolean checkedNoPic = mCheckNoPic.isChecked();
			mCheckNoPic.setChecked(!checkedNoPic);
			break;
		case R.id.layout_bigtext:
			boolean checkedBigText = mCheckBigText.isChecked();
			mCheckNoPic.setChecked(!checkedBigText);
			break;
		case R.id.layout_clear_cache:
			DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
			diskCache.clear();
			Toast.makeText(this, "清除完成", Toast.LENGTH_SHORT).show();
			break;
		case R.id.layout_send_email:
			// Intent.ACTION_SENDTO 无附件的发送
			// Intent.ACTION_SEND 带附件的发送
			// Intent.ACTION_SEND_MULTIPLE 带有多附件的发送
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
			// 设置文本格式
			emailIntent.setType("text/plain");
			// //设置对方邮件地址
			// emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
			// //设置标题内容
			// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.setting_recommend_words));
			// //设置邮件文本内容
			// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,getString(R.string.setting_recommend_words));
			startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
			break;
		case R.id.layout_updata:
			UpData();
			break;

		default:
			break;
		}
	}

	private void UpData()
	{
		util = new UpdateUtil(this);
		// 判断是否需要更新
		if (newCode > util.getVeisionCode())
		{
			String content = "1.解决了XXXBUG\n2.更新XXX更能\n3.提高用户体验";
			UpdateDialog updateDialog = new UpdateDialog(content);
			updateDialog.show(getSupportFragmentManager(), null);
			updateDialog.setOnConfirmListener(new OnConfirmListener()
			{

				@Override
				public void onConfirm()
				{
					util.update(apkUrl, false);
				}
			});
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		switch (buttonView.getId())
		{
		case R.id.check_nopic:
			SPUtils.setNoPic(SettingActivity.this, isChecked);
			break;
		case R.id.check_bigtext:
			SPUtils.setBigText(SettingActivity.this, isChecked);
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
