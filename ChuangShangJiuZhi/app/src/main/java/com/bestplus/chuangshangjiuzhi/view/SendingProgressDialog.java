package com.bestplus.chuangshangjiuzhi.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author rx
 * @version 1.0 类说明 弹出指示对话框
 */
public class SendingProgressDialog {
	public ProgressDialog dialog;
	private final String message = "正在发送中,请稍候...";

	public SendingProgressDialog(Context paramContext) {
		this(paramContext, null);
	}

	public SendingProgressDialog(Context paramContext, String paramString) {

		ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
		this.dialog = localProgressDialog;
		this.dialog.setCancelable(true);
		this.dialog.setCanceledOnTouchOutside(false);
		
		if (paramString == null) {
			this.dialog.setMessage(message);
		} else {
			this.dialog.setMessage(paramString);
		}
	}

	public void start() {
		try {
			this.dialog.show();
		} catch (Exception e) {
		}

	}

	public void stop() {
		try {
			if (this.dialog != null) {
				this.dialog.dismiss();
				this.dialog.cancel();
			}
		} catch (Exception e) {
		}
	}
}
