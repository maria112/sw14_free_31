package at.tugraz.tugrazmenu;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity> {
    private Solo solo;
    public MenuActivityTest(){
        super(MenuActivity.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
