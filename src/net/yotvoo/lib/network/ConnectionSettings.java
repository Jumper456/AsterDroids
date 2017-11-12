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
}
