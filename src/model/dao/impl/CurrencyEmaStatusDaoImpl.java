package model.dao.impl;

import model.dao.CurrencyDao;
import model.dao.CurrencyEmaStatusDao;
import model.dao.DBConnection;
import model.dao.DBException;
import model.entities.Currency;
import model.entities.CurrencyEmaStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyEmaStatusDaoImpl implements CurrencyEmaStatusDao {

    private Connection conn;

    public CurrencyEmaStatusDaoImpl(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(CurrencyEmaStatus ces) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO " + CurrencyEmaStatusDaoModel.TABLE + " "
                            + "(" + CurrencyEmaStatusDaoModel.PAIRTIME
                            + ", " + CurrencyEmaStatusDaoModel.EMA9
                            + ", " + CurrencyEmaStatusDaoModel.EMA20
                            + ", " + CurrencyEmaStatusDaoModel.EMA80
                            + ", " + CurrencyEmaStatusDaoModel.CURRENCYPAIR+") "
                            + "VALUES "
                            + "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, ces.getPairTime());
            st.setDouble(2, ces.getEma9());
            st.setDouble(3, ces.getEma20());
            st.setDouble(4, ces.getEma80());
            st.setString(5, ces.getCurrency().getPair());

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
    public void update(CurrencyEmaStatus ces) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE " + CurrencyEmaStatusDaoModel.TABLE + " "
                            + "SET " + CurrencyEmaStatusDaoModel.PAIRTIME + " = ?, "
                            + CurrencyEmaStatusDaoModel.EMA9 + " = ?, "
                            + CurrencyEmaStatusDaoModel.EMA20 + " = ?, "
                            + CurrencyEmaStatusDaoModel.EMA80 + " = ?, "
                            + CurrencyEmaStatusDaoModel.CURRENCYPAIR + " = ? "
                            + "WHERE " + CurrencyEmaStatusDaoModel.PAIRTIME + " = ?");

            st.setString(1, ces.getPairTime());
            st.setDouble(2, ces.getEma9());
            st.setDouble(3, ces.getEma20());
            st.setDouble(4, ces.getEma80());
            st.setString(5, ces.getCurrency().getPair());
            st.setString(6, ces.getPairTime());
            st.executeUpdate();
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public void deleteByPairTime(String pairTime) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM " + CurrencyEmaStatusDaoModel.TABLE + " "
                            + "WHERE " + CurrencyEmaStatusDaoModel.PAIRTIME + " = ?");

            st.setString(1, pairTime);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public CurrencyEmaStatus findByPairTime(String pairTime) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM " + CurrencyEmaStatusDaoModel.TABLE
                            + " INNER JOIN " + CurrencyDaoModel.TABLE
                            + " ON " + CurrencyEmaStatusDaoModel.CURRENCYPAIR + " = " + CurrencyDaoModel.PAIR
                            + " WHERE " + CurrencyEmaStatusDaoModel.PAIRTIME + " = ?");
            st.setString(1, pairTime);
            rs = st.executeQuery();
            CurrencyEmaStatus ces = null;
            if(rs.next()){
                ces = instantiateCurrencyEmaStatus(rs, instantiateCurrency(rs));
            }
            return ces;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public List<CurrencyEmaStatus> findByPair(String pair) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                        "SELECT * FROM " + CurrencyEmaStatusDaoModel.TABLE
                                + " INNER JOIN " + CurrencyDaoModel.TABLE
                                + " ON " + CurrencyEmaStatusDaoModel.CURRENCYPAIR + " = " + CurrencyDaoModel.PAIR
                                + " WHERE " + CurrencyDaoModel.PAIR + " = ?"
                                + " ORDER BY " + CurrencyEmaStatusDaoModel.PAIRTIME);
            st.setString(1, pair);
            rs = st.executeQuery();
            List<CurrencyEmaStatus> listCes = new ArrayList<>();
            Map<String, Currency> map = new HashMap<>();
            while(rs.next()){
                Currency currency = map.get(rs.getString("Pair"));
                if(currency == null){
                    currency = instantiateCurrency(rs);
                    map.put(rs.getString("Pair"), currency);
                }
                CurrencyEmaStatus ces = instantiateCurrencyEmaStatus(rs, currency);
                listCes.add(ces);
            }
            return listCes;
        }catch(SQLException e){
            throw new DBException(e.getMessage());
        }finally {
            DBConnection.closeResultSet(rs);
            DBConnection.closeStatemant(st);
        }
    }

    @Override
    public List<CurrencyEmaStatus> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM " + CurrencyEmaStatusDaoModel.TABLE
                            + " INNER JOIN " + CurrencyDaoModel.TABLE
                            + " ON " + CurrencyEmaStatusDaoModel.CURRENCYPAIR + " = " + CurrencyDaoModel.PAIR
                            + " ORDER BY " + CurrencyEmaStatusDaoModel.PAIRTIME);
            rs = st.executeQuery();
            List<CurrencyEmaStatus> listCes = new ArrayList<>();
            Map<String, Currency> map = new HashMap<>();
            while(rs.next()){
                Currency currency = map.get(rs.getString("Pair"));
                if(currency == null){
                    currency = instantiateCurrency(rs);
                    map.put(rs.getString("Pair"), currency);
                }
                CurrencyEmaStatus ces = instantiateCurrencyEmaStatus(rs, currency);
                listCes.add(ces);
            }
            return listCes;
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

    private static CurrencyEmaStatus instantiateCurrencyEmaStatus(ResultSet rs, Currency currency) throws SQLException{
        CurrencyEmaStatus ces = new CurrencyEmaStatus();
        ces.setPairTime(rs.getString("PairTime"));
        ces.setEma9(rs.getDouble("Ema9"));
        ces.setEma20(rs.getDouble("Ema20"));
        ces.setEma80(rs.getDouble("Ema80"));
        ces.setCurrency(currency);
        return ces;
    }

    public void closeCurrencyEmaStatusConnection(){
        DBConnection.closeConnetion(conn);
    }
}
