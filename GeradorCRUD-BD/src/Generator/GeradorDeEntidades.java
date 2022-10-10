package Generator;

import Entidades.Atribute;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class GeradorDeEntidades {

    public GeradorDeEntidades(String className, List<Atribute> atributes) {

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> cg = new ArrayList(); // Código Gerado
        StringTools st = new StringTools();
        String path = "/home/joaoan2/projects/LP-3/TesteGerador/src/Entidades/" + className + ".java";
        if (manipulaArquivo.existeOArquivo(path)) {

            boolean hasDate = false;
            for (int i = 0; i < atributes.size(); i++) {
                if (atributes.get(i).getTypeJava().equals("Date")) {
                    hasDate = true;
                    break;
                }
            }

            List<String> entitieFile = manipulaArquivo.abrirArquivo(path);
            for (int i = 0; i < entitieFile.size(); i++) {

                if (i > 0 && entitieFile.get(i - 1).trim().length() >= 7 && entitieFile.get(i - 1).trim().substring(0, 7).equals("package") && hasDate) {
                    cg.add("\nimport Tools.DateTools;");
                }

                if (i > 1 && entitieFile.get(i - 1).trim().length() >= 26 && entitieFile.get(i - 1).trim().substring(0, 26).equals("public String toString() {")) {

                    String toString = "        return ";

                    if (hasDate) {
                        cg.add("        DateTools dt = new DateTools();");
                    }

                    for (int j = 0; j < atributes.size(); j++) {

                        if (atributes.get(j).getTypeJava().equals("Date")) {
                            toString += "dt.conversionDateToString(";
                        }

                        if (atributes.get(j).getOriginTableFK() == null) {
                            toString += atributes.get(j).getNameJava();
                        } else {
                            toString += atributes.get(j).getNameJava() + ".get" + st.firstLetterToUpperCase(st.bdToJava(atributes.get(j).getOriginNameFK())) + "()";
                        }

                        if (atributes.get(j).getTypeJava().equals("Date")) {
                            toString += ")";
                        }

                        toString += " + \";\" + ";
                    }

                    toString = toString.substring(0, toString.length() - 9) + ";";

                    cg.add(toString);
                    cg.add("    }    \n"
                            + "}");
                    break;
                } else {
                    cg.add(entitieFile.get(i));
                }
            }

            for (String line : cg) {
                System.out.println(line);
            }

            manipulaArquivo.salvarArquivo(path, cg);
        }
    }

}
