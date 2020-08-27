package org.fyan102.algorithms.class_generator;

import org.fyan102.algorithms.interfaces.ITest;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class ClassGenerator {
    public static void main(String[] args) {
        new ClassGenerator().test();
    }

    public void generate(final String className, final String code) {
        try {
            // write code to file
            File javaFile = new File("src/" + className.replace(".", "/") + ".java");
            PrintWriter writer = new PrintWriter(new FileWriter(javaFile));
            writer.print(code);
            writer.close();
            // compile the new code
            File distDir = new File("out/production/AlgorithmsDemo/");
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compileResult = compiler.run(null, null, null,
                    "-d", distDir.getAbsolutePath(), javaFile.getAbsolutePath());
            if (compileResult != 0) {
                System.err.println("Failed!!");
                return;
            }
            Class.forName(className);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        String classPath = "out/production/AlgorithmsDemo/";
        String className = "org.fyan102.algorithms.test.TestClassGenerator";
        ClassReloader reloaded = new ClassReloader(classPath);
        try {
            Class<?> loadClass = reloaded.loadClass(className);
            ITest test = (ITest) loadClass.getConstructor(new Class[]{Integer.class}).newInstance(new Object[]{5});
            test.setValue(10);
            System.out.println(test.getValue());
            String code = "package org.fyan102.algorithms.test;\n" +
                    "import org.fyan102.algorithms.interfaces.ITest;\n" +
                    "public class TestClassGenerator implements ITest {\n" +
                    "\tprivate int value;\n" +
                    "\tpublic TestClassGenerator(){\n" +
                    "\t\tvalue=0;\n" +
                    "\t}\n" +
                    "\tpublic void setValue(int newValue){\n" +
                    "\t\tvalue=newValue;\n" +
                    "\t}\n" +
                    "\tpublic int getValue(){\n" +
                    "\t\treturn value;\n" +
                    "\t}\n" +
                    "}\n";

            generate(className, code);
            loadClass = reloaded.findClass(className);
            test = (ITest) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
            test.setValue(10);
            System.out.println(test.getValue());
        }
        catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
