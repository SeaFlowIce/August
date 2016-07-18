package com.galgame.util;

import com.galgame.august.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UpdateDialog extends DialogFragment
{

	private TextView content;
	private String strcontent;

	public UpdateDialog(String content)
	{
		strcontent = content;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// 设置dialog没有标题栏
		getDialog().requestWindowFeature(STYLE_NO_TITLE);
		View view = inflater.inflate(R.layout.layout_dialog, null);
		content = (TextView) view.findViewById(R.id.tv_content);
		content.setText(strcontent);
		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (listener != null)
				{
					listener.onConfirm();
				}
				getDialog().dismiss();
			}
		});
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancle);
		btn_cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getDialog().dismiss();
			}
		});

		return view;
	}

	public interface OnConfirmListener
	{
		void onConfirm();
	}

	private OnConfirmListener listener;

	public void setOnConfirmListener(OnConfirmListener l)
	{
		listener = l;
	}

	// /**
	// * 设置dialog文本内容
	// */
	// public void setContent(String content){
	// this.content.setText(content);
	// }

}
