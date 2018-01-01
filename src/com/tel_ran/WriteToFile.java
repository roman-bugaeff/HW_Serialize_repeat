package com.tel_ran;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbuga on 1/1/2018.
 */
public class WriteToFile {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        List<User> deserialize = deserialize();
        serialize(deserialize);

        System.out.println(deserialize.size());
        deserialize.stream().forEach(System.out::println);
    }


    private static void serialize(List<User> list) throws IOException {
        File file = new File("resourses/users.bin");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        User user = new User();

        //while (true) {
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
                    if (!nInp.equals(""))//return null;
                        System.out.println("Empty name not allowed!");
                }
            } catch (IOException e) {
            }
        }
        return userName;
    }
}
