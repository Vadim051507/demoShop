package com.example.demoShop.service;

import com.example.demoShop.dto.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    @Test
    void shouldAddItemToCart() {
        CartItem item = new CartItem(1L, "Букет троянд", 1100, "img.jpg");

        cartService.addItem(item);

        assertEquals(1, cartService.getItems().size());
        assertEquals("Букет троянд", cartService.getItems().get(0).getName());
    }

    @Test
    void shouldIncrementQuantityWhenSameItemAdded() {
        CartItem item1 = new CartItem(1L, "Букет троянд", 1100, "img.jpg");
        CartItem item2 = new CartItem(1L, "Букет троянд", 1100, "img.jpg");

        cartService.addItem(item1);
        cartService.addItem(item2);

        assertEquals(1, cartService.getItems().size());
        assertEquals(2, cartService.getItems().get(0).getQuantity());
    }

    @Test
    void shouldAddDifferentItemsAsSeparateEntries() {
        CartItem roses = new CartItem(1L, "Букет троянд", 1100, "img1.jpg");
        CartItem tulips = new CartItem(2L, "Тюльпани", 750, "img2.jpg");

        cartService.addItem(roses);
        cartService.addItem(tulips);

        assertEquals(2, cartService.getItems().size());
    }

    @Test
    void shouldRemoveItemById() {
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));
        cartService.addItem(new CartItem(2L, "Тюльпани", 750, "img2.jpg"));

        cartService.removeItem(1L);

        assertEquals(1, cartService.getItems().size());
        assertEquals(2, cartService.getItems().get(0).getProductId());
    }

    @Test
    void shouldDoNothingWhenRemovingNonExistentItem() {
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));

        assertDoesNotThrow(() -> cartService.removeItem(999L));
        assertEquals(1, cartService.getItems().size());
    }

    @Test
    void shouldClearAllItems() {
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));
        cartService.addItem(new CartItem(2L, "Тюльпани", 750, "img2.jpg"));

        cartService.clear();

        assertTrue(cartService.getItems().isEmpty());
    }

    @Test
    void shouldClearEmptyCartWithoutError() {
        assertDoesNotThrow(() -> cartService.clear());
        assertEquals(0, cartService.getCount());
    }

    @Test
    void shouldCalculateTotalCorrectly() {
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));
        cartService.addItem(new CartItem(2L, "Тюльпани", 750, "img2.jpg"));

        assertEquals(1850, cartService.getTotal());
    }

    @Test
    void shouldCalculateTotalWithQuantity() {
        CartItem item = new CartItem(1L, "Букет троянд", 1100, "img.jpg");
        cartService.addItem(item);
        cartService.addItem(item);

        assertEquals(2200, cartService.getTotal());
    }

    @Test
    void shouldReturnZeroTotalForEmptyCart() {
        assertEquals(0, cartService.getTotal());
    }

    @Test
    void shouldCountTotalQuantityNotUniqueItems() {
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));
        cartService.addItem(new CartItem(1L, "Букет троянд", 1100, "img.jpg"));
        cartService.addItem(new CartItem(2L, "Тюльпани", 750, "img2.jpg"));

        assertEquals(3, cartService.getCount());

    }

    @Test
    void shouldReturnZeroCountForEmptyCart() {
        assertEquals(0, cartService.getCount());
    }
}
