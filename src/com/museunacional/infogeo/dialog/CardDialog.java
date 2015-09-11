package com.museunacional.infogeo.dialog;

import ufrj.museunacional.infogeo.R;
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
import android.widget.TextView;

import com.museunacional.infogeo.model.Meteorito;

public class CardDialog {

	public static Dialog generateDialog(Meteorito met, String analysis,
			Activity a) {

		final Dialog dialog = new Dialog(a);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_simple);
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		dialog.getWindow().setGravity(Gravity.CENTER);

		setCloseButton(dialog);
		setFields(met, analysis, dialog);

		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());

		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.getWindow().setAttributes(lp);

		return dialog;
	}

	private static void setFields(Meteorito met, String analysis, Dialog dialog) {

		// ImageView iv = (ImageView) dialog.findViewById(R.id.ivDialog);
		TextView name = (TextView) dialog.findViewById(R.id.tvNameDialog);
		TextView classif = (TextView) dialog.findViewById(R.id.tvClassDialog);
		TextView pos = (TextView) dialog.findViewById(R.id.tvPositionDialog);
		TextView fact = (TextView) dialog.findViewById(R.id.tvFactDialog);
		TextView mass = (TextView) dialog.findViewById(R.id.tvMassDialog);
		TextView anal = (TextView) dialog.findViewById(R.id.tvAnalysisDialog);
		TextView info = (TextView) dialog.findViewById(R.id.tvInfoDialog);

		name.setText(met.getNome().toUpperCase() + "-" + met.getUf());

		String type = met.getTipo() != null ? " Tipo: " + met.getTipo() + "."
				: "";
		String cls = met.getClasse() != null ? " Classe: " + met.getClasse()
				+ "." : "";
		String group = met.getGrupo() != null ? " Grupo: " + met.getGrupo()
				+ "." : "";
		String cat = type + cls + group;

		if (cat.isEmpty()) {
			classif.setVisibility(View.GONE);
		} else {
			classif.setText(classif.getText() + cat);
		}

		pos.setText(pos.getText()
				+ " "
				+ String.format("(%.4f;%.4f)", met.getLatitude(),
						met.getLongitude()));

		fact.setText(met.getFato()
				+ (met.getData() != null ? ": " + met.getData() : ""));

		mass.setText(mass.getText() + " " + met.getInfoMass());

		if (analysis == null || analysis.isEmpty()) {
			anal.setVisibility(View.GONE);
		} else {
			anal.setText(Html.fromHtml(anal.getText() + " " + analysis));
		}

		info.setText(dialog.getContext().getString(R.string.lorem));
		info.setMovementMethod(new ScrollingMovementMethod());

	}

	private static void setCloseButton(final Dialog dialog) {
		ImageButton btClose = (ImageButton) dialog
				.findViewById(R.id.btCloseDialog);

		btClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

}
