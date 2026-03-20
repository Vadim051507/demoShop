package com.example.demoShop.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactRequestTest {

    @Test
    void shouldStoreAndReturnAllFields() {
        ContactRequest request = new ContactRequest();
        request.setName("Вадим");
        request.setEmail("wadimlikar@gmail.com");
        request.setPhone("+380966008376");
        request.setSubject("Замовлення букету");
        request.setMessage("Хочу замовити троянди");

        assertEquals("Вадим", request.getName());
        assertEquals("wadimlikar@gmail.com", request.getEmail());
        assertEquals("+380966008376", request.getPhone());
        assertEquals("Замовлення букету", request.getSubject());
        assertEquals("Хочу замовити троянди", request.getMessage());

    }

    @Test
    void shouldHavenNullFieldsByDefault() {
        ContactRequest request = new ContactRequest();

        assertNull(request.getName());
        assertNull(request.getEmail());
        assertNull(request.getPhone());
        assertNull(request.getSubject());
        assertNull(request.getMessage());
    }

    @Test
    void shouldAllowNullPhone() {
        ContactRequest request = new ContactRequest();

        request.setName("Вадим");
        request.setEmail("wadimlikar@gmail.com");
        request.setPhone(null);
        request.setSubject("Other");
        request.setMessage("Question");

        assertNull(request.getPhone());
        assertNull(request.getName());
        assertNull(request.getEmail());
    }


}
