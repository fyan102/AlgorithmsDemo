package org.fyan102.algorithms;

import org.fyan102.algorithms.ui.ClassGenerator;

public class Main {
    public static void main(String[] args) {
        new ClassGenerator().generate("package org.fyan102.algorithms.test;\n" +
                "public class Heuristic {\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"Hello, world1\");\n" +
                "\t}\n" +
                "}\n");
    }
}
