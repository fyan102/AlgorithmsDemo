package org.fyan102.algorithms.class_generator;

import java.io.*;
import java.util.HashMap;

public class ClassReloader extends ClassLoader {
    private String classPath;
    private HashMap<String, Class<?>> classList;
    public ClassReloader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
        classList = new HashMap<>();
    }
    
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> klass = classList.get(name);
        if (klass == null) {
            byte[] data = loadClassData(name);
            klass = this.defineClass(name, data, 0, data.length);
            classList.put(name, klass);
        }
        return klass;
    }

    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "/");
            FileInputStream inputStream = new FileInputStream(new File(classPath + name + ".class"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int b = 0;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
            inputStream.close();
            return outputStream.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
