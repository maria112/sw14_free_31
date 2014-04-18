package at.tugraz.tugrazmenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ContainerToMenuItemConverter {
    public void convertAndAddToList(MenuItemContainer inputContainer, List<MenuItem> menuItemList, List<Restaurant> restaurantList) throws ParseException {
        Restaurant currentRestaurant;
        String description = inputContainer.description;
        String title = inputContainer.title;
        String dateString = title.substring(title.indexOf("am ") + 3, title.lastIndexOf(".") + 5);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = dateFormat.parse(dateString);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String[] splitByRestaurant = description.split("<b>");

        for (int i = 1; i < splitByRestaurant.length; i++) {
            String name = splitByRestaurant[i].split("</b>")[0];
            String address = (splitByRestaurant[i].substring(splitByRestaurant[i].indexOf("</b> (") + 6, splitByRestaurant[i].indexOf("Tel")-2));
            String telephoneNumber = (splitByRestaurant[i].substring(splitByRestaurant[i].indexOf("Tel. ") + 5, splitByRestaurant[i].indexOf(")<br>")));

            Restaurant restaurant = new Restaurant(name,address,telephoneNumber);
            int indexOfRestaurant = existsInRestaurantListAtIndex(restaurantList, restaurant);
            if (indexOfRestaurant != -1){
                currentRestaurant = restaurantList.get(indexOfRestaurant);
            }else{
                currentRestaurant = restaurant;
                restaurantList.add(currentRestaurant);
            }
            String[] menuItems = splitByRestaurant[i].split("• ");
            for (int j = 1; j < menuItems.length; j++) {
                String itemContent = menuItems[j].replace("<br>  ","");
                menuItemList.add(new MenuItem(currentRestaurant, itemContent, calendar));
            }
        }
    }

    public int existsInRestaurantListAtIndex(List<Restaurant> restaurantList, Restaurant restaurant) {
        int index = -1;
        for (int i = 0; i < restaurantList.size(); i++) {
            if (restaurantList.get(i).name.equals(restaurant.name)) {
                index = i;
            }
        }
        return index;
    }
}
