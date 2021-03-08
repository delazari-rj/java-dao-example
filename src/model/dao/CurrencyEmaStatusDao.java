package model.dao;

import model.entities.CurrencyEmaStatus;

import java.util.List;

public interface CurrencyEmaStatusDao {
    void insert(CurrencyEmaStatus ces);
    void update(CurrencyEmaStatus ces);
    void deleteByPairTime(String pairTime);
    CurrencyEmaStatus findByPairTime(String pairTime);
    List<CurrencyEmaStatus> findByPair(String pair);
    List<CurrencyEmaStatus> findAll();
}
