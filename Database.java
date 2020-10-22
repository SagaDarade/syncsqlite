package com.raintree.syncdemo;

public class Database {

    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_FAILED = 1;

   public static final String BASE_URL = "http://169.254.179.76/syncdemo/syncinfo.php/";
    //public static final String SERVER_URL="http://169.254.179.76/syncdemo/syncinfo1.php";
    public static final String UI_UPDATE_BROADCAST ="com.net.synctest.updatebroadcast";

    public static final String DB_NAME = "Contactdb";
    public static final String TABLE_NAME = "Contctinfo";
    public static final String NAME = "name";
    public static final String ADDRESS = "addrs";
    public static final String DESIGNATION = "desg";
    public static final String SYNC_STATUS = "syncstatus";
}
