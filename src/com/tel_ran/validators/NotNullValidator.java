package com.tel_ran.validators;

/**
 * Created by rbuga on 1/1/2018.
 */
public class NotNullValidator {
    public static void validate(Object o){
        if(o == null){
            throw new RuntimeException("Field is null");
        }

    }
}
