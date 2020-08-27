package org.fyan102.algorithms.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ClassReLoader extends ClassLoader {
    private String classPath;
    private HashMap<String, Class<?>> classList;
    
    public ClassReLoader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
        classList = new HashMap<>();
    }
    
    @Override
    public Class<?> findClass(String name) {
        Class<?> klass = classList.get(name);
        if (klass == null) {
            byte[] data = loadClassData(name);
            assert data != null;
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
            int b;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
            inputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
