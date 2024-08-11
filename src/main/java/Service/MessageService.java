package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

/**
 * This class links the message DAO and Controller.
 */
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message m){
        return messageDAO.insertMessage(m);
    }

    public Message removeMessage(int messageId){
        return messageDAO.deleteMessageById(messageId);
    }

    public List<Message> getUserMessages(int accountId){
        return messageDAO.getAllMessagesByUserId(accountId);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int messageId){
        return messageDAO.getMessageByMessageId(messageId);
    }

    public Message updateMessage(int messageId, String updatedMessage){
        return messageDAO.UpdateMessageById(messageId, updatedMessage);
    }
}
