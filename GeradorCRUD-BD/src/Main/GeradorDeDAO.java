package Main;

import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
class GeradorDeDAO {

    GeradorDeDAO(String className, List<String> atributos, String classNameBD, List<String> atributosBD) {
        StringTools stringTools = new StringTools();
        String classNameMin = stringTools.firstLetterToLowerCase(className);
        String[] aux;        
        String[] auxBD;
        List<String> cg = new ArrayList();
        String[] pk = atributos.get(0).split(";");

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
        if (pk[0].equals("int")) {
            cg.add("    public int autoId" + className + "() {\n"
                    + "        Integer a = (Integer) em.createQuery(\"SELECT MAX(e." + pk[1] + ") FROM " + classNameBD + "e \").getSingleResult();\n"
                    + "        if(a != null) {\n"
                    + "            return a + 1;\n"
                    + "        } else {\n"
                    + "            return 1;\n"
                    + "        }\n"
                    + "    }");
        }

        // Lists and ListsInOrder
        for (int i = 0; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");            
            auxBD = atributosBD.get(i).split(";");

            if (aux[0].equals("String")) {
                cg.add("    public List<" + className + "> listBy" + stringTools.firstLetterToUpperCase(aux[1]) + "(" + aux[0] + " " + stringTools.firstLetterToLowerCase(aux[1]) + ") {\n"
                        + "        return em.createQuery(\"SELECT e FROM " + classNameBD + " e WHERE e." + auxBD[0] + " LIKE :" + aux[1] + "\").setParameter(\"" + aux[1] + "\", \"%\" + " + aux[1] + " + \"%\").getResultList();\n"
                        + "    }\n");
            }

            cg.add("    public List<" + className + "> listInOrder" + stringTools.firstLetterToUpperCase(aux[1]) + "() {\n"
                    + "        return em.createQuery(\"SELECT e FROM " + classNameBD + " e ORDER BY e." + auxBD[0] + "\").getResultList();\n"
                    + "    }\n");
        }

        // listInOrderString
        cg.add("    public List<String> listInOrderString(String order) {\n"
                + "        List<" + className + "> lf;");

        String orderString = "        ";
        for (int i = 0; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");            

            if (i != 0) {
                orderString += " else ";
            }

            orderString += "if (order.equals(\"" + aux[1] + "\")) {\n"
                    + "            lf = listInOrder" + stringTools.firstLetterToUpperCase(aux[1]) + "();\n"
                    + "        }";

            if (i == atributos.size() - 1) {
                orderString += " else {\n"
                        + "            lf = listInOrder" + stringTools.firstLetterToUpperCase(pk[1]) + "();\n"
                        + "        }";
            }
        }
        cg.add(orderString);

        cg.add("\n        List<String> ls = new ArrayList<>();\n"
                + "        for (int i = 0; i < lf.size(); i++) {\n"
                + "            ls.add(lf.get(i).get" + stringTools.firstLetterToUpperCase(pk[1]) + "() + \"-\" + lf.get(i).get" + stringTools.firstLetterToUpperCase(atributos.get(1).split(";")[1]) + "());\n"
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
        manipulaArquivo.salvarArquivo("src/DAOs/DAO" + className + ".java", cg);

    }

}
