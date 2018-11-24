package es.iessaladillo.pedrojoya.pr05.data.local.model;

public class Users {
    private Avatar avatar;
    private String name, adress, mail, web;
    private int phoneNumer;
    private int id;
    private static int idCounter=0;

    public Users(Avatar avatar, String name, String adress, String mail, String web, int phoneNumer) {
        this.avatar = avatar;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.web = web;
        this.phoneNumer = phoneNumer;
        id = idCounter;
        idCounter++;
    }

    public int getId() {
        return id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(int phoneNumer) {
        this.phoneNumer = phoneNumer;
    }
}
