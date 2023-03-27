package org.example;

import java.util.Objects;

public class Helpers {
    public static boolean isExist(String keyword){
        for(int i =0;i< StaticFiles.PASCALKEYWORDS.length; i++){
            if (Objects.equals(keyword, StaticFiles.PASCALKEYWORDS[i])){
                return  true;
            }
        }
        return false;
    }
}
