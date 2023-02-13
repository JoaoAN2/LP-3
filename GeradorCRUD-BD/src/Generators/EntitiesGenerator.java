package Generators;

import Entidades.Attribute;
import Entidades.Config;
import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class EntitiesGenerator {

    public EntitiesGenerator(String className, List<Attribute> attributes, Table tableEntity, boolean entityPK, Config config) {

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> cg = new ArrayList(); // Código Gerado
        StringTools st = new StringTools();
        String path = config.getPath() + "/src/Entidades/" + className + ".java";
        ToStringGenerator tsg = new ToStringGenerator();
        if (manipulaArquivo.existeOArquivo(path)) {

            List<String> entitieFile = manipulaArquivo.abrirArquivo(path);
            for (int i = 0; i < entitieFile.size(); i++) {

                if (i > 0 && entitieFile.get(i - 1).trim().length() >= 7 && entitieFile.get(i - 1).trim().substring(0, 7).equals("package") && tableEntity.isHasDate()) {
                    cg.add("\nimport Tools.DateTools;");
                }

                if (i > 1 && entitieFile.get(i - 1).trim().length() >= 26 && entitieFile.get(i - 1).trim().substring(0, 26).equals("public String toString() {")) {

                    if (tableEntity.isHasDate()) {
                        cg.add("        DateTools dt = new DateTools();");
                    }
                       
                    String toString;
                    
                    if (entityPK) {
                        toString = tsg.generateToStringPK(attributes);
                    } else {
                        toString = tableEntity.isHasNxm()
                                ? tsg.generateToStringNxm(className, attributes)
                                : tsg.generateToString(attributes);
                    }

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
