package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class RestaurantActivityTest extends ActivityInstrumentationTestCase2<RestaurantActivity> {
    Activity activity;

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
}
