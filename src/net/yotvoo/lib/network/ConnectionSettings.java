package net.yotvoo.lib.network;

import java.io.Serializable;

public class ConnectionSettings implements Serializable {
    private int id;
    private String name;
    private String address;
    private String port;
    private String userName;
    private String userPassword;
    private String notes;

    public ConnectionSettings() {
    }

    public ConnectionSettings(int id, String name, String address, String port, String userName, String userPassword, String notes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ConnectionSettings{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", port='" + port + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
