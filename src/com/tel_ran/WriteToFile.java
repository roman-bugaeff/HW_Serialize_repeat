package com.tel_ran;

import com.tel_ran.annotations.Length;
import com.tel_ran.annotations.NotNull;
import com.tel_ran.validators.LengthValidator;
import com.tel_ran.validators.NotNullValidator;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbuga on 1/1/2018.
 */
public class WriteToFile {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException {

        List<User> deserialize = deserialize();

        serialize(deserialize);
        System.out.println(deserialize.size());
        deserialize.stream().forEach(System.out::println);
    }

    private static void serialize(List<User> list) throws IOException, IllegalAccessException {
        File file = new File("resourses/users.bin");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        User user = new User();

        getFieldWithAnnotations(user);

        System.out.println("Input user name: ");
        user.setUserName(getFromKeyboard(br));

        System.out.println("Input password: ");
        user.setPassword(getFromKeyboard(br));

        System.out.println("Input First name: ");
        user.setFirstName(getFromKeyboard(br));

        System.out.println("Input  Last Name: ");
        user.setLastName(getFromKeyboard(br));

        System.out.println("Input email: ");
        user.setEmail(getFromKeyboard(br));



        list.add(user);

        oos.writeObject(list);
        fos.close();
        oos.close();

    }

    private static List<User> deserialize() throws IOException, ClassNotFoundException {
        File file = new File("resourses/users.bin");
        FileInputStream fis = new FileInputStream(file);

        if (fis.available() <= 0) {
            return new ArrayList<>();
        }
        ObjectInputStream ois = new ObjectInputStream(fis);

        List<User> list = (List<User>) ois.readObject();

        fis.close();
        ois.close();
        return list;
    }

    public static String getFromKeyboard(BufferedReader br) {
        String userName = null;
        String nInp = null;

        while (true) {

            try {
                nInp = br.readLine();
                if (!nInp.equals("") && !nInp.equalsIgnoreCase("exit")) {
                    userName = nInp;
                    break;
                } else {
                    if (!nInp.equals(""))
                        System.out.println("Empty name not allowed!");
                }
            } catch (IOException e) {
            }
        }
        return userName;
    }

    private static void notNullProcessing(Field field, Object user) throws IllegalAccessException {
        if(field.getAnnotation(NotNull.class) != null){
            field.setAccessible(true);
            Object obj = field.get(user);
            NotNullValidator.validate(obj);
        }
    }

    private static void lengthProcessing(Field field, Object user) throws IllegalAccessException {
        boolean annotationPresent = field.isAnnotationPresent(Length.class);
        if(annotationPresent){
            Length length = field.getAnnotation(Length.class);
            field.setAccessible(true);
            Object obj = field.get(user);
            LengthValidator.lengthValidate((String)obj, length.minValue(), length.maxValue());
        }
    }

    public static void getFieldWithAnnotations(Object user) throws IllegalAccessException {
        Field[] fields = user.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            notNullProcessing(field,user);
            lengthProcessing(field,user);
        }
    }
}
