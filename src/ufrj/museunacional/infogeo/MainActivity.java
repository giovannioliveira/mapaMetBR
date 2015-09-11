package ufrj.museunacional.infogeo;

import java.util.ArrayList;

import ufrj.museunacional.infogeo.util.FileReader;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.museunacional.infogeo.dialog.CDialog;
import com.museunacional.infogeo.model.Analise;
import com.museunacional.infogeo.model.Meteorito;

public class MainActivity extends FragmentActivity implements
		OnMapReadyCallback {

	static final LatLng BRASIL_CENTER = new LatLng(-14.2400732, -53.1805018);
	static final float MAX_ZOOM = 16;
	static final float MIN_ZOOM = 5;
	LatLngBounds BOUNDS;
	static final int RESET_TIME = 180;
	final Handler mHandler = new Handler();

	ArrayList<Meteorito> alMeteorites;
	ArrayList<Analise> alAnalysis;
	ArrayList<MarkerOptions> alMarkers = new ArrayList<MarkerOptions>();

	CDialog mDialogBuilder;
	Dialog mDialog;

	SupportMapFragment mSMFragment;
	GoogleMap mMap;
	OverscrollHandler mOverscrollHandler;

	FragmentActivity mActivity;
	EditText etSerch;
	FrameLayout flInterceptor;
	FrameLayout flInterceptor2;

	final Runnable mReset = new Runnable() {

		@Override
		public void run() {

			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					resetMap();
				}
			});
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActivity = this;

		mDialogBuilder = new CDialog(mActivity);
		regiterIds();
		mSMFragment.getMapAsync(this);

	}

	void regiterIds() {
		mSMFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		etSerch = (EditText) findViewById(R.id.etSearch);
		flInterceptor = (FrameLayout) findViewById(R.id.touch_interceptor);
		flInterceptor2 = (FrameLayout) findViewById(R.id.touch_interceptor2);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {

		mMap = googleMap;

		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setRotateGesturesEnabled(false);
		mMap.setMyLocationEnabled(false);

		setCameraPosition();
		setCameraListener();
		getData();
		setMarkers();
		setListeners();

	}

	void getData() {

		String s = FileReader.getStringFromInputStream(getResources()
				.openRawResource(R.raw.meteoritos));
		String s2 = FileReader.getStringFromInputStream(getResources()
				.openRawResource(R.raw.analise));

		Gson g = new Gson();

		alMeteorites = g.fromJson(s, new TypeToken<ArrayList<Meteorito>>() {
		}.getType());

		alAnalysis = g.fromJson(s2, new TypeToken<ArrayList<Analise>>() {
		}.getType());
	}

	void setMarkers() {

		for (Meteorito m : alMeteorites) {

			MarkerOptions mo = new MarkerOptions()
					.position(new LatLng(m.getLatitude(), m.getLongitude()))
					.title(m.getNome() + "-" + m.getUf().toUpperCase())
					.snippet(
							(m.getData() != null ? m.getFato() + ": "
									+ m.getData() + ". " : "")
									+ "Clique aqui para saber mais sobre este meteorito!");

			alMarkers.add(mo);
			mMap.addMarker(mo);

		}

	}

	void setListeners() {

		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				Meteorito met = null;
				for (Meteorito m : alMeteorites) {
					if ((m.getNome() + "-" + m.getUf().toUpperCase())
							.equals(marker.getTitle())) {
						met = m;
						break;
					}
				}
				String analisys = new String();
				for (Analise a : alAnalysis) {
					if (a.getMeteorito_id().equals(met.getId())) {
						analisys += a.getAnalysis() + " ";
					}
				}
				mDialogBuilder.buildDialog(met, analisys);
				mDialog = mDialogBuilder.getDialog();
				mDialog.show();
			}
		});

		etSerch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				resetMarkers();
				setCameraPosition();

			}
		});

		flInterceptor.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mHandler.removeCallbacksAndMessages(null);
				mHandler.postDelayed(mReset, RESET_TIME * 1000);
				return false;
			}
		});
		
		flInterceptor2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				closeSoftInput();
				return false;
			}
		});
		
		
	}

	void resetMarkers() {
		Log.e("reset", "now");
		mMap.clear();
		String search = etSerch.getText().toString().trim();
		if (search.isEmpty()) {
			for (MarkerOptions mo : alMarkers) {
				mMap.addMarker(mo);
			}
		} else {
			for (int i = 0; i < alMeteorites.size(); i++) {
				if(alMeteorites.get(i).contains(search)){
					mMap.addMarker(alMarkers.get(i));
				}
			}
		}
	}
	
	void closeSoftInput(){
	    View view = this.getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}

	void resetMap(){
		closeSoftInput();
		if(mDialog!=null && mDialog.isShowing()){
			mDialog.dismiss();
		}
		etSerch.setText("");
		setCameraPosition();
		setMarkers();
	}

	// camera correction related functions

	void setCameraPosition() {
		CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(BRASIL_CENTER,
				MIN_ZOOM);
		mMap.animateCamera(upd);

	}

	void setCameraListener() {

		mOverscrollHandler = new OverscrollHandler();
		mOverscrollHandler.sendEmptyMessageDelayed(0, 100);

		mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition position) {

				if (BOUNDS == null) {
					VisibleRegion vr = mMap.getProjection().getVisibleRegion();
					BOUNDS = new LatLngBounds(vr.latLngBounds.southwest,
							vr.latLngBounds.northeast);
				}

			}
		});

	}

	LatLng getLatLngCorrection(LatLngBounds cameraBounds) {
		double latitude = 0, longitude = 0;
		if (cameraBounds.southwest.latitude < BOUNDS.southwest.latitude) {
			latitude = BOUNDS.southwest.latitude
					- cameraBounds.southwest.latitude;
		}
		if (cameraBounds.southwest.longitude < BOUNDS.southwest.longitude) {
			longitude = BOUNDS.southwest.longitude
					- cameraBounds.southwest.longitude;
		}
		if (cameraBounds.northeast.latitude > BOUNDS.northeast.latitude) {
			latitude = BOUNDS.northeast.latitude
					- cameraBounds.northeast.latitude;
		}
		if (cameraBounds.northeast.longitude > BOUNDS.northeast.longitude) {
			longitude = BOUNDS.northeast.longitude
					- cameraBounds.northeast.longitude;
		}
		return new LatLng(latitude, longitude);
	}

	@SuppressLint("HandlerLeak")
	private class OverscrollHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (mMap != null && BOUNDS != null) {
				CameraPosition position = mMap.getCameraPosition();
				VisibleRegion region = mMap.getProjection().getVisibleRegion();
				float zoom = 0;
				if (position.zoom < MIN_ZOOM)
					zoom = MIN_ZOOM;
				if (position.zoom > MAX_ZOOM)
					zoom = MAX_ZOOM;
				LatLng correction = getLatLngCorrection(region.latLngBounds);
				if (zoom != 0 || correction.latitude != 0
						|| correction.longitude != 0) {
					zoom = (zoom == 0) ? position.zoom : zoom;
					double lat = position.target.latitude + correction.latitude;
					double lon = position.target.longitude
							+ correction.longitude;
					CameraPosition newPosition = new CameraPosition(new LatLng(
							lat, lon), zoom, position.tilt, position.bearing);
					CameraUpdate update = CameraUpdateFactory
							.newCameraPosition(newPosition);
					mMap.moveCamera(update);
				}
			}
			/* Recursively call handler every 100ms */
			sendEmptyMessageDelayed(0, 100);
		}
	}

}
