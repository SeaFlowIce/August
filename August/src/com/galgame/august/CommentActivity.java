package com.galgame.august;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.galgame.model.Comment;
import com.galgame.model.Comments;
import com.galgame.model.ReplyTo;
import com.galgame.model.Story;
import com.galgame.util.ApiClient;
import com.galgame.util.ImageUtils;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.PrettyTimeUtils;
import com.xinbo.utils.ResponseListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CommentActivity extends AppCompatActivity
{

	private ListView mListView;
	private CommentAdapter mCommentadapter;
	private List<Comment> mDataCommentLong = new ArrayList<>();
	private List<Comment> mDataCommentShort = new ArrayList<>();
	private boolean IsCommentShort;
	private Story mStory;
	private ViewHolder holder = null;
	private ActionBar mActionBar;
	private boolean showText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		MainDrawerActivity.isNight(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		Log.e("CommentActivity", "onCreate");
		initUI();
		initData();
	}

	private void initUI()
	{
		initToolbar();
		initListView();
	}

	private void initToolbar()
	{
		Log.e("CommentActivity", "initToolbar");
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

	}

	private void imgRotateAndTranslate()
	{
		holder.imgIcon.setScaleType(ImageView.ScaleType.MATRIX);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imgIcon.getDrawable();
		Bitmap mBitmap = bitmapDrawable.getBitmap();
		Matrix matrix = new Matrix();
		// int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		float matrixValues[] =
		{ 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
		matrix.setValues(matrixValues);
		matrix.postTranslate(0, height);
		// matrix.postRotate(180f, width / 2, height / 2);
		// matrix.postTranslate(width, height);
		holder.imgIcon.setImageMatrix(matrix);
	}

	private void initListView()
	{
		Log.e("CommentActivity", "initListView");
		mListView = (ListView) findViewById(R.id.listView1);
		mCommentadapter = new CommentAdapter();
		mListView.setAdapter(mCommentadapter);

		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				if (position == mDataCommentLong.size() + 1)
				{
					// holder.imgIcon.clearAnimation();
					// imgRotateAndTranslate();
					IsCommentShort = !IsCommentShort;

					if (IsCommentShort)
					{
						mListView.setSelection(position);
						RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
								Animation.RELATIVE_TO_SELF, 0.5f);
						animation.setDuration(800);
						animation.setFillAfter(true);
						holder.imgIcon.startAnimation(animation);
						// Animation animation =
						// AnimationUtils.loadAnimation(CommentActivity.this,
						// R.anim.rotate_comment_short1);
						// holder.imgIcon.startAnimation(animation);

					} else
					{
						mListView.setSelection(0);
						// RotateAnimation animation = new RotateAnimation(180,
						// 0, Animation.RELATIVE_TO_SELF, 0.5f,
						// Animation.RELATIVE_TO_SELF, 0.5f);
						// animation.setDuration(800);
						// animation.setFillAfter(true);
						// holder.imgIcon.startAnimation(animation);
						// Handler handler = new Handler();
						// handler.postDelayed(new Runnable()
						// {
						// @Override
						// public void run()
						// {
						// mListView.setSelection(0);
						// }
						// }, 1100);

						// Animation animation =
						// AnimationUtils.loadAnimation(CommentActivity.this,
						// R.anim.rotate_comment_short2);
						// holder.imgIcon.startAnimation(animation);
					}
				}
			}
		});
	}

	private void initData()
	{
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// mStory = (Story) intent.getSerializableExtra("story");
		// int id = mStory.getStoryId();
		int id = intent.getIntExtra("Detail", 0);
		Log.e("CommentActivity", "initData=====" + id);
		ApiClient.getCommentLong(this, id, new ResponseListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				// TODO Auto-generated method stub
				Log.e("CommentActivity", "getCommentLong onResponse");
				Comments comments = GsonUtils.parseJSON(arg0, Comments.class);
				List<Comment> comment = comments.getComments();
				mDataCommentLong.addAll(comment);
				mCommentadapter.notifyDataSetChanged();
			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		ApiClient.getCommentShort(this, id, new ResponseListener()
		{

			@Override
			public void onResponse(String arg0)
			{
				// TODO Auto-generated method stub
				Comments comments = GsonUtils.parseJSON(arg0, Comments.class);
				List<Comment> comment = comments.getComments();
				mDataCommentShort.addAll(comment);
				mCommentadapter.notifyDataSetChanged();

			}

			@Override
			public void onErrorResponse(VolleyError arg0)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	class ViewHolder
	{
		TextView tvHeader;
		ImageView imgIcon;
		ImageView imgAvatar;
		TextView tvAuthor;
		TextView tvContent;
		TextView tvTime;
		TextView tvReply;
		TextView tvExpand;

	}

	class CommentAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			if (IsCommentShort)
			{
				return mDataCommentShort.size() + mDataCommentLong.size() + 2;
			} else
			{
				return mDataCommentLong.size() + 2;
			}
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

		@SuppressLint("InflateParams")
		@SuppressWarnings("null")
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			Log.e("CommentActivity", "getView");

			if (convertView == null)
			{
				holder = new ViewHolder();
				if (position == 0 || position == mDataCommentLong.size() + 1)
				{
					convertView = getLayoutInflater().inflate(R.layout.list_comment_item1, null);
					Log.e("CommentActivity", "list_comment_item1");
					holder.tvHeader = (TextView) convertView.findViewById(R.id.comment_list_header_text);
					holder.imgIcon = (ImageView) convertView.findViewById(R.id.comment_list_header_icon);
				} else
				{
					Log.e("CommentActivity", "list_comment_item2");
					convertView = getLayoutInflater().inflate(R.layout.list_comment_item2, null);
					holder.imgAvatar = (ImageView) convertView.findViewById(R.id.comment_item_avatar);
					holder.tvAuthor = (TextView) convertView.findViewById(R.id.comment_item_author);
					holder.tvContent = (TextView) convertView.findViewById(R.id.comment_item_content);
					holder.tvTime = (TextView) convertView.findViewById(R.id.comment_item_time);
					holder.tvReply = (TextView) convertView.findViewById(R.id.comment_replied_content);
					holder.tvExpand = (TextView) convertView.findViewById(R.id.comment_expand_button);
				}
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == 0)
			{
				holder.imgIcon.setVisibility(View.GONE);
				holder.tvHeader.setText("0条长评");
				if (mDataCommentLong.size() > 0)
				{
					holder.tvHeader.setText(mDataCommentLong.size() + "条长评");
				}
			} else if (position == mDataCommentLong.size() + 1)
			{
				// convertView.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// showShort = !showShort;
				// getCommentsShort();
				// }
				// });
				// holder.imgIcon.setVisibility(View.VISIBLE);
				// holder.tvHeader.setText("条短评");
				holder.imgIcon.setVisibility(View.VISIBLE);
				holder.tvHeader.setText("0条短评");
				if (mDataCommentShort.size() > 0)
				{
					holder.tvHeader.setText(mDataCommentShort.size() + "条短评论");

				}

			} else
			{
				Comment comment;
				if (position <= mDataCommentLong.size() + 1)
				{
					comment = mDataCommentLong.get(position - 1);
				} else
				{
					comment = mDataCommentShort.get(position - 2 - mDataCommentLong.size());
				}

				ImageUtils.displayCircleImage(CommentActivity.this, comment.getAvatar(), holder.imgAvatar);
				holder.tvAuthor.setText(comment.getAuthor());
				holder.tvContent.setText(comment.getContent());

				// 美化时间格式
				String prettyTime = PrettyTimeUtils.getPrettyTime(comment.getTime() * 1000L);
				holder.tvTime.setText(prettyTime);
				ReplyTo replyTo = comment.getReplyTo();
				if (replyTo == null)
				{
					holder.tvReply.setVisibility(View.GONE);
				} else
				{
					holder.tvReply.setVisibility(View.VISIBLE);
					holder.tvReply.setText(replyTo.toString());
					if (holder.tvReply.getLineCount() > 2)
					{
						holder.tvExpand.setVisibility(View.VISIBLE);
						holder.tvExpand.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								// TODO
								Log.e("tag", "" + showText);
								// showText = !showText;
								// Log.e("tag", "" + showText);
								// if (showText)
								if (!showText)
								{
									showText = true;
									holder.tvReply.setMaxLines(Integer.MAX_VALUE);
									holder.tvExpand.setText(R.string.comment_colspan);
								} else
								{
									showText = false;
									holder.tvReply.setMaxLines(2);
									holder.tvExpand.setText(R.string.comment_expand);
								}
							}
						});
					}
				}

			}
			// TODO
			mActionBar.setTitle(mDataCommentLong.size() + mDataCommentShort.size() + "条点评");
			return convertView;
		}

		// TODO
		@Override
		public int getViewTypeCount()
		{

			return 2;
		}

		@Override
		public int getItemViewType(int position)
		{
			if (position == 0 || position == mDataCommentLong.size() + 1)
			{
				return 0;
			} else
			{
				return 1;
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
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
