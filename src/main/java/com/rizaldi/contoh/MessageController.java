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

@LineMessageHandler
public class MessageController {
    private LineMessagingClient client;

    @Autowired
    public MessageController(LineMessagingClient client) {
        this.client = client;
    }

    @EventMapping
    public void handle(MessageEvent<TextMessageContent> event) {
        String text = event.getMessage().getText().toLowerCase();
        String[] pesanSplit = text.split(" ");
        if (pesanSplit[0].equals("apakah")) {
            String jawaban = getRandomJawaban();
            String replyToken = event.getReplyToken();

        }
    }

    private String getRandomJawaban(){
        String jawaban="";
        int random=new random().nextInt();
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
        ReplyMessage replyMessage=new ReplyMessage(replyToken,balasan);
        client.replyMessage(replyMessage);
    }
}
}
g