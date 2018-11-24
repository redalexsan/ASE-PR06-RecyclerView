package es.iessaladillo.pedrojoya.pr05.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private Avatar avatar;
    private String name, adress, mail, web;
    private int phoneNumer;
    private int id;
    private static int idCounter=0;

    public User(Avatar avatar, String name, String adress, String mail, String web, int phoneNumer) {
        this.avatar = avatar;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.web = web;
        this.phoneNumer = phoneNumer;
        id = idCounter;
        idCounter++;
    }

    protected User(Parcel in) {
        avatar = in.readParcelable(Avatar.class.getClassLoader());
        name = in.readString();
        adress = in.readString();
        mail = in.readString();
        web = in.readString();
        phoneNumer = in.readInt();
        id = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(avatar, flags);
        dest.writeString(name);
        dest.writeString(adress);
        dest.writeString(mail);
        dest.writeString(web);
        dest.writeInt(phoneNumer);
        dest.writeInt(id);
    }
}
