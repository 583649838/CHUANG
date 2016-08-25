package com.bestplus.chuangshangjiuzhi.util;

import com.bestplus.chuangshangjiuzhi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


public class DialogUtil {

	public static Dialog showAlertDialog(Context context, int contentId,
			DialogInterface.OnClickListener okCallback,
			DialogInterface.OnClickListener cancelCallback) {
		String content = context.getString(contentId);
		return showAlertDialog(context, context.getString(R.string.app_name), content, null, okCallback,
				cancelCallback);
	}

	public static Dialog showAlertDialog(Context context, int contentId,
			int btnId, DialogInterface.OnClickListener okCallback,
			DialogInterface.OnClickListener cancelCallback) {
		String content = context.getString(contentId);
		String btnLabel = context.getString(btnId);
		return showAlertDialog(context, context.getString(R.string.app_name), content, btnLabel,
				okCallback, cancelCallback);
	}

	/**
	 * 显示提示对话框，可以设置用户选择后的动作
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param okCallback
	 *            确定按钮回调事件
	 * @param cancelCallback
	 *            取消按钮回调事件
	 */
	public static Dialog showAlertDialog(Context context, String title,
			String content, String btnLabel,
			DialogInterface.OnClickListener okCallback,
			DialogInterface.OnClickListener cancelCallback) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(AppUtil.getDrawableId(context, "hfb_application"));
		builder.setCancelable(false);
		builder.setMessage(content).setTitle(title);
		String btn = TextUtils.isEmpty(btnLabel) ? "确定" : btnLabel;
		builder.setPositiveButton(btn, okCallback);
		builder.setNegativeButton("取消", cancelCallback);
		Dialog dialog = builder.create();
		dialog.show();
		return dialog;
		// builder.create().show();
	}

	/**
	 * 提示对话框，二个按钮，只有确定有事件
	 * 
	 * @param context
	 * @param content
	 * @param onClickListener
	 */
	public static Dialog showAlertDialogOKCancel(Context context,
			String okButton, String content,
			DialogInterface.OnClickListener onClickListener) {

		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(content).setTitle(R.string.app_name);
		builder.setPositiveButton(okButton, onClickListener);
		builder.setNegativeButton("取消", null);
		Dialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static Dialog showAlertDialogOKCancel(Context context, int okId,
			int contentId, DialogInterface.OnClickListener onClickListener) {
		return showAlertDialogOKCancel(context, context.getString(okId),
				context.getString(contentId), onClickListener);
	}

	public static Dialog showAlertDialogOKCancel(Context context, int okId,
			int contentId, DialogInterface.OnClickListener onOKClickListener,
			DialogInterface.OnClickListener onCancelClickListener) {
		return showAlertDialogOKCancel(context, context.getString(okId),
				context.getString(contentId), onOKClickListener,
				onCancelClickListener);
	}

	public static Dialog showAlertDialogOKCancel(Context context,
			String okButton, String content,
			DialogInterface.OnClickListener onOKClickListener,
			DialogInterface.OnClickListener onCancelClickListener) {

		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(AppUtil.getDrawableId(context, "hfb_application"));
		builder.setMessage(content).setTitle(R.string.app_name);
		builder.setPositiveButton(okButton, onOKClickListener);
		builder.setNegativeButton("取消", onCancelClickListener);
		Dialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static Dialog showProgressDialog(Context context, String msg) {
		return showProgressDialog(context, msg, true, null);
	}

	public static Dialog showProgressDialog(Context context, int msgId) {
		return showProgressDialog(context, msgId, true);
	}

	public static Dialog showProgressDialog(Context context, int msgId,
			boolean canCancel) {
		return showProgressDialog(context, msgId, canCancel, null);
	}

	public static Dialog showProgressDialog(Context context, String msg,
			boolean canCancel) {
		return showProgressDialog(context, msg, canCancel, null);
	}

	/**
	 * 显示进度条对话框，可以设置按下物理返回键时执行的动作。
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            进度框中显示的内容
	 * @param onCancelListener
	 *            设置onCancel事件
	 * @return 进度条对话框实例
	 */
	public static Dialog showProgressDialog(Context context, String msg,
			boolean canCancel, DialogInterface.OnCancelListener onCancelListener) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}
		ProgressDialog progressDialog = new ProgressDialog(context);
		// progressDialog.setIcon(AppUtil
		// .getDrawableId(context, "hfb_application"));
		progressDialog.setTitle(R.string.app_name);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(canCancel);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnCancelListener(onCancelListener);
		progressDialog.show();
		return progressDialog;
	}

	public static Dialog showProgressDialog(Context context, int msgId,
			boolean canCancel, DialogInterface.OnCancelListener onCancelListener) {
		String msg = context.getString(msgId);
		return showProgressDialog(context, msg, canCancel, onCancelListener);
	}

	public static Dialog showProgressDialog(Context context, int msgId,
			DialogInterface.OnCancelListener onCancelListener) {
		String msg = context.getString(msgId);
		return showProgressDialog(context, msg, true, onCancelListener);
	}

	public static DialogInterface.OnClickListener defaultListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	// 内容，ok事件
	public static Dialog showAlertDialog(Context context, String content,
			DialogInterface.OnClickListener okCallback) {
		return showAlertDialog(context, context.getString(R.string.app_name), content,
				context.getString(R.string.confirm_label), okCallback, context.getString(R.string.cancel),
				null);
	}
	
	public static Dialog showAlertDialog(Context context, String content) {
		return showAlertDialog(context, content, defaultListener);
	}

	public static Dialog showAlertDialog(Context context, String content,
			String okLabel, DialogInterface.OnClickListener okCallback) {

		return showAlertDialog(context, context.getString(R.string.app_name), content,
				okLabel, okCallback, context.getString(R.string.cancel), null);
	}

	/**
	 * 显示提示对话框，可以设置用户作为选择后的动作
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            对话框标题
	 * @param content
	 *            对话框内容
	 * @param okCallback
	 *            确定按钮回调事件
	 * @param cancelCallback
	 *            取消按钮回调事件
	 */
	public static Dialog showAlertDialog(Context context, String title,
			String content, String btnLabelOk,
			DialogInterface.OnClickListener okCallback, String btnLabelRet,
			DialogInterface.OnClickListener cancelCallback) {
		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			return null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(R.drawable.application);
		builder.setCancelable(false);
		builder.setMessage(content).setTitle(title);
		String label = context.getString(R.string.confirm_label);
		String btnLabel = TextUtils.isEmpty(btnLabelOk) ? label
				: btnLabelOk;
		if (okCallback != null) {
			builder.setPositiveButton(btnLabel, okCallback);
		}
		label = context.getString(R.string.cancel);
		btnLabel = TextUtils.isEmpty(btnLabelRet) ? label
				: btnLabelRet;
		if (cancelCallback != null) {
			builder.setNegativeButton(btnLabel, cancelCallback);
		}
		Dialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static Dialog showAlertDialog(Context context, String content,
			String btnLabelOk, DialogInterface.OnClickListener okCallback,
			String btnLabelRet, DialogInterface.OnClickListener cancelCallback) {

		return showAlertDialog(context, context.getString(R.string.app_name),
				content, btnLabelOk, okCallback, btnLabelRet, cancelCallback);
	}

	// 标题，内容，ok事件
	public static Dialog showAlertDialog(Context context, int titleId,
			int contentId, DialogInterface.OnClickListener okCallback) {
		String title = context.getString(titleId);
		String content = context.getString(contentId);
		return showAlertDialog(context, title, content, null, okCallback, null,
				null);
	}

	// 内容，ok事件
	public static Dialog showAlertDialog(Context context, int contentId,
			DialogInterface.OnClickListener okCallback) {
		String title = context.getString(R.string.app_name);
		String content = context.getString(contentId);
		return showAlertDialog(context, title, content, null, okCallback, null,
				null);
	}

	// 标题，内容，ok按钮，ok事件，cancel按钮，cancel事件
	public static Dialog showAlertDialog(Context context, int titleId,
			int contentId, int btnLabelOkId,
			DialogInterface.OnClickListener okCallback, int btnLabelRetId,
			DialogInterface.OnClickListener cancelCallback) {
		String title = context.getString(titleId);
		String content = context.getString(contentId);
		String btnLabel = context.getString(btnLabelOkId);
		String btnLabelRet = context.getString(btnLabelRetId);
		return showAlertDialog(context, title, content, btnLabel, okCallback,
				btnLabelRet, cancelCallback);
	}

	// 内容，ok事件，cancel事件
	public static Dialog showAlertDialog(Context context, String content,
			DialogInterface.OnClickListener okCallback,
			DialogInterface.OnClickListener cancelCallback) {

		return showAlertDialog(context, context.getString(R.string.app_name), content,
				context.getString(R.string.confirm_label), okCallback, context.getString(R.string.cancel),
				cancelCallback);
	}
	
	public static void dismiss(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			// progress.getWindow().setWindowAnimations(R.anim.dialogout);
			dialog.dismiss();
		}
	}

}
