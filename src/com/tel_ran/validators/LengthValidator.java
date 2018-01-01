package com.tel_ran.validators;

/**
 * Created by rbuga on 1/1/2018.
 */
public class LengthValidator {
    public static void lengthValidate(String str, int min, int max){
        if(str == null && min ==0){
            return;
        }
        if(str == null){
            throw new RuntimeException("Length is not valid. Value is null");
        }
        if(str.length() <= max && str.length() >= min){
            return;
        }
        throw new RuntimeException("Length is not valid. Length is not match");
    }
}
