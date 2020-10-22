package com.raintree.syncdemo;

public class Contact {

    private String Name;
    private String Addrs;
    private  String Designation;
    private  int Sync_status;


    Contact(String Name,String Addrs,String Designation,int sync_status)
    {
        this.setName(Name);
        this.setAddres(Addrs);
        this.setDesignation(Designation);
        this.setSync_status(sync_status);

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddrs() {
        return Addrs;
    }

    public void setAddres(String addres) {
        Addrs= addres;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public int getSync_status() {
        return Sync_status;
    }

    public void setSync_status(int sync_status) {
        Sync_status = sync_status;
    }
}
