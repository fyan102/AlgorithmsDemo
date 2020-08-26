package org.fyan102.algorithms.class_generator;

import java.io.*;

public class ClassReloader extends ClassLoader {
    private String classPath;

    public ClassReloader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
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
