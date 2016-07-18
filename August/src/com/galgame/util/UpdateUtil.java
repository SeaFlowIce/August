package com.galgame.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.galgame.august.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;

public class UpdateUtil
{

	private Context context;
	// 新版本号

	private Notification notification;
	private NotificationManager manager;

	public UpdateUtil(Context context)
	{
		this.context = context;

	}

	/**
	 * 下载更新
	 * 
	 * @param newCode：服务中获取的版本号
	 * @param url：新版本APK下载地址
	 * @param isAuto：是否需要下载完成自动跳出安装界面
	 */
	public void update(String url, boolean isAuto)
	{
		new MyTask(isAuto).execute(url);
	}

	/**
	 * 获得当前应用的版本号
	 */
	public int getVeisionCode()
	{
		int versionCode = 1000;
		// 获得包管理器
		PackageManager pm = context.getPackageManager();
		try
		{
			// 获得包的信息
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			// 获得版本号
			versionCode = packageInfo.versionCode;
			String name = packageInfo.versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return versionCode;
	}

	class MyTask extends AsyncTask<String, Integer, String>
	{
		private boolean isAuto;

		public MyTask(boolean isAuto)
		{
			this.isAuto = isAuto;
		}

		@Override
		protected String doInBackground(String... params)
		{
			String str = "下载失败";
			try
			{
				URL url = new URL(params[0]);
				URLConnection openConnection = url.openConnection();
				InputStream is = openConnection.getInputStream();
				int length = openConnection.getContentLength();
				FileOutputStream fos = context.openFileOutput("a.apk",
						Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
				byte[] buffer = new byte[1024];
				int len;
				int sum = 0;
				int node = 5120;
				int nodeNum = 0;
				while (-1 != (len = is.read(buffer)))
				{
					sum += len;
					fos.write(buffer, 0, len);
					if (sum >= node * nodeNum)
					{
						publishProgress(sum * 100 / length);
						nodeNum++;
					}
				}
				str = "下载成功";
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return str;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
			// tv.setText("下载"+values[0]+"%");

			notification.contentView.setTextViewText(R.id.tv_progress, "下载" + values[0] + "%");
			notification.contentView.setProgressBar(R.id.progressBar1, 100, values[0], false);
			manager.notify(1, notification);
		}

		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			notification.contentView.setTextViewText(R.id.tv_progress, "下载完成");
			notification.contentView.setProgressBar(R.id.progressBar1, 100, 100, false);
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 指定打开的文件路径，已经类型
			intent.setDataAndType(Uri.fromFile(new File(context.getFilesDir() + "/a.apk")),
					"application/vnd.android.package-archive");
			if (isAuto)
			{
				// 下载完成自动弹出安装提示
				context.startActivity(intent);
			} else
			{
				// 点击Notification界面跳转
				notification.contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
				manager.notify(1, notification);
			}

		}

		@Override
		protected void onPreExecute()
		{
			showNotify();
			super.onPreExecute();
		}

	}

	private void showNotify()
	{
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder builder = new Builder(context);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
		notification = builder.setTicker("下载更新").setWhen(System.currentTimeMillis())
				.setSmallIcon(R.drawable.ic_launcher).setContent(views).build();

		manager.notify(1, notification);
	}
}
