package ufrj.museunacional.infogeo;

import java.util.ArrayList;

import ufrj.museunacional.infogeo.util.FileReader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.museunacional.infogeo.dialog.CardDialog;
import com.museunacional.infogeo.model.Analise;
import com.museunacional.infogeo.model.Meteorito;

public class MainFragment extends Fragment {
	
	ArrayList<Meteorito> alMeteorites;
	ArrayList<Analise> alAnalysis;

	Dialog dialog;

	private final LatLng BRASIL_CENTER = new LatLng(-14.2400732,
			-53.1805018);
	private final float MAX_ZOOM = 16;
	private final float MIN_ZOOM = 5;
	private LatLngBounds BOUNDS;
	private final int RESET_TIME = 120;
	private final Handler handler = new Handler();

	Activity mActivity;
	GoogleMap mMap;
	OverscrollHandler mOverscrollHandler;

	private final Runnable run = new Runnable() {

		@Override
		public void run() {
			mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					setCameraPosition();
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}

				}
			});
		}
	};
	

	public MainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.getUiSettings().setRotateGesturesEnabled(false);
		mMap.setMyLocationEnabled(false);

		setCameraPosition();
		setCameraListener();
		getData();
		setMarkers();
		setListeners();

		return rootView;
	}
	
	private void getData(){
		
		String s = FileReader.getStringFromInputStream(getResources().openRawResource(
				R.raw.meteoritos));
		String s2 = FileReader.getStringFromInputStream(getResources().openRawResource(
				R.raw.analise));
		
		Gson g = new Gson();

		alMeteorites = g.fromJson(s,
				new TypeToken<ArrayList<Meteorito>>() {
				}.getType());
		
		alAnalysis = g.fromJson(s2,
				new TypeToken<ArrayList<Analise>>() {
				}.getType());
	}
	

	public void setMarkers() {

		for (Meteorito m : alMeteorites) {
			mMap.addMarker(new MarkerOptions().position(
					new LatLng(m.getLatitude(), 
							m.getLongitude())
					).title(
					m.getNome()+"-"+m.getUf().toUpperCase())
					.snippet((m.getData() != null ? m.getFato() + ": " +m.getData()+". " : "") + "Clique aqui para saber mais sobre este meteorito!"));
		}
		
	}
	
	private void setListeners(){
		
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				if (dialog != null && dialog.isShowing()) {
					return;
				}
				Meteorito met = null;
				for(Meteorito m : alMeteorites){
					if((m.getNome()+"-"+m.getUf().toUpperCase()).equals(marker.getTitle())){
						met = m;
						break;
					}
				}
				String analisys = new String();
				for(Analise a: alAnalysis){
					if(a.getMeteorito_id().equals(met.getId())){
						analisys += a.getAnalysis()+" ";
					}
				}
				dialog = CardDialog.generateDialog(met, analisys, mActivity);
				dialog.show();
				handler.removeCallbacks(run);
				handler.postDelayed(run, RESET_TIME * 1000);
			}
		});
	}
	
	//camera correction related functions
	
	public void setCameraPosition() {
		CameraUpdate upd = CameraUpdateFactory.newLatLngZoom(BRASIL_CENTER,
				MIN_ZOOM);
		mMap.animateCamera(upd);

	}

	private void setCameraListener() {

		mOverscrollHandler = new OverscrollHandler();
		mOverscrollHandler.sendEmptyMessageDelayed(0, 100);

		mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition position) {

				if (BOUNDS == null) {
					VisibleRegion vr = mMap.getProjection()
							.getVisibleRegion();
					BOUNDS = new LatLngBounds(vr.latLngBounds.southwest,
							vr.latLngBounds.northeast);
				} else {

					handler.removeCallbacks(run);
					handler.postDelayed(run, RESET_TIME * 1000);
				}

			}
		});

	}

	private LatLng getLatLngCorrection(LatLngBounds cameraBounds) {
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
				VisibleRegion region = mMap.getProjection()
						.getVisibleRegion();
				float zoom = 0;
				if (position.zoom < MIN_ZOOM)
					zoom = MIN_ZOOM;
				if (position.zoom > MAX_ZOOM)
					zoom = MAX_ZOOM;
				LatLng correction = getLatLngCorrection(region.latLngBounds);
				if (zoom != 0 || correction.latitude != 0
						|| correction.longitude != 0) {
					zoom = (zoom == 0) ? position.zoom : zoom;
					double lat = position.target.latitude
							+ correction.latitude;
					double lon = position.target.longitude
							+ correction.longitude;
					CameraPosition newPosition = new CameraPosition(
							new LatLng(lat, lon), zoom, position.tilt,
							position.bearing);
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