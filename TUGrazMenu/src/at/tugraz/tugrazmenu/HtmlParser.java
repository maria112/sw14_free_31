package at.tugraz.tugrazmenu;

import org.apache.commons.lang3.StringEscapeUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class HtmlParser extends XmlHelper {

    public String getDateString(String formattedHtmlContent) throws Exception {
        String[] date = formattedHtmlContent.split("<h1>");
        if (date.length > 1) {
            return date[1].split("ab")[1].split("</h1>")[0].trim();
        }else throw new Exception("No Date Found");
    }

    public GregorianCalendar stringToGregorianCalendar(String dateString) throws ParseException {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = simpledateformat.parse(dateString);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public List<GregorianCalendar> getWeekDates(GregorianCalendar calendar) {
        List<GregorianCalendar> weekDates = new ArrayList<>();
        int daysInWeek = 5;

        for (int i = 0; i < daysInWeek; i++) {
            weekDates.add(new GregorianCalendar());
            weekDates.get(i).setTime(calendar.getTime());
            weekDates.get(i).add(Calendar.DAY_OF_YEAR, i);
        }
        return weekDates;
    }

    public void populateItemAndRestaurantLists(GregorianCalendar startDate, String htmlContent, List<Restaurant> restaurants, List<MenuItem> menuItems) throws Exception {
        //TODO skip current instead of throwing exception
        String contentSplitByDate[] = htmlContent.split("<h2>");
        List<GregorianCalendar> dates = getWeekDates(startDate);
        for (int i = 1; i < 6 && i < contentSplitByDate.length; i++) {
            String splitByRestaurant[] = contentSplitByDate[i].split("<h3>");
            for (int j = 1; j < splitByRestaurant.length; j++) {
                String temp[] = splitByRestaurant[j].split("\">");
                String name;
                if (temp.length > 1) {
                    name = temp[1].split("<")[0].trim();
                } else {
                    throw new Exception("input does not have the expected format");
                }

                ContainerToMenuItemConverter restaurantChecker = new ContainerToMenuItemConverter();
                Restaurant currentRestaurant = new Restaurant(name, null, null);

                int restaurantLocation = restaurantChecker.existsInRestaurantListAtIndex(restaurants, currentRestaurant);
                if (restaurantLocation > -1) {
                    currentRestaurant = restaurants.get(restaurantLocation);
                } else {
                    temp=htmlContent.split(Pattern.quote(currentRestaurant.name + " ("));
                    if (temp.length > 1) {
                        currentRestaurant.address = temp[1].split(",")[0];
                        currentRestaurant.telephoneNumber = htmlContent.split(Pattern.quote(currentRestaurant.name + " ("))[1].split("Tel.")[1].split("\\)")[0].trim();

                        currentRestaurant.name = StringEscapeUtils.unescapeHtml4(currentRestaurant.name);
                        currentRestaurant.address = StringEscapeUtils.unescapeHtml4(currentRestaurant.address);

                        restaurants.add(currentRestaurant);
                    } else {
                        throw new Exception("input does not have the expected format");
                    }
                }

                String menuItemDescriptions[] = splitByRestaurant[j].split("<li>");
                for (int k = 1; k < menuItemDescriptions.length; k++) {
                    String description = menuItemDescriptions[k].split("</li>")[0];
                    MenuItem currentItem = new MenuItem(currentRestaurant, description, dates.get(i - 1));
                    currentItem.content = StringEscapeUtils.unescapeHtml4(currentItem.content);
                    if(menuItemExistsInListAt(menuItems, currentItem) < 0)
                    {
                        menuItems.add(currentItem);
                    }
                }
            }
        }
    }

    public int menuItemExistsInListAt(List<MenuItem> menuItemList, MenuItem currentItem) {

        int index = -1;
        for (int i = 0; i < menuItemList.size(); i++) {
            if (menuItemList.get(i).content.equals(currentItem.content) && menuItemList.get(i).date == currentItem.date && menuItemList.get(i).restaurant.name.equals(currentItem.restaurant.name)) {
                index = i;
            }
        }
        return index;
    }
  }

