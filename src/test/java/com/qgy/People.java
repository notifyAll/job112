package com.qgy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class People implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    public People() {
        // TODO Auto-generated constructor stub
        name = "老王";
        age = 22;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public static void main(String[] args) {
        People p = new People();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            FileOutputStream fos = new FileOutputStream("567");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        People p1;
        try {
            FileInputStream fis = new FileInputStream("567");
            ois = new ObjectInputStream(fis);
            p1 = (People) ois.readObject();
            System.out.println("name" + p1.name);
            System.out.println("age" + p1.age);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
