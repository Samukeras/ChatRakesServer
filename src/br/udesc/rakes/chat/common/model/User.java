package br.udesc.rakes.chat.common.model;

public class User implements java.io.Serializable {

    private String nickname;


    public User(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return getNickname();
    }

}