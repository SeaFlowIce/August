package com.galgame.august;

import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.model.Detail;
import com.galgame.model.Extra;
import com.galgame.model.Recent;
import com.galgame.model.Story;
import com.galgame.util.ApiClient;
import com.galgame.util.DBUtils;
import com.galgame.util.ImageUtils;
import com.galgame.util.SPUtils;
import com.galgame.util.ZHConstants;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.ResponseListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DetailActivity extends AppCompatActivity implements OnClickListener
{

	private WebView mWebView;
	private ImageView mImgHeader;
	private ImageView mImgCollect;
	private Story mStory;
	private Recent mRecent;
	private ActionBar mActionBar;
	private ImageView mImgShare;
	private Detail detail;
	private Extra mExtra;
	private TextView mCommentTv;
	private TextView mVoteTv;
	private TextView mImgTitle;
	private ImageView mImgVote;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		MainDrawerActivity.isNight(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initUI();
		initData();
		// initVote();
	}

	private void initVote()
	{
		// TODO Auto-generated method stub
		boolean isVote = SPUtils.isVote(this);
		if (isVote)
		{
			mImgVote.setImageResource(R.drawable.praised);
			mVoteTv.setText(mExtra.getPopularity() + 1 + "");
		} else
		{
			mImgVote.setImageResource(R.drawable.praise);
			mVoteTv.setText(mExtra.getPopularity() + "");
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.action_collect:
			collect();
			break;
		case R.id.action_share:// TODO 简单分享 V4Demo SharingSupport
			// ShareCompat.IntentBuilder.from(this).setType("text/plain").setText("I'm
			// sharing!").startChooser();
			ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(detail.getShareUrl()).startChooser();
		case R.id.action_comment:
			Intent intent = new Intent(this, CommentActivity.class);
			// intent.putExtra("Detail", mStory);
			// intent.putExtra("Detail", mRecent);
			intent.putExtra("Detail", id);
			startActivity(intent);
			break;
		case R.id.action_vote:
			Vote();
			break;

		default:
			break;
		}
	}

	private void Vote()
	{
		boolean isVote = SPUtils.isVote(this);
		SPUtils.setVote(this, !isVote);
		if (!isVote)
		{
			mImgVote.setImageResource(R.drawable.praised);
			mVoteTv.setText(mExtra.getPopularity() + 1 + "");
		} else
		{
			mImgVote.setImageResource(R.drawable.praise);
			mVoteTv.setText(mExtra.getPopularity() + "");
		}
	}

	private void collect()
	{
		boolean hasCollected = DBUtils.hasCollected(mStory);
		if (hasCollected)
		{
			mImgCollect.setImageResource(R.drawable.collect);
			DBUtils.cancelCollect(mStory);
		} else
		{
			mImgCollect.setImageResource(R.drawable.collected);
			DBUtils.collect(mStory);
		}
	}

	private void initData()
	{

		Intent intent = getIntent();
		mStory = (Story) intent.getSerializableExtra("story");
		// Bundle extras = intent.getExtras();
		// String string = intent.getStringExtra("detail");
		// mRecent = (Recent) intent.getSerializableExtra("recent");
		// mStory = (Story) intent.getSerializableExtra("story");
		// if (string.equals("recent"))
		// {
		// id = mRecent.getNewsId();
		// } else
		// {
		// id = mStory.getStoryId();
		// }
		id = mStory.getStoryId();
		boolean hasCollected = DBUtils.hasCollected(mStory);
		if (hasCollected)
		{
			mImgCollect.setImageResource(R.drawable.collected);
		}
		ApiClient.getDetail(this, id, new ResponseListener()
		{
			@Override
			public void onResponse(String arg0)
			{
				detail = GsonUtils.parseJSON(arg0, Detail.class);
				mImgTitle.setText(detail.getTitle());
				// UILUtils.displayImageNoAnim(detail.getImage(), mImgHeader);
				ImageUtils.displayImageNoAnim(DetailActivity.this, detail.getImage(), mImgHeader);
				String body = detail.getBody();
				List<String> cssList = detail.getCss();
				// 使用CSS设置HTM的风格
				if (cssList.size() > 0)
				{
					// 不适用官方的css，因为设置了img-place-holder的高度为200
					// String css = cssList.get(0);
					String cssLink = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"> ";
					String formatCss = String.format(cssLink, ZHConstants.url.css);
					body = formatCss + body;
				}
				mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("DetailActivity", arg0 == null ? "error" : arg0.getMessage() + "");
			}
		});
		ApiClient.getExtra(this, id, new ResponseListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				mExtra = GsonUtils.parseJSON(arg0, Extra.class);
				mCommentTv.setText(mExtra.getComments() + "");
				boolean isVote = SPUtils.isVote(DetailActivity.this);
				if (isVote)
				{
					mImgVote.setImageResource(R.drawable.praised);
					mVoteTv.setText(mExtra.getPopularity() + 1 + "");
				} else
				{
					mImgVote.setImageResource(R.drawable.praise);
					mVoteTv.setText(mExtra.getPopularity() + "");
				}
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				Log.e("onErrorResponse", arg0 == null ? "null" : arg0.getMessage());
			}
		});

	}

	private void initUI()
	{
		mImgHeader = (ImageView) findViewById(R.id.img_header);
		mImgCollect = (ImageView) findViewById(R.id.action_collect);
		mImgShare = (ImageView) findViewById(R.id.action_share);
		mImgVote = (ImageView) findViewById(R.id.action_item_vote_image);
		mCommentTv = (TextView) findViewById(R.id.action_item_comment_text);
		mVoteTv = (TextView) findViewById(R.id.action_item_vote_text);
		mImgTitle = (TextView) findViewById(R.id.tv_detail_imgTitle);
		mImgCollect.setOnClickListener(this);
		mImgShare.setOnClickListener(this);
		findViewById(R.id.action_comment).setOnClickListener(this);
		findViewById(R.id.action_vote).setOnClickListener(this);

		initWebView();
		initToolbar();
	}

	private void initToolbar()
	{

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		// TODO
		mActionBar.setTitle("");
	}

	private void initWebView()
	{
		mWebView = (WebView) findViewById(R.id.webView1);
		// TODO
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		boolean isBigSize = com.galgame.util.SPUtils.isBigText(this);
		boolean isNight = SPUtils.isNight(this);
		if (isNight)
		{
			mWebView.setBackgroundColor(Color.parseColor("#35364F"));

		}
		if (isBigSize)
		{
			settings.setTextSize(TextSize.LARGER);
		} else
		{
			settings.setTextSize(TextSize.NORMAL);
		}
		// settings.setTextSize(TextSize.NORMAL);
		mWebView.setWebViewClient(new WebViewClient());
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
