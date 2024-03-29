package Generators;

import Entidades.Attribute;
import Entidades.Config;
import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class DAOsGenerator {

    public DAOsGenerator(Table tableEntity, Config config) throws IOException {

        StringTools st = new StringTools();
        List<String> cg = new ArrayList();

        String className = st.firstLetterToUpperCase(tableEntity.getTableNameJava());
        String classNameMin = st.firstLetterToLowerCase(className);
        List<Attribute> atributos = tableEntity.getAttributes();

        // Package, Imports and public class
        cg.add("package DAOs;\n");

        if (tableEntity.isHasNxm() && tableEntity.isHasAttribute()) {
            cg.add("import Entidades." + className + "PK;");
        }

        cg.add("import Entidades." + className + ";\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n");

        cg.add("public class DAO" + className + " extends DAOGenerico<" + className + "> {\n");

        // Construtor
        cg.add("    public DAO" + className + "() {\n"
                + "        super(" + className + ".class);\n"
                + "    }\n");

        if (tableEntity.isHasNxm() && tableEntity.isHasAttribute()) {
            cg.add("    public " + className + " obter(" + className + "PK " + classNameMin + "PK){\n"
                    + "        return em.find(" + className + ".class, " + classNameMin + "PK);\n"
                    + "    }\n");
        }

        // AutoID
        if (atributos.get(0).getTypeJava().equals("int")) {
            cg.add("    public int autoId" + className + "() {\n"
                    + "        Integer a = (Integer) em.createQuery(\"SELECT MAX(e." + atributos.get(0).getNameJava() + ") FROM " + className + "e \").getSingleResult();\n"
                    + "        if(a != null) {\n"
                    + "            return a + 1;\n"
                    + "        } else {\n"
                    + "            return 1;\n"
                    + "        }\n"
                    + "    }");
        }

        // Lists and ListsInOrder
        for (Attribute atributo : atributos) {

            if (atributo.getTypeJava().equals("String")) {
                cg.add("    public List<" + className + "> listBy" + st.firstLetterToUpperCase(atributo.getNameJava()) + "(" + atributo.getTypeJava() + " " + st.firstLetterToLowerCase(atributo.getNameJava()) + ") {\n"
                        + "        return em.createQuery(\"SELECT e FROM " + className + " e WHERE e." + atributo.getNameJava() + " LIKE :" + atributo.getNameJava() + "\").setParameter(\"" + atributo.getNameJava() + "\", \"%\" + " + atributo.getNameJava() + " + \"%\").getResultList();\n"
                        + "    }\n");
            }

            cg.add("    public List<" + className + "> listInOrder" + st.firstLetterToUpperCase(atributo.getNameJava()) + "() {\n"
                    + "        return em.createQuery(\"SELECT e FROM " + className + " e ORDER BY e." + atributo.getNameJava() + "\").getResultList();\n"
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
                + "        for (int i = 0; i < lf.size(); i++) {");

        if (tableEntity.isHasNxm() && tableEntity.isHasAttribute()) {
            cg.add("            ls.add(lf.get(i).get" + className + "PK().toString());");
        } else {
            cg.add("            ls.add(lf.get(i).get" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + "() + \"-\" + lf.get(i).get" + st.firstLetterToUpperCase(atributos.get(1).getNameJava()) + "());");
        }

        cg.add("        }\n"
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
        manipulaArquivo.salvarArquivo(config.getPath() + "/src/DAOs/DAO" + className + ".java", cg);

    }

}
