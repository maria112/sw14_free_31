package at.tugraz.tugrazmenu;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);

		String name = getIntent().getExtras().getString("name");
		String adress = getIntent().getExtras().getString("adress");
		String telNr = getIntent().getExtras().getString("telNr");
		
		TextView nameField = (TextView) findViewById(R.id.textrestaurant);
		TextView adressField = (TextView) findViewById(R.id.textadress);
		TextView telField = (TextView) findViewById(R.id.texttelefon);

		nameField.setText(name);
		adressField.setText(adress);
		telField.setText(telNr);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		final View mapView = getFragmentManager().findFragmentById(R.id.map).getView();
		mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
			@Override
            public void onGlobalLayout() {        		
        		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        		map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(47.0610254, 15.4520016)));
        		map.moveCamera(CameraUpdateFactory.zoomTo(14));
        		try {
					setRestaurantMarker();
				} catch (IOException e) {
					e.printStackTrace();
				} 
        		
                mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
	}
	

	public void setRestaurantMarker() throws IOException{
		Context context = getApplicationContext(); 
		Geocoder geo = new Geocoder(context);
		double latitude = 0;
		double longitue = 0;
		String adress = getIntent().getExtras().getString("adress");
		List<Address>addresses = geo.getFromLocationName(adress, 5); 	
		 if (addresses.size() > 0) {
			 latitude = addresses.get(0).getLatitude();
			 longitue = addresses.get(0).getLongitude();
		 }
		 
		 String restaurant = getIntent().getExtras().getString("name");
		 GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		 map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitue)).title(restaurant));	
		 
		map.setMyLocationEnabled(true);
	}
}
	
	

