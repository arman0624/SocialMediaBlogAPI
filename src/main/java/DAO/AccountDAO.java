package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

/**
 * This class interacts with the account database.
 */
public class AccountDAO {

    public Account insertAccount(Account a){
        if(usernameExists(a.getUsername()) || a.getPassword().length() < 4 || a.getUsername() == ""){
            return null;
        }
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO account (username, password) VALUES (?,?)");
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.execute();
            return login(a);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Account login(Account a){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE username=? AND password=?");
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean usernameExists(String username){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userRegistered(int id){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE account_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}