package Service;

import DAO.MessageDAOImp;
import Model.Message;

import java.util.List;

public class MessageService
{
    private final MessageDAOImp messageDAOImp;

    public MessageService() {
        this.messageDAOImp = new MessageDAOImp();
    }

    public Message addMessage(Message message)
    {
        return this.messageDAOImp.createMessage(message);
    }
    public List<Message> getAllMessages()
    {
        return this.messageDAOImp.getMessages();
    }
    public Message getMessage(int messageID)
    {
        return this.messageDAOImp.getMessage(messageID);
    }
    public Message deleteMessage(int messageID)
    {
        return this.messageDAOImp.deleteMessage(messageID);
    }
    public Message updateMessage(int messageID, String updatedMessage)
    {
        return this.messageDAOImp.updateMessage(messageID, updatedMessage);
    }
    public List<Message> getMessagesBasedOnUserID(int accountID)
    {
        return this.messageDAOImp.getAllMessagesForUser(accountID);
    }
}
