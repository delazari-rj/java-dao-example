package model.dao.impl;

import model.dao.CurrencyDao;
import model.dao.DBConnection;
import model.dao.DBException;
import model.entities.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao {

    private Connection conn;

    public CurrencyDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Currency c) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
              "INSERT INTO " + CurrencyDaoModel.TABLE
                    + " (" + CurrencyDaoModel.PAIR + ", " + CurrencyDaoModel.PRICE +") "
                    + " VALUES "
                    + " (?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, c.getPair());
            st.setDouble(2, c.getPrice());

            int rowAffected = st.executeUpdate();
            if(rowAffected < 0){
                throw new DBException("Unexpected error! No rows affected!");
            }
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public void update(Currency currency) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE " + CurrencyDaoModel.TABLE
                            + " SET " + CurrencyDaoModel.PAIR + " = ?, " + CurrencyDaoModel.PRICE + "= ? "
                            + " WHERE " + CurrencyDaoModel.PAIR + " = ?");

            st.setString(1, currency.getPair());
            st.setDouble(2, currency.getPrice());
            st.setString(3,currency.getPair());
            st.executeUpdate();
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public void deleteByPair(String pair) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM " + CurrencyDaoModel.TABLE
                            + " WHERE " + CurrencyDaoModel.PAIR + " = ?");

            st.setString(1, pair);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public Currency findByPair(String pair) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM " + CurrencyDaoModel.TABLE
                        +   " WHERE " + CurrencyDaoModel.PRICE + " = ?");
            rs = st.executeQuery();
            Currency currency = null;
            if(rs.next()){
                currency = instantiateCurrency(rs);
            }
            return currency;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public List<Currency> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM " + CurrencyDaoModel.TABLE );
            rs = st.executeQuery();
            List<Currency> currencyList = new ArrayList<>();
            while(rs.next()){
                currencyList.add(instantiateCurrency(rs));
            }
            return currencyList;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatemant(st);
        }
    }

    private Currency instantiateCurrency(ResultSet rs) throws SQLException{
        return new Currency(rs.getString(CurrencyDaoModel.PAIR),
                            rs.getDouble(CurrencyDaoModel.PRICE));
    }

    public void closeCurrencyConnection(){
        DBConnection.closeConnetion(conn);
    }
}