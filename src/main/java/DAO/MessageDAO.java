package DAO;

import Model.Message;
import DAO.AccountDAO;
import Util.ConnectionUtil;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class interacts with the message database.
 */
public class MessageDAO {
    
    public Message insertMessage(Message m){
        if(m.getMessage_text()=="" || m.getMessage_text().length() > 255 || !AccountDAO.userRegistered(m.getPosted_by())){
            return null;
        }
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)");
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM message WHERE posted_by=? AND message_text=? AND time_posted_epoch=?");
                ps1.setInt(1, m.getPosted_by());
                ps1.setString(2, m.getMessage_text());
                ps1.setLong(3, m.getTime_posted_epoch());
                ResultSet rs = ps1.executeQuery();
                if(rs.next()){
                    return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Message deleteMessageById(int messageId){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM message WHERE message_id=?");
            ps1.setInt(1, messageId);
            ResultSet rs1 = ps1.executeQuery();
            Message m = null;
            if(rs1.next()){
                m = new Message(rs1.getInt("message_id"), rs1.getInt("posted_by"), rs1.getString("message_text"), rs1.getLong("time_posted_epoch"));
            }
            PreparedStatement ps = conn.prepareStatement("DELETE FROM message WHERE message_id=?");
            ps.setInt(1, messageId);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                return m;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int accountId){
        List<Message> msgs = new ArrayList<>();
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM message WHERE posted_by=?");
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                msgs.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return msgs;
    }

    public List<Message> getAllMessages(){
        List<Message> msgs = new ArrayList<>();
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM message");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                msgs.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch")));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return msgs;
    }

    public Message getMessageByMessageId(int messageId){
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM message WHERE message_id=?");
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Message UpdateMessageById(int message_id, String updated_message){
        if(updated_message=="" || getMessageByMessageId(message_id) == null || updated_message.length()>255){
            return null;
        }
        try{
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE message SET message_text=? WHERE message_id=?");
            ps.setString(1, updated_message);
            ps.setInt(2, message_id);
            ps.execute();
            return getMessageByMessageId(message_id);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
