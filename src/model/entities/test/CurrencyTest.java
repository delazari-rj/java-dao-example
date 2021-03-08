package model.entities.test;

import model.entities.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    private Currency currency;

    @BeforeEach
    void setUp() {
        currency = new Currency("BTCUSDT", 32250.0);
    }

    @Test
    void getPair() {
        assertEquals(currency.getPair(), "BTCUSDT");
    }

    @Test
    void setPair() {
        Currency c = new Currency();
        c.setPair("ETHUSDT");
        assertEquals(c.getPair(), "ETHUSDT");
    }

    @Test
    void getPrice() {
        assertEquals(currency.getPrice(), 32250.0);
    }

    @Test
    void setPrice() {
        Currency c = new Currency();
        c.setPrice(1089.0);
        assertEquals(c.getPrice(), 1089.0);
    }
}