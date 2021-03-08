package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class CurrencyEmaStatus implements Serializable {

    private String pairTime;
    private Double ema9;
    private Double ema20;
    private Double ema80;
    private Currency currency;

    public CurrencyEmaStatus() {}

    public CurrencyEmaStatus(String pairTime, Double ema9, Double ema20, Double ema80, Currency currency) {
        this.pairTime = pairTime;
        this.ema9 = ema9;
        this.ema20 = ema20;
        this.ema80 = ema80;
        this.currency = currency;
    }

    public String getPairTime() {
        return pairTime;
    }

    public void setPairTime(String pairTime) {
        this.pairTime = pairTime;
    }

    public Double getEma9() {
        return ema9;
    }

    public void setEma9(Double ema9) {
        this.ema9 = ema9;
    }

    public Double getEma20() {
        return ema20;
    }

    public void setEma20(Double ema20) {
        this.ema20 = ema20;
    }

    public Double getEma80() {
        return ema80;
    }

    public void setEma80(Double ema80) {
        this.ema80 = ema80;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean isPriceAboveAllEMAs(){

        if(currency.getPrice() >= ema9 && currency.getPrice() >= ema20 && currency.getPrice()>= ema80) {
            return true;
        }
        return false;
    }

    public Boolean isPriceBellowAllEMAs(){
        if(currency.getPrice() <= ema9 && currency.getPrice() <= ema20 && currency.getPrice()<= ema80) {
            return true;
        }
        return false;
    }

    public Boolean isPriceAboveEMA9(){
        if(currency.getPrice() >= ema9){
            return true;
        }
        return false;
    }

    public Boolean isPriceAboveEMA20(){
        if(currency.getPrice() >= ema20){
            return true;
        }
        return false;
    }

    public Boolean isPriceAboveEMA80(){
        if(currency.getPrice() >= ema80){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEmaStatus that = (CurrencyEmaStatus) o;
        return pairTime.equals(that.pairTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairTime);
    }

    @Override
    public String toString() {
        return "CurrencyStatus{" +
                "time='" + pairTime + '\'' +
                ", ema9=" + ema9 +
                ", ema20=" + ema20 +
                ", ema80=" + ema80 +
                ", currency=" + currency +
                '}';
    }
}