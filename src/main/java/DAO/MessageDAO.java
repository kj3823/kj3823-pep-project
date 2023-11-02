package DAO;

import Model.Message;
import java.util.List;

public interface MessageDAO {
    public Message createMessage(Message newMessage);
    public List<Message> getMessages();
    public Message getMessage(int messageID);
    public Message deleteMessage(int messageID);
    public Message updateMessage(int messageID, String newMessage);
    public List<Message> getAllMessagesForUser(int user_id);
}
