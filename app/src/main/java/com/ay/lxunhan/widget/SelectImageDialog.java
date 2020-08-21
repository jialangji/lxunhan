package com.ay.lxunhan.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ay.lxunhan.R;

import java.util.Objects;

public class SelectImageDialog extends Dialog implements
		View.OnClickListener {
	private ItemClickListener itemClickListener;
	private String topButton;
	private String bottomButton;

	public SelectImageDialog(Context context, int theme,String topButton,String bottomButton) {
		super(context, theme);
		this.topButton=topButton;
		this.bottomButton=bottomButton;
	}

	public void setItemClickListener(ItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_choose_img);
		Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		View other_view = findViewById(R.id.other_view);
		Button dialog_cancel = findViewById(R.id.dialog_cancel);
		Button choose_by_local = findViewById(R.id.choose_by_local);
		Button choose_by_camera = findViewById(R.id.choose_by_camera);
		choose_by_camera.setText(topButton);
		choose_by_local.setText(bottomButton);
		other_view.setOnClickListener(this);
		dialog_cancel.setOnClickListener(this);
		choose_by_local.setOnClickListener(this);
		choose_by_camera.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.other_view:
			if (itemClickListener != null) {
				itemClickListener.cancel();
				dismiss();
			}
			break;
		case R.id.dialog_cancel:
			if (itemClickListener != null) {
				itemClickListener.cancel();
				dismiss();
			}
			break;
		case R.id.choose_by_camera:
			if (itemClickListener != null) {
				itemClickListener.takephoto();
				dismiss();
			}
			break;
		case R.id.choose_by_local:
			if (itemClickListener != null) {
				itemClickListener.album();
				dismiss();
			}
			break;

		default:
			break;
		}
	}

	public interface ItemClickListener {
		void album();

		void takephoto();

		void cancel();
	}
}