package com.rizaldi.contoh;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class MessageController {
    private LineMessagingClient client;

    @Autowired
    public MessageController(LineMessagingClient client) {
        this.client = client;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MessageController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageController.class, args);
    }

    @EventMapping
    public void handle(MessageEvent<TextMessageContent> event) {
        String text = event.getMessage().getText().toLowerCase();
        String[] pesanSplit = text.split(" ");
        if (pesanSplit[0].equals("apakah")) {
            String jawaban = getRandomJawaban();
            String replyToken = event.getReplyToken();
            balasChat(replyToken,jawaban);
        }
    }

    private String getRandomJawaban(){
        String jawaban="";
        int random=new Random().nextInt();
        if(random%2==0){
            jawaban="Ya";
        }
        else{
            jawaban="Tidak";
        }
        return jawaban;
    }

    private void balasChat(String replyToken,String jawaban) {
        TextMessage balasan=new TextMessage(jawaban);
        try {
            client
                    .replyMessage(new ReplyMessage(replyToken, balasan))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Ada error saat ingin membalas chat");
        }
    }
}
