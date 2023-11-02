package DAO;

import Model.Message;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import Util.ConnectionUtil;

public class MessageDAOImp implements MessageDAO {

    Connection connection;

    public MessageDAOImp()
    {
        this.connection = ConnectionUtil.getConnection();
    }

    @Override
    public Message createMessage(Message newMessage)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE account_id = ?");
            statement.setInt(1, newMessage.getPosted_by());
            ResultSet rs = statement.executeQuery();
            if(rs.next() != false)
            {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO MESSAGE VALUES (default, ?, ?, ?)");
                ps.setInt(1, newMessage.getPosted_by());
                ps.setString(2, newMessage.getMessage_text());
                ps.setLong(3, newMessage.getTime_posted_epoch());
                int updatedRows = ps.executeUpdate();
                if(updatedRows > 0)
                {
                    statement = connection.prepareStatement("SELECT * FROM MESSAGE WHERE posted_by = ? AND message_text = ?");
                    statement.setInt(1, newMessage.getPosted_by());
                    statement.setString(2, newMessage.getMessage_text());
                    rs = statement.executeQuery();
                    if(rs.next()) {
                        newMessage.setMessage_id(rs.getInt(1));
                        return newMessage;
                    }
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public List<Message> getMessages()
    {
        List<Message> messages = new ArrayList<>();
        try
        {
            String sql = "SELECT * from Message";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
            {
                Message message =  new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                messages.add(message);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return messages;
    }
    @Override
    public Message getMessage(int messageID)
    {
        Message message = null;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Message WHERE message_id = ?");
            statement.setInt(1, messageID);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                message =  new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return message;
    }
    @Override
    public Message deleteMessage(int messageID)
    {
        Message message = null;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Message WHERE message_id = ?");
            statement.setInt(1, messageID);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                message =  new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                statement = connection.prepareStatement("DELETE FROM Message WHERE message_id = ?");
                statement.setInt(1, messageID);
                int updatedRows = statement.executeUpdate();
                if(updatedRows > 0)
                {
                    System.out.println("Deleted!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return message;
    }
    @Override
    public Message updateMessage(int messageID, String newMessage){
        Message message = null;
        try
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE Message SET message_text = ?  WHERE message_id = ? ");
            statement.setString(1, newMessage);
            statement.setInt(2, messageID);
            int updatedRows = statement.executeUpdate();
            if(updatedRows > 0)
            {
                statement = connection.prepareStatement("SELECT * FROM Message WHERE message_id = ?");
                statement.setInt(1, messageID);
                ResultSet rs = statement.executeQuery();
                rs.next();
                message =  new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return message;
    }
    @Override
    public List<Message> getAllMessagesForUser(int user_id)
    {
        List<Message> messages = new ArrayList<>();
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Message WHERE posted_by = ? ");
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                System.out.println("IN while loop");
                messages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
            System.out.println(messages);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return messages;
    }

}

