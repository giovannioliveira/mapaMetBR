package com.museunacional.infogeo.dialog;

import ufrj.museunacional.infogeo.R;
import ufrj.museunacional.infogeo.util.GeneralUtil;
import android.app.Activity;
import android.app.Dialog;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.museunacional.infogeo.model.Meteorito;

public class CDialog {

	Dialog mDialog;

	Activity mContext;
	Meteorito mMeteorito;
	String mAnal;

	TextView tvTitle;
	TextView tvDistrict;
	LinearLayout llFact;
	TextView tvFactLabel;
	TextView tvFactData;
	LinearLayout llClass;
	TextView tvClass;
	LinearLayout llMass;
	TextView tvMass;
	LinearLayout llMassMus;
	TextView tvMassMus;
	LinearLayout llAnal;
	TextView tvAnal;
	LinearLayout llInfo;
	TextView tvInfo;
	ImageView iv1;
	ImageView iv2;
	LinearLayout llImg;
	ImageButton btClose;

	public CDialog(Activity mContext) {

		this.mContext = mContext;

	}

	public Dialog buildDialog(Meteorito mMeteorito, String mAnal) {

		this.mMeteorito = mMeteorito;
		this.mAnal = mAnal;

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}

		mDialog = new Dialog(mContext);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.dialog_card);
		mDialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mDialog.getWindow().setGravity(Gravity.CENTER);

		registerIds();
		fillFields();
		setListeners();

		mDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(mDialog.getWindow().getAttributes());

		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		mDialog.getWindow().setAttributes(lp);

		return mDialog;

	}

	void registerIds() {
		tvTitle = (TextView) mDialog.findViewById(R.id.tvName);
		tvDistrict = (TextView) mDialog.findViewById(R.id.tvDistrict);
		llFact = (LinearLayout) mDialog.findViewById(R.id.llFact);
		tvFactLabel = (TextView) mDialog.findViewById(R.id.tvFactLabel);
		tvFactData = (TextView) mDialog.findViewById(R.id.tvFactData);
		llClass = (LinearLayout) mDialog.findViewById(R.id.llClass);
		tvClass = (TextView) mDialog.findViewById(R.id.tvClass);
		llMass = (LinearLayout) mDialog.findViewById(R.id.llMass);
		tvMass = (TextView) mDialog.findViewById(R.id.tvMass);
		llMassMus = (LinearLayout) mDialog.findViewById(R.id.llMassMuseum);
		tvMassMus = (TextView) mDialog.findViewById(R.id.tvMassMuseum);
		llAnal = (LinearLayout) mDialog.findViewById(R.id.llAnal);
		tvAnal = (TextView) mDialog.findViewById(R.id.tvAnal);
		llInfo = (LinearLayout) mDialog.findViewById(R.id.llInfo);
		tvInfo = (TextView) mDialog.findViewById(R.id.tvInfo);
		iv1 = (ImageView) mDialog.findViewById(R.id.iv1);
		iv2 = (ImageView) mDialog.findViewById(R.id.iv2);
		llImg = (LinearLayout) mDialog.findViewById(R.id.llImg);
		btClose = (ImageButton) mDialog.findViewById(R.id.btCloseDialog);
	}

	void fillFields() {
		tvTitle.setText(mMeteorito.getNome());
		tvDistrict.setText(mMeteorito.getUf().toUpperCase());
		if (mMeteorito.getFato().equals("q")) {
			tvFactLabel.setText(mContext.getString(R.string.fall));
		} else {
			tvFactLabel.setText(mContext.getString(R.string.found));
		}
		tvFactData.setText((mMeteorito.getData() == null || mMeteorito
				.getData().isEmpty()) ? "Desconhecida." : mMeteorito.getData());

		String type = mMeteorito.getTipo() != null ? mMeteorito.getTipo()
				+ ". " : "";
		String cls = mMeteorito.getClasse() != null ? mMeteorito.getClasse()
				+ ". " : "";
		String group = mMeteorito.getGrupo() != null ? mMeteorito.getGrupo()
				+ ". " : "";

		String cat = type + cls + group;

		if (cat.isEmpty()) {
			llClass.setVisibility(View.GONE);
		} else {
			tvClass.setText(cat);
		}

		tvMass.setText(mMeteorito.getInfoMass());

		if (mMeteorito.getMassa_museu() == null) {
			llMassMus.setVisibility(View.GONE);
		}else{
			tvMassMus.setText(mMeteorito.getMassa_museu());
		}
		
		if(mAnal == null || mAnal.isEmpty()){
			llAnal.setVisibility(View.GONE);
		}else{
			tvAnal.setText(Html.fromHtml(mAnal));
		}
		
		if(mMeteorito.getInformacoes() == null){
			llInfo.setVisibility(View.GONE);
		}else{
			tvInfo.setText(mMeteorito.getInformacoes());
		}
		
		int[] imgRes = GeneralUtil.getImageResources(mMeteorito.getId());
		
		if(imgRes[0] == 0){
			llImg.setVisibility(View.GONE);
		}else if(imgRes[1] == 0){
			iv1.setImageResource(imgRes[0]);
			iv2.setVisibility(View.GONE);
		}else{
			iv1.setImageResource(imgRes[0]);
			iv2.setImageResource(imgRes[1]);
		}
		
	}
	
	void setListeners(){
		
		tvInfo.setMovementMethod(new ScrollingMovementMethod());
		
		btClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				
			}
		});
	}
	
	public Dialog getDialog(){
		return mDialog;
	}

}
