package Controller;


import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper om;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.om = new ObjectMapper();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::loginHandler);
        app.post("/register", this::registerHandler);
        app.post("/messages", this::createMessageHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        app.get("/accounts/{id}/messages", this::getUserMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{id}", this::getMessageHandler);
        app.patch("/messages/{id}", this::updateMessageHandler);
        return app;
    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        Account a = om.readValue(ctx.body(), Account.class);
        Account temp = accountService.login(a);
        if(temp != null){
            ctx.status(200);
            ctx.json(om.writeValueAsString(temp));
        }
        else{
            ctx.status(401);
        }
    }

    private void registerHandler(Context ctx) throws JsonProcessingException{
        Account a = om.readValue(ctx.body(), Account.class);
        Account temp = accountService.addAccount(a);
        if(temp != null){
            ctx.status(200);
            ctx.json(om.writeValueAsString(temp));
        }
        else{
            ctx.status(400);
        }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        Message m = om.readValue(ctx.body(), Message.class);
        Message temp = messageService.addMessage(m);
        if(temp != null){
            ctx.status(200);
            ctx.json(om.writeValueAsString(temp));
        }
        else{
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message m = messageService.removeMessage(message_id);
        ctx.status(200);
        if(m != null){
            ctx.json(om.writeValueAsString(m));
        }
    }

    private void getUserMessagesHandler(Context ctx) throws JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("id"));
        ctx.status(200);
        ctx.json(messageService.getUserMessages(account_id));
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.status(200);
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        Message m = messageService.getMessage(message_id);
        ctx.status(200);
        if(m != null){
            ctx.json(om.writeValueAsString(m));
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("id"));
        JsonNode jn = om.readTree(ctx.body());
        String updated_message = jn.get("message_text").asText();
        Message m = messageService.updateMessage(message_id, updated_message);
        if(m!=null){
            ctx.status(200);
            ctx.json(om.writeValueAsString(m));
        }
        else{
            ctx.status(400);
        }
    }
}