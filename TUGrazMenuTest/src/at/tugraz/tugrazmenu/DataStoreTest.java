package at.tugraz.tugrazmenu;

import junit.framework.TestCase;

public class DataStoreTest extends TestCase {

	class NotificationHandler implements DataStore.DataStoreNotification {

		boolean haveData = false;

		@Override
		public void onDataLoaded() {
			haveData = true;
		}

		public boolean getHaveData() {
			return haveData;
		}
	}

	public void testLoadData() {
		NotificationHandler handler = new NotificationHandler();
		DataStore.loadData(handler);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(true, handler.getHaveData());

		assertEquals(false, DataStore.getRestaurants().isEmpty());
		assertEquals(false, DataStore.getRestaurantMenus().isEmpty());
	}
}
