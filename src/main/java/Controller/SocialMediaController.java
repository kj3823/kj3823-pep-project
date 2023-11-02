package Controller;

import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;
    private final ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javelin object from this method.
     * @return a Javelin app object which defines the behavior of the Javelin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.post("login", this::login);
        app.post("register",this::postAccountHandler);
        app.post("messages", this::postMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getMessageUsingAccountIDHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javelin Context object manages information about both the HTTP request and response.
     */

    private void getMessageHandler(Context ctx)throws JsonProcessingException
    {
        String pathParam = ctx.pathParam("message_id");
        Message retrievedMessage = this.messageService.getMessage(Integer.parseInt(pathParam));
        if(retrievedMessage != null)
        {
            ctx.json(mapper.writeValueAsString(retrievedMessage));
        }
        ctx.status(200);
    }
    private void getAllMessagesHandler(Context context)throws JsonProcessingException
    {
        context.json(mapper.writeValueAsString(this.messageService.getAllMessages()));
        context.status(200);
    }

    private void login(Context context)throws JsonProcessingException
    {
        Account retrievedAccount = null;
        Account account  = mapper.readValue(context.body(), Account.class);
        if(account.getUsername() != null || account.getPassword() != null)
        {
            retrievedAccount = this.accountService.Login(account);
        }
        if(retrievedAccount != null)
        {
            context.status(200);
            context.json(mapper.writeValueAsString(retrievedAccount));
        }
        else
        {
            context.status(401);
//            context.json(mapper.writeValueAsString("Invalid Username or Password!"));
        }
    }

    private void postAccountHandler(Context ctx)throws JsonProcessingException
    {
        Account retrievedAccount;
        Account newAccount  = mapper.readValue(ctx.body(), Account.class);
        if(!newAccount.getUsername().isEmpty() && newAccount.getPassword().length() > 4)
        {
            retrievedAccount = this.accountService.addAccount(newAccount);
            if(retrievedAccount != null)
            {
                ctx.json(mapper.writeValueAsString(retrievedAccount));
                ctx.status(200);
                return;
            }
        }
        ctx.status(400);
//        ctx.json(mapper.writeValueAsString("Error Adding User!"));
    }

    private void postMessageHandler(Context ctx)throws JsonProcessingException {
        Message newMessage = null;
        Message message = mapper.readValue(ctx.body(), Message.class);
        if (!message.getMessage_text().isEmpty() && message.getMessage_text().length() < 255) {
            newMessage = this.messageService.addMessage(message);
        }
        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

        private void deleteMessageHandler(Context ctx)throws JsonProcessingException
        {
            String messageID = ctx.pathParam("message_id");
            Message deletedMessage = this.messageService.deleteMessage(Integer.parseInt(messageID));
            if(deletedMessage != null)
            {
                ctx.json(mapper.writeValueAsString(deletedMessage));
            }
            ctx.status(200);
        }
        private void updateMessageHandler(Context ctx)throws JsonProcessingException
        {
            Message toBeUpdated = mapper.readValue(ctx.body(), Message.class);
            String messageID = ctx.pathParam("message_id");
            if(!toBeUpdated.getMessage_text().isEmpty() && toBeUpdated.getMessage_text().length() < 255)
            {
                Message updatedMessage =  this.messageService.updateMessage(Integer.parseInt(messageID), toBeUpdated.getMessage_text());
                if(updatedMessage != null)
                {
                    ctx.json(mapper.writeValueAsString(updatedMessage));
                    ctx.status(200);
                    return;
                }
            }
            ctx.status(400);
        }
        private void getMessageUsingAccountIDHandler(Context ctx)throws JsonProcessingException
        {
            String accountID = ctx.pathParam("account_id");
            System.out.println(accountID);
            ctx.json(mapper.writeValueAsString(this.messageService.getMessagesBasedOnUserID(Integer.parseInt(accountID))));
            ctx.status(200);
        }

}