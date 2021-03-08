package model.dao;

import model.dao.impl.CurrencyDaoImpl;
import model.dao.impl.CurrencyEmaStatusDaoImpl;

import java.sql.Connection;

public class DaoFactory {

    public static CurrencyDao createCurrencyDao(){
        return new CurrencyDaoImpl(DBConnection.getConnection());
    }

    public static CurrencyEmaStatusDao createCurrencyEmaStatusDao(){
        return new CurrencyEmaStatusDaoImpl(DBConnection.getConnection());
    }
}
