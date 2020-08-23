package org.fyan102.algorithms.ui;

import java.io.*;

public class ClassGenerator {
    public boolean generate(String code) {
        try (PrintWriter writer = new PrintWriter("Heuristic.java")) {
            writer.print(code);
            writer.close();
            Process p = Runtime.getRuntime().exec("cmd /c javac Heuristic.java");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;

            try {
                while ((line = input.readLine()) != null)
                    System.out.println(line);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
