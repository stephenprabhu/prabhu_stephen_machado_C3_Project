import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("10:30:00").plusHours(2);
        Restaurant mockedRes = spy(restaurant);
        Mockito.when(mockedRes.getCurrentTime()).thenReturn(currentTime);
        assertTrue(mockedRes.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("10:30:00").minusHours(2);
        Restaurant mockedRes = spy(restaurant);
        Mockito.when(mockedRes.getCurrentTime()).thenReturn(currentTime);
        assertFalse(mockedRes.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void calculate_order_value_should_return_correct_order_total_for_given_menu_items(){
        ArrayList<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        int orderTotal = restaurant.calculate_order_total(selectedItems);

        int expectedTotal = 0;
        List<Item> menu = restaurant.getMenu();
        for(Item menuItem: menu) {
            if (menuItem.getName().equals("Sweet corn soup") || menuItem.getName().equals("Vegetable lasagne")) {
                expectedTotal += menuItem.getPrice();
            }
        }
        assertEquals(expectedTotal, orderTotal);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}