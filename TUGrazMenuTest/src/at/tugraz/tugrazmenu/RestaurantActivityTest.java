package at.tugraz.tugrazmenu;

import java.io.IOException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

public class RestaurantActivityTest extends ActivityInstrumentationTestCase2<RestaurantActivity> {
    RestaurantActivity activity;

    public RestaurantActivityTest() {
        super(RestaurantActivity.class);
    }

    protected void setUp() throws Exception {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("name", "Galileo");
        bundle.putString("adress", "Lessingstraße 10");
        bundle.putString("telNr", "0316 12345");
        intent.putExtras(bundle);
        setActivityIntent(intent);
        activity = getActivity();
    }

    public void testOnCreate() {
        TextView name = (TextView) activity.findViewById(R.id.textrestaurant);
        TextView adress = (TextView) activity.findViewById(R.id.textadress);
        TextView telefon = (TextView) activity.findViewById(R.id.texttelefon);

        assertEquals("Galileo", name.getText());
        assertEquals("Lessingstraße 10", adress.getText());
        assertEquals("0316 12345", telefon.getText());
    }
	  
	    @UiThreadTest
	    public void testCameraPosition(){
		  GoogleMap map = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map)).getMap();
		  double latitude = map.getCameraPosition().target.latitude;
		  double longitude = map.getCameraPosition().target.longitude;
		  float zoom = map.getCameraPosition().zoom;
		  
		  assertEquals(15.4520016, longitude, 0.00001); 
		  assertEquals(47.0610254, latitude, 0.00001); 
		  assertEquals(14.0f, zoom); 
	  }
	  
	    @UiThreadTest
	    public void testMyPosition(){
		 GoogleMap map = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map)).getMap();
		 assertEquals(true, map.isMyLocationEnabled()); 
	  }
	    
	  public void testGetRestaurationLocation() throws IOException{
		  LatLng location = activity.getRestaurantLocation("Lessingstraße 25");
		  assertEquals(15.4520016, location.longitude, 0.02);
		  assertEquals(47.0610254, location.latitude, 0.02);
	  }
}
