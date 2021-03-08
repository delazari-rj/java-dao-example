package model.entities.test;

import model.entities.Currency;
import model.entities.CurrencyEmaStatus;
import model.entities.enums.TimeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyEmaStatusTest {

    private CurrencyEmaStatus ema;
    private Currency currency;

    @BeforeEach
    void setUp(){
        currency = new Currency("ADAUSDT", 1.18);
        String pairTime = currency.getPair() + TimeStatus._1HOUR;
        ema = new CurrencyEmaStatus(pairTime, 1.10,0.90,0.80, currency);
    }
    @Test
    void getPairTime(){
        assertEquals(ema.getPairTime(), "ADAUSDT_1HOUR");
    }
    @Test
    void getEMA9(){
        assertEquals(ema.getEma9(), 1.10);
    }
    @Test
    void getEMA20(){
        assertEquals(ema.getEma20(), 0.90);
    }
    @Test
    void getEMA80(){
        assertEquals(ema.getEma80(), 0.80);
    }
    @Test
    void getCurrency(){
        assertEquals(ema.getCurrency().getPair(), "ADAUSDT");
        assertEquals(ema.getCurrency().getPrice(), 1.18);
    }
    @Test
    void setPairTime(){
        ema.setPairTime("ADABUSD" + TimeStatus._1DAY);
        assertEquals(ema.getPairTime(), "ADABUSD_1DAY");
    }
    @Test
    void setEMA9(){
        ema.setEma9(1.02);
        assertEquals(ema.getEma9(), 1.02);
    }
    @Test
    void setEMA20(){
        ema.setEma20(0.84);
        assertEquals(ema.getEma20(), 0.84);
    }
    @Test
    void setEMA80(){
        ema.setEma80(0.42);
        assertEquals(ema.getEma80(), 0.42);
    }
    @Test
    void setCurrency(){
        currency.setPair("ADABUSD");
        currency.setPrice(0.56);
        ema.setCurrency(currency);
        assertEquals(ema.getCurrency(), currency);
    }
    @Test
    void isPriceAboveAllEMAs(){
        configureEMAValues(40.0,30.0,20.0);

        configureCurrencyPrice(50.5);
        assertTrue(ema.isPriceAboveAllEMAs());

        configureCurrencyPrice(35.9);
        assertFalse(ema.isPriceAboveAllEMAs());

        configureCurrencyPrice(25.7);
        assertFalse(ema.isPriceAboveAllEMAs());

        configureCurrencyPrice(15.6);
        assertFalse(ema.isPriceAboveAllEMAs());
    }
    @Test
    void isPriceBellowAllEMAs(){
        configureEMAValues(50.0,40.0,30.0);

        configureCurrencyPrice(20.5);
        assertTrue(ema.isPriceBellowAllEMAs());

        configureCurrencyPrice(35.9);
        assertFalse(ema.isPriceBellowAllEMAs());

        configureCurrencyPrice(45.7);
        assertFalse(ema.isPriceBellowAllEMAs());

        configureCurrencyPrice(55.6);
        assertFalse(ema.isPriceBellowAllEMAs());
    }
    @Test
    void isPriceAboveEMA9(){
        configureEMAValues(20.1,15.2,10.3);

        configureCurrencyPrice(20.5);
        assertTrue(ema.isPriceAboveEMA9());

        configureCurrencyPrice(9.5);
        assertFalse(ema.isPriceAboveEMA9());
    }
    @Test
    void isPriceAboveEMA20(){
        configureEMAValues(20.2,15.5,10.7);

        configureCurrencyPrice(20.5);
        assertTrue(ema.isPriceAboveEMA20());

        configureCurrencyPrice(9.5);
        assertFalse(ema.isPriceAboveEMA20());
    }
    @Test
    void isPriceAboveEMA80(){
        configureEMAValues(20.9,15.6,10.2);

        configureCurrencyPrice(20.5);
        assertTrue(ema.isPriceAboveEMA80());

        configureCurrencyPrice(9.5);
        assertFalse(ema.isPriceAboveEMA80());
    }

    private void configureEMAValues(Double ema9, Double ema20, Double ema80){
        ema.setEma9(ema9);
        ema.setEma20(ema20);
        ema.setEma80(ema80);
    }

    private void configureCurrencyPrice(Double price){
        currency.setPrice(price);
        ema.setCurrency(currency);
    }
}