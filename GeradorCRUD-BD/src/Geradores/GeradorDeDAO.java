package Geradores;

import Entidades.Atribute;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class GeradorDeDAO {

    public GeradorDeDAO(String className, List<Atribute> atributos, String classNameBD) {
        StringTools st = new StringTools();
        String classNameMin = st.firstLetterToLowerCase(className);
        List<String> cg = new ArrayList();

        // Package, Imports and public class
        cg.add("package DAOs;\n\n"
                + "import Entidades." + className + ";\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n\n"
                + "public class DAO" + className + " extends DAOGenerico<" + className + "> {\n");

        // Construtor
        cg.add("    public DAO" + className + "() {\n"
                + "        super(" + className + ".class);\n"
                + "    }\n");

        // AutoID
        if (atributos.get(0).getTypeJava().equals("int")) {
            cg.add("    public int autoId" + className + "() {\n"
                    + "        Integer a = (Integer) em.createQuery(\"SELECT MAX(e." + atributos.get(0).getNameJava() + ") FROM " + classNameBD + "e \").getSingleResult();\n"
                    + "        if(a != null) {\n"
                    + "            return a + 1;\n"
                    + "        } else {\n"
                    + "            return 1;\n"
                    + "        }\n"
                    + "    }");
        }

        // Lists and ListsInOrder
        for (int i = 0; i < atributos.size(); i++) {

            if (atributos.get(i).getTypeJava().equals("String")) {
                cg.add("    public List<" + className + "> listBy" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(" + atributos.get(i).getTypeJava() + " " + st.firstLetterToLowerCase(atributos.get(i).getNameJava()) + ") {\n"
                        + "        return em.createQuery(\"SELECT e FROM " + classNameBD + " e WHERE e." + atributos.get(i).getNameBD() + " LIKE :" + atributos.get(i).getNameJava() + "\").setParameter(\"" + atributos.get(i).getNameJava() + "\", \"%\" + " + atributos.get(i).getNameJava() + " + \"%\").getResultList();\n"
                        + "    }\n");
            }

            cg.add("    public List<" + className + "> listInOrder" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "() {\n"
                    + "        return em.createQuery(\"SELECT e FROM " + classNameBD + " e ORDER BY e." + atributos.get(i).getNameBD() + "\").getResultList();\n"
                    + "    }\n");
        }

        // listInOrderString
        cg.add("    public List<String> listInOrderString(String order) {\n"
                + "        List<" + className + "> lf;");

        String orderString = "        ";
        for (int i = 0; i < atributos.size(); i++) {

            if (i != 0) {
                orderString += " else ";
            }

            orderString += "if (order.equals(\"" + atributos.get(i).getNameJava() + "\")) {\n"
                    + "            lf = listInOrder" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "();\n"
                    + "        }";

            if (i == atributos.size() - 1) {
                orderString += " else {\n"
                        + "            lf = listInOrder" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + "();\n"
                        + "        }";
            }
        }
        cg.add(orderString);

        cg.add("\n        List<String> ls = new ArrayList<>();\n"
                + "        for (int i = 0; i < lf.size(); i++) {\n"
                + "            ls.add(lf.get(i).get" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + "() + \"-\" + lf.get(i).get" + st.firstLetterToUpperCase(atributos.get(1).getNameJava()) + "());\n"
                + "        }\n"
                + "        return ls;\n"
                + "    }\n");

        cg.add("    public static void main(String[] args) {\n"
                + "        DAO" + className + " dao" + className + " = new DAO" + className + "();\n"
                + "        List<" + className + "> lista" + className + " = dao" + className + ".list();\n"
                + "        for (" + className + " " + classNameMin + " : lista" + className + ") {\n"
                + "            System.out.println(" + classNameMin + ");\n"
                + "        }\n"
                + "    }");

        cg.add("}");

        for (String line : cg) {
            System.out.println(line);
        }

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo("/home/joaoan2/NetBeansProjects/TesteGerador/src/DAOs/DAO" + className + ".java", cg);

    }

}
