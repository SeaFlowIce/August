package com.xinbo.upgrade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yuchen.lib.R;

public class DownloadFile {

	private static Context mContext;
	private static NotificationManager mNotifiMgr;
	private static Notification mNotifi;
	private static RemoteViews mNotifiviews;
	
	public static void upgrade(Context context,String downloadUrl,String filePath,Class cls) {
		mContext = context;
		// 下载
		new UpgradeTask(filePath,cls).execute(downloadUrl);
	}

	static class UpgradeTask extends AsyncTask<String, Integer, Void> {
		String filePath;
		Class cls;
		@Override
		protected void onPreExecute() {
			// 发送通知显示升级进度
			sendNotify();
		}
		public UpgradeTask(String filePath,Class cls) {
			this.filePath = filePath;
			this.cls = cls;
		}
		@Override
		protected Void doInBackground(String... params) {
			String downUrl = params[0];
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				URL url = new URL(downUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 设置连接超时时间
				conn.setConnectTimeout(25000);
				// 设置下载数据超时时间
				conn.setReadTimeout(25000);
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					// 服务端错误响应
					return null;
				}

				int fileLength = conn.getContentLength();
				is = conn.getInputStream();
				File downFile = new File(filePath+new File(downUrl).getName());
				// 如果文件夹不存在则创建
				if (!downFile.getParentFile().exists()) {
					downFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(downFile);
				byte[] buffer = new byte[1024];
				int len = 0;
				int loadedLen = 0;// 当前已下载文件大小
				// 更新13次
				int updateSize = fileLength / 13;
				int num = 0;
				while (-1 != (len = is.read(buffer))) {
					loadedLen += len;
					fos.write(buffer, 0, len);
					if (loadedLen > updateSize * num) {
						num++;
						publishProgress(loadedLen,fileLength);
					}
				}
				fos.flush();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				// 处理超时异常，提示用户在网络良好情况下重试

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 更新通知
			updateNotify(values[0],values[1]);
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(mContext, "下载完成，请点击通知栏完成升级", Toast.LENGTH_LONG)
					.show();

			finishNotify(cls);
		}
	}

	//启动通知栏
	private static void sendNotify() {
		Intent intent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, 0);
		mNotifiviews = new RemoteViews(mContext.getPackageName(), R.layout.custom_notify);
		mNotifiviews.setViewVisibility(R.id.tv_subtitle, View.VISIBLE);
		mNotifiviews.setViewVisibility(R.id.progressBar1, View.VISIBLE);

		mNotifi = new NotificationCompat.Builder(mContext)
				.setContent(mNotifiviews)
				.setAutoCancel(true)
				// 单击后自动删除
				// .setOngoing(true)// 无法删除的通知
				// 定制通知布局
				.setSmallIcon(R.drawable.ic_launcher).setTicker("ticker")
				.setWhen(System.currentTimeMillis()).setSound(Uri.parse(""))
				.setVibrate(new long[] { 0, 100, 300, 400 })
				.setContentIntent(contentIntent).build();
		mNotifiMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifiMgr.notify(12345, mNotifi);
	}

	//更新通知栏
	private static void updateNotify(int loadedLen,int totalLen) {
		int progress = loadedLen * 100 / totalLen;
		mNotifiviews.setTextViewText(R.id.tv_subtitle, progress + "%");
		mNotifiviews.setProgressBar(R.id.progressBar1, totalLen,
				loadedLen, false);
		// mNotifiviews.setViewVisibility(R.id.tv_title, View.INVISIBLE);
		mNotifiMgr.notify(12345, mNotifi);
	}

	//下载完成时的通知栏
	private static void finishNotify(Class cls) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.fromFile(new File(APK_UPGRADE)),
//				"application/vnd.android.package-archive");
		intent.setClass(mContext, cls);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, 0);
		mNotifi.contentIntent = contentIntent;
		mNotifiviews.setTextViewText(R.id.tv_title, "下载完成，请点击完成升级");
		mNotifiviews.setViewVisibility(R.id.tv_subtitle, View.INVISIBLE);
		mNotifiviews.setViewVisibility(R.id.progressBar1, View.INVISIBLE);
		mNotifiMgr.notify(12345, mNotifi);
	}
}
