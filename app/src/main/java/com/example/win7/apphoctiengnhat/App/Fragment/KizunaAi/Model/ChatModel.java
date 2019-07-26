package com.example.win7.apphoctiengnhat.App.Fragment.KizunaAi.Model;

/**
 * Created by ntkhai1996 on 04-Jan-18.
 */

public class ChatModel {

    private String chatSend;
    private String chatReceive;
    private boolean isNotSetAnimation;

    public ChatModel(String chatSend, String chatReceive, boolean isNotSetAnimation) {
        this.chatSend = chatSend;
        this.chatReceive = chatReceive;
        this.isNotSetAnimation = isNotSetAnimation;
    }

    public boolean isNotSetAnimation() {
        return isNotSetAnimation;
    }

    public void isNotSetAnimation(boolean notSetAnimation) {
        isNotSetAnimation = notSetAnimation;
    }

    public String getChatSend() {
        return chatSend;
    }

    public String getChatReceive() {
        return chatReceive;
    }
}
