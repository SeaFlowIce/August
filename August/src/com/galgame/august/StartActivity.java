package com.galgame.august;

import com.android.volley.VolleyError;
import com.galgame.model.Start;
import com.galgame.util.ApiClient;
import com.galgame.util.ImageUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class StartActivity extends AppCompatActivity
{

	private ImageView mImg;
	private TextView mTv;
	private Handler handler;
	private Runnable r;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		initUI();
		// initData();
		initACG();
		initAnim();
		initDelayed();
	}

	// 进行延时跳转
	private void initDelayed()
	{
		handler = new Handler();
		r = new Runnable()
		{

			@Override
			public void run()
			{
				startActivity(new Intent(StartActivity.this, MainDrawerActivity.class));
				finish();
			}
		};
		handler.postDelayed(r, 5000);
	}

	private void initUI()
	{
		mImg = (ImageView) findViewById(R.id.img_start);
		mTv = (TextView) findViewById(R.id.tv_start);
	}

	private void initACG()
	{
		mImg.setImageResource(R.drawable.eris_8);
		mTv.setText("Eris Floralia");
		TextView mtv1 = (TextView) findViewById(R.id.tv_start_title);
		TextView mtv2 = (TextView) findViewById(R.id.tv_start_helloworld);
		mtv1.setText("八月速递");
		mtv2.setText("秽翼的尤斯蒂娅");
	}

	private void initAnim()
	{
		Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.alpha_start);
		Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.trans_start);
		View view = findViewById(R.id.start_LL);
		view.startAnimation(animation2);
		mImg.startAnimation(animation1);

	}

	private void initData()
	{
		ApiClient.getStart(this, new ResponseListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				Start start = GsonUtils.parseJSON(arg0, Start.class);
				mTv.setText(start.getText());
				String img = start.getImg();
				ImageUtils.displayImage(StartActivity.this, img, mImg);
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("StartActivity", arg0.getMessage());
			}
		});
	}

	public void btnEnd(View v)
	{
		// 关闭延时跳转的线程
		handler.removeCallbacks(r);

		startActivity(new Intent(StartActivity.this, MainDrawerActivity.class));
		finish();
	}
}
