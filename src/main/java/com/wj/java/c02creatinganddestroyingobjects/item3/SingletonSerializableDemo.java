package com.wj.java.c02creatinganddestroyingobjects.item3;

import com.wj.java.helper.Utils;

import java.io.*;

/*
    Enforce the singleton property with a private constructor or an enum type
 */

class NestedObject implements Serializable{

}

class NotSerializableSingleton implements Serializable{
    private final static NotSerializableSingleton INSTANCE = new NotSerializableSingleton();
    private transient NestedObject obj;
    private NotSerializableSingleton(){
        obj = new NestedObject();
    }

    static NotSerializableSingleton getInstance(){
        return INSTANCE;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        obj = new NestedObject();
    }

    @Override
    public String toString(){
        return "obj hashCode: " + Integer.valueOf(obj.hashCode()).toString() + ", hashCode: " + Integer.valueOf(this.hashCode()).toString();
    }
}


class SerializableSingleton implements Serializable{
    private final static SerializableSingleton INSTANCE = new SerializableSingleton();
    private NestedObject obj;
    private SerializableSingleton(){
        obj = new NestedObject();
    }

    static SerializableSingleton getInstance(){
        return INSTANCE;
    }

    // readResolve method to preserve singleton property
    private Object readResolve(){
        return INSTANCE;
    }

    @Override
    public String toString(){
        return "obj hashCode: " + Integer.valueOf(obj.hashCode()).toString() + ", hashCode: " + Integer.valueOf(this.hashCode()).toString();
    }
}


class SerializationHelper{

    static void serialize(String filePath, Object obj) {
        ObjectOutputStream os = null;
        try{
            os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Utils.resourcePath(filePath))));
            os.writeObject(obj);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static Object deserialize(String filePath){
        ObjectInputStream os = null;
        Object obj = null;
        try{
            os = new ObjectInputStream(new BufferedInputStream(new FileInputStream(Utils.resourcePath(filePath))));
            obj = os.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

}



public class SingletonSerializableDemo {

    private static void invokeSerialization(Serializable obj, String path){
        SerializationHelper.serialize(path, obj);
    }

    private static <T> T invokeDeserialization(String path, Class<T> clazz){
        return clazz.cast(SerializationHelper.deserialize(path));
    }

    public static void main(String[] args) {
        NotSerializableSingleton notSerializableSingleton = NotSerializableSingleton.getInstance();
        System.out.println(notSerializableSingleton);

        String filePath = "item3/not_serializable_object.txt";
        invokeSerialization(notSerializableSingleton, filePath);
        NotSerializableSingleton notSerializableSingleton1 = invokeDeserialization(filePath, NotSerializableSingleton.class);
        NotSerializableSingleton notSerializableSingleton2 = invokeDeserialization(filePath, NotSerializableSingleton.class);
        System.out.println(notSerializableSingleton1);
        System.out.println(notSerializableSingleton2);
        System.out.println(notSerializableSingleton1 == notSerializableSingleton2);
        System.out.println("-----------------------------------------");

        SerializableSingleton serializableSingleton = SerializableSingleton.getInstance();
        System.out.println(serializableSingleton);

        filePath = "item3/serializable_object.txt";
        invokeSerialization(serializableSingleton, filePath);
        SerializableSingleton serializableSingleton1 = invokeDeserialization(filePath, SerializableSingleton.class);
        SerializableSingleton serializableSingleton2 = invokeDeserialization(filePath, SerializableSingleton.class);
        System.out.println(serializableSingleton1);
        System.out.println(serializableSingleton2);
        System.out.println(serializableSingleton1 == serializableSingleton2);
    }
}
