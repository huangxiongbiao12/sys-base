package com.rm.common.web.security.config;

import lombok.Getter;

/**
 * Created by huangxb on 2017/6/27.
 */
@Getter
public enum EPlatform {
    Any("any"),
    IOS("iOS"),
    ANDROID("Android"),
    WEB("web"),
    Linux("Linux"),
    Mac_OS("Mac OS"),
    Windows("Windows"),
    Others("Others");

    EPlatform(String desc){
        this.description = desc;
    }


//    public EPlatform valuesOf(String des) {
//        EPlatform[] values = EPlatform.values();
//        for (EPlatform plat : values) {
//            if (plat.description.equals(des)) {
//                return plat;
//            }
//        }
//        return EPlatform.Others;
//    }

    public String toString(){
        return description;
    }

    private String description;
}