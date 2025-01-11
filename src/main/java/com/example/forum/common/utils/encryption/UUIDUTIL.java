package com.example.forum.common.utils.encryption;

import java.util.UUID;

public class UUIDUTIL {
    public static  String UUID_36(){
        return UUID.randomUUID().toString();
    }
    public static String UUID_32(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
