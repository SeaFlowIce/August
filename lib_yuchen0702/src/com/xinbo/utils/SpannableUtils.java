package com.xinbo.utils;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.yuchen.lib.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 在Android中，TextView是我们最常用的用来显示文本的控件。
 * 一般情况下，TextView中的文本都是一个样式。那么如何对于TextView中各个部分的文本来设置字体，大小，颜色，样式，以及超级链接等属性呢？
 * 下面我们通过SpannableString的具体实例操作来演示一下。 关键方法： public void setSpan (Object what,
 * int start, int end, int flags) 主要是start跟end
 * start是起始位置,无论中英文，都算一个,从0开始计算起。end是结束位置，所有处理的文字，包含开始位置，但不包含结束位置。
 */
public class SpannableUtils {
	public static void setSpannable(final Context context, TextView mTextView) {
		SpannableString mSpannableString = new SpannableString(
				" 百度超链接，电话， 邮件， 短信， 彩信， 地图， 背景色， 前景色， 正常、  粗体、 斜体、 粗斜体、下划线、 删除线、  上标、 下标 、 点击跳转、 设置字体1、 设置字体2、 设置字体大小1、设置字体大小2、 设置字体大小3、 设置字体大小4");

		// 设置超链接 （需要添加setMovementMethod方法附加响应）
		mSpannableString.setSpan(new URLSpan("http://www.baidu.com"), 1, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpannableString.setSpan(new URLSpan("tel:4155551212"), 7, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 电话
		mSpannableString.setSpan(new URLSpan("mailto:webmaster@google.com"), 11, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 邮件
		mSpannableString.setSpan(new URLSpan("sms:4155551212"), 15, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 短信
																											// 使用sms:或者smsto:
		mSpannableString.setSpan(new URLSpan("mms:4155551212"), 19, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 彩信
																											// 使用mms:或者mmsto:
		mSpannableString.setSpan(new URLSpan("geo:38.899533,-77.036476"), 23, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 地图

		// 设置高亮显示样式1,背景色
		mSpannableString.setSpan(new BackgroundColorSpan(Color.CYAN), 26, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置高亮显示样式，前景色
		mSpannableString.setSpan(new ForegroundColorSpan(Color.YELLOW), 32, 36, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		// 设置斜体
		mSpannableString.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 38, 40,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 正常
		mSpannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 42, 44,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 粗体
		mSpannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 46, 48,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 斜体
		mSpannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 50, 53,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 粗斜

		// 设置下划线
		mSpannableString.setSpan(new UnderlineSpan(), 54, 57, Spanned.SPAN_COMPOSING);
		// 设置删除线
		mSpannableString.setSpan(new StrikethroughSpan(), 59, 62, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置上下标
		mSpannableString.setSpan(new SubscriptSpan(), 64, 66, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 下标
		mSpannableString.setSpan(new SuperscriptSpan(), 68, 70, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 上标
		// 设置可点击
		mTextView.setMovementMethod(LinkMovementMethod.getInstance());

		View.OnClickListener mClickListener = new View.OnClickListener() {
			// 定义自己的动作
			@Override
			public void onClick(View v) {
				// TODO
				// 在这里就可以做跳转到activity或者弹出对话框的操作
				Toast.makeText(context, "Click Success", 1).show();

			}
		};
		mSpannableString.setSpan(new Clickable(mClickListener), 73, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置字体（依次包括字体名称，字体大小，字体样式，字体颜色，链接颜色）
		ColorStateList mColorStateList = null;
		ColorStateList mColorStateList2 = null;
		XmlPullParser mParser = context.getResources().getXml(R.color.color);
		try {
			mColorStateList.createFromXml(context.getResources(), mParser);
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		XmlPullParser mLinkParser = context.getResources().getXml(R.color.linkcolor);
		try {
			mColorStateList2.createFromXml(context.getResources(), mLinkParser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSpannableString.setSpan(new TextAppearanceSpan("monospace", android.graphics.Typeface.BOLD_ITALIC, 30,
				mColorStateList, mColorStateList2), 79, 84, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置字体(default,default-bold,monospace,serif,sans-serif)
		mSpannableString.setSpan(new TypefaceSpan("monospace"), 82, 87, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpannableString.setSpan(new TypefaceSpan("serif"), 89, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置字体大小（绝对值,单位：像素）
		mSpannableString.setSpan(new AbsoluteSizeSpan(20), 94, 100, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpannableString.setSpan(new AbsoluteSizeSpan(20, true), 102, 110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
																												// dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。

		// 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
		mSpannableString.setSpan(new RelativeSizeSpan(0.5f), 111, 119, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mSpannableString.setSpan(new RelativeSizeSpan(2.0f), 120, 127, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 设置项目符号
		mSpannableString.setSpan(new BulletSpan(android.text.style.BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN), 0, 127,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		System.out.println(mSpannableString.length());
		// SpannableString对象设置给TextView
		mTextView.setText(mSpannableString);
	}

	static class Clickable extends ClickableSpan implements OnClickListener {
		private final View.OnClickListener mClickListener;

		public Clickable(View.OnClickListener l) {
			mClickListener = l;
		}

		@Override
		public void onClick(View v) {
			mClickListener.onClick(v);

		}

	}
}
