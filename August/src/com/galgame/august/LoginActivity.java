package com.galgame.august;

import java.text.SimpleDateFormat;

import com.galgame.util.AccessTokenKeeper;
import com.galgame.util.Constants;
import com.galgame.util.ImageUtils;
import com.galgame.util.MyUser;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;
import com.xinbo.utils.RegexValidateUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends Activity implements Callback
{
	private EditText mEditPwd;
	private EditText mEditUser;
	private String user;
	private String pwd;
	private View mLayoutLogin;
	private View mLayoutUnLogin;
	private View mrelativeLayout;
	private EditText mEditCode;

	private static final String TAG = LoginActivity.class.getName();

	/** 显示认证后的信息，如 AccessToken */
	private TextView mTokenText;

	private AuthInfo mAuthInfo;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;

	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	/** 用户信息接口 */
	private UsersAPI mUsersAPI;

	/** 顯示用戶暱稱 */
	private TextView mTvUserName;

	/** 顯示用戶頭像 */
	private ImageView mImgUser;

	public void btnGetCode(View v)
	{
		// TODO 请求获取短信验证码，在监听中返回
		String phoneNum = mEditUser.getText().toString();
		SMSSDK.getVerificationCode("86", phoneNum);
	}

	public void btnVerify(View v)
	{
		String phoneNum = mEditUser.getText().toString();
		String code = mEditCode.getText().toString();
		SMSSDK.submitVerificationCode("86", phoneNum, code);
	}

	public void btnSignUp(View v)
	{
		if (!isValid())
		{
			return;
		}

		// 将校验合法的用户名和密码提交到服务端
		// String url = "http://www.tuniu.com/signin?user=" + user + "&pwd=" +
		// pwd;
		// 使用Bmob实现用户注册
		final MyUser myUser = new MyUser(user, pwd, 18);
		myUser.signUp(this, new SaveListener()
		{

			@Override
			public void onSuccess()
			{
				// TODO Auto-generated method stub
				toast("注册成功:" + myUser.getUsername() + "-" + myUser.getObjectId() + "-" + myUser.getCreatedAt() + "-"
						+ myUser.getSessionToken() + ",是否验证：" + myUser.getEmailVerified());
			}

			@Override
			public void onFailure(int code, String msg)
			{
				// TODO Auto-generated method stub
				toast("注册失败:" + msg);
			}
		});
	}

	private boolean isValid()
	{
		user = mEditUser.getText().toString();
		pwd = mEditPwd.getText().toString();
		// 手机号判空
		if (TextUtils.isEmpty(user))
		{
			// 提示用户输入
			mEditUser.setError("请输入手机号码");
			return false;
		}
		// 格式合法性
		if (!RegexValidateUtil.checkMobileNumber(user))
		{
			// 提示用户输入
			mEditUser.setError("请输入正确的手机号码");
			return false;
		}
		// 密码判空
		if (TextUtils.isEmpty(pwd))
		{
			// 提示用户输入
			mEditPwd.setError("请输入密码");
			return false;
		}
		// 格式密码合法性
		if (!RegexValidateUtil.checkCharacter(pwd))
		{
			// 提示用户输入
			mEditPwd.setError("请输入6-16位字母或数字");
			return false;
		}
		return true;
	}

	public void btnSignOut(View v)
	{
		// 注销，删除sp中的用户信息
		BmobUser.logOut(this);
		mLayoutUnLogin.setVisibility(View.VISIBLE);
		mrelativeLayout.setVisibility(View.VISIBLE);
		mLayoutLogin.setVisibility(View.INVISIBLE);
		mSinaShare.setVisibility(View.VISIBLE);
	}

	public void btnSignIn(View v)
	{
		if (!isValid())
		{
			return;
		}
		// 使用Bmob实现用户登录
		final MyUser myUser = new MyUser(user, pwd);
		myUser.login(this, new SaveListener()
		{

			@Override
			public void onSuccess()
			{
				// TODO Auto-generated method stub
				toast(myUser.getUsername() + "登陆成功");
				// testGetCurrentUser();

				mLayoutUnLogin.setVisibility(View.GONE);
				mrelativeLayout.setVisibility(View.GONE);
				mLayoutLogin.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailure(int code, String msg)
			{
				// TODO Auto-generated method stub
				toast("登陆失败:" + code + "," + msg);
			}
		});
	}

	public void toast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		MainDrawerActivity.isNight(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initUI();
		initData();
		initMOBSMSSDK();
		// 获取 Token View，并让提示 View 的内容可滚动（小屏幕可能显示不全）
		mTokenText = (TextView) findViewById(R.id.token_text_view);
		// 獲取用戶暱稱 tv_userName
		mTvUserName = (TextView) findViewById(R.id.tv_userName);
		// 獲取用戶頭像（中圖）
		mImgUser = (ImageView) findViewById(R.id.img_user);
		// 创建微博实例
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
		// SSO 授权, ALL IN ONE 如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
		mSinaShare = (Button) findViewById(R.id.btn_sina_share);
		mSinaShare.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mSsoHandler.authorize(new AuthListener());
			}
		});
		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
		// 第一次启动本应用，AccessToken 不可用
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid())
		{
			updateTokenView(true);
		}
		// 获取用户信息接口
		mUsersAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);

		if (mAccessToken != null && mAccessToken.isSessionValid())
		{
			// String uid = mAccessToken.getUid();
			long uid = Long.parseLong(mAccessToken.getUid());
			mUsersAPI.show(uid, mListener);

		} else
		{
			Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_access_token_is_empty, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null)
		{
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener
	{

		@Override
		public void onComplete(Bundle values)
		{
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid())
			{
				// 显示 Token
				updateTokenView(false);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
				Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT)
						.show();
				// TODO
				mSinaShare.setVisibility(View.GONE);
				mLayoutUnLogin.setVisibility(View.GONE);
				mrelativeLayout.setVisibility(View.GONE);
				mLayoutLogin.setVisibility(View.VISIBLE);
			} else
			{
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code))
				{
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel()
		{
			Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e)
		{
			Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 显示当前 Token 信息。
	 * 
	 * @param hasExisted
	 *            配置文件中是否已存在 token 信息并且合法
	 */
	@SuppressLint("SimpleDateFormat")
	private void updateTokenView(boolean hasExisted)
	{
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
		mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

		String message = String.format(format, mAccessToken.getToken(), date);
		if (hasExisted)
		{
			message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
		}
		mTokenText.setText(message);
	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener()
	{
		@Override
		public void onComplete(String response)
		{
			if (!TextUtils.isEmpty(response))
			{
				LogUtil.i(TAG, response);
				// 调用 User#parse 将JSON串解析成User对象
				User user = User.parse(response);
				if (user != null)
				{
					// Toast.makeText(LoginActivity.this, "获取User信息成功，用户昵称：" +
					// user.screen_name, Toast.LENGTH_LONG).show();
					mTvUserName.setText(user.screen_name);
					// 50*50 user.profile_image_url
					// 180*180 user..avatar_large
					// mImgUser
					// ImageLoader.getInstance().displayImage(user.profile_image_url,
					// mImgUser);
					ImageUtils.displayImage(LoginActivity.this, user.avatar_large, mImgUser);
				} else
				{
					Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e)
		{
			LogUtil.e(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(LoginActivity.this, info.toString(), Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private static String APPKEY = "14354de5ffe50";

	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "f040c70952ff4ff2e706f2ce336366b4";
	private EventHandler eventHandler;
	private Button mSinaShare;

	private void initMOBSMSSDK()
	{
		// TODO
		// 1. 初始化Mob SDK
		SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
		final Handler handler = new Handler(this);
		eventHandler = new EventHandler()
		{
			public void afterEvent(int event, int result, Object data)
			{
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 2. 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	public boolean handleMessage(Message msg)
	{
		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;

		if (result == SMSSDK.RESULT_COMPLETE)
		{
			// 回调完成
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
			{
				// 提交验证码成功
				Toast.makeText(this, "提交验证码成功", Toast.LENGTH_SHORT).show();
			} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
			{
				// 获取验证码成功
				Toast.makeText(this, "获取验证码成功", Toast.LENGTH_SHORT).show();
			} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
			{
				// 返回支持发送验证码的国家列表
			}
		} else
		{
			((Throwable) data).printStackTrace();
			Toast.makeText(this, "验证失败", Toast.LENGTH_SHORT).show();
		}

		return false;
	}

	private void initData()
	{
		// 自动登录，读取sp中是否保存了用户信息
		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
		if (myUser != null)
		{
			Log.i("life", "本地用户信息:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername() + ",age = "
					+ myUser.getAge());
			mLayoutUnLogin.setVisibility(View.GONE);
			mrelativeLayout.setVisibility(View.GONE);
			mLayoutLogin.setVisibility(View.VISIBLE);
		} else
		{
			toast("本地用户为null,请登录。");
		}
	}

	private void initUI()
	{
		mEditUser = (EditText) findViewById(R.id.edit_user);
		mEditPwd = (EditText) findViewById(R.id.edit_pwd);
		mEditCode = (EditText) findViewById(R.id.edit_code);
		mLayoutLogin = findViewById(R.id.layout_login);
		mLayoutUnLogin = findViewById(R.id.layout_unlogin);
		mrelativeLayout = findViewById(R.id.relativeLayout1);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// 3. 注销回调接口
		SMSSDK.unregisterEventHandler(eventHandler);
	}

}
