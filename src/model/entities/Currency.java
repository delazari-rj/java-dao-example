package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Currency implements Serializable {

    private String pair;
    private Double price;

    public Currency(){};

    public Currency(String pair, Double price) {
        this.pair = pair;
        this.price = price;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(pair, currency.pair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "pair='" + pair + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
