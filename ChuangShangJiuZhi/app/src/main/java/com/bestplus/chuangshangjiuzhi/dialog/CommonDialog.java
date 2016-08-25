package com.bestplus.chuangshangjiuzhi.dialog;

import com.bestplus.chuangshangjiuzhi.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 通用dialog
 * 
 * @author Admin
 * 
 */
public class CommonDialog extends Dialog implements OnClickListener {
	private TextView titleTextView;
	private Button button, cancelButton;
	private ImageButton exitButton;
	private LinearLayout dismissLayout, mainLayout;
	Context context;
	private String title = "";
	private String text = "", text2 = "";
	
    private CustomDialogListener listener = null;  
    
    public interface CustomDialogListener{  
        public void onClick(View view);  
    }

	public CommonDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CommonDialog(Context context, int theme, String title, String text) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.title = title;
		this.text = text;
	}

	public CommonDialog(Context context, int theme, String title, String text,
			String text2) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.title = title;
		this.text = text;
		this.text2 = text2;
	}
	
	public void SetCustomListener(CustomDialogListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_common);
		titleTextView = (TextView) findViewById(R.id.tv_dialogTitle);
		button = (Button) findViewById(R.id.btn_dialogButton);
		exitButton = (ImageButton) findViewById(R.id.btn_exit);
		cancelButton = (Button) findViewById(R.id.btn_cancelButton);
		dismissLayout = (LinearLayout) findViewById(R.id.layout_dismiss);
		mainLayout = (LinearLayout) findViewById(R.id.layout_main);

		exitButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		dismissLayout.setOnClickListener(this);
		mainLayout.setOnClickListener(this);
		button.setOnClickListener(this);

		titleTextView.setText(title);
		if (text.equals("")) {
			button.setVisibility(View.GONE);
		} else {
			button.setVisibility(View.VISIBLE);
			button.setText(text);
		}

		if (text2.equals("")) {
			cancelButton.setVisibility(View.GONE);
		} else {
			cancelButton.setVisibility(View.VISIBLE);
			cancelButton.setText(text2);
		}
	}

	@Override
	public void onClick(View arg0) {
		if(null == this.listener){
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.layout_dismiss:
				dismiss();
				break;
			case R.id.btn_exit:
				dismiss();
				break;
			case R.id.layout_main:

				break;
			case R.id.btn_dialogButton:
				if (text.equals(context.getResources().getString(
						R.string.confirm_label))) {
					dismiss();
				}
				break;
			case R.id.btn_cancelButton:
				dismiss();
				break;
			default:
				break;
			}
		}else{
			this.listener.onClick(arg0);
		}
	}
}
