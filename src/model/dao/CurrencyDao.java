package model.dao;

import model.entities.Currency;

import java.sql.ResultSet;
import java.util.List;

public interface CurrencyDao {

    void insert(Currency c);
    void update(Currency c);
    void deleteByPair(String pair);
    Currency findByPair(String pair);
    List<Currency> findAll();

}
