package org.fyan102.algorithms.ui;

import java.io.*;

public class ClassGenerator {
    public void generate(String className, String code) {
        try {
            PrintWriter writer =
                    new PrintWriter("src\\org\\fyan102\\algorithms\\demos\\" + className + ".java");
            writer.print(code);
            writer.close();
            String[] commands = {"cmd /c javac src\\org\\fyan102\\algorithms\\demos\\" + className + ".java -d " +
                    "out\\production\\AlgorithmsDemo"};
            for (String cmd : commands) {
                Process p = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                try {
                    while ((line = input.readLine()) != null)
                        System.out.println(line);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
