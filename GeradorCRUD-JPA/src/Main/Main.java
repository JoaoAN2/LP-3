package Main;
import Tools.ManipulaArquivo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "src/Entidades/Federation.java";
        String nameFile = path.split("/")[path.split("/").length - 1];
        List<String> atributos = new ArrayList();
        List<String> atributosBD = new ArrayList();
        String classNameBD = "";

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> entitieFile = manipulaArquivo.abrirArquivo(path);
        String className = nameFile.substring(0, nameFile.length() - 5);
        
        List<String> cgEntitie = new ArrayList(); // Entidade
        
        int cont = 0;

        for (int i = 0; i < entitieFile.size(); i++) {

            if (i > 1 && entitieFile.get(i - 1).trim().length() >= 26 && entitieFile.get(i - 1).trim().substring(0,26).equals("public String toString() {")) {
                
                String toString = "        return ";
                
                for (int j = 0; j < atributos.size(); j++) {
                    toString += atributos.get(j).split(";")[1] + " + \";\" + ";
                }
                
                toString = toString.substring(0, toString.length() - 9) + ";";
                
                cgEntitie.add(toString);
            } else {
                cgEntitie.add(entitieFile.get(i));
            }
            
            if (entitieFile.get(i).trim().length() >= 6 && entitieFile.get(i).trim().substring(0, 6).equals("@Table")) {
                classNameBD = entitieFile.get(i).trim().substring(15, entitieFile.get(i).trim().length() - 2);
            }
            
            if (entitieFile.get(i).trim().length() >= 7 && entitieFile.get(i).trim().substring(0, 7).equals("@Column")) {
                
                atributosBD.add(entitieFile.get(i).trim().substring(16, entitieFile.get(i).trim().length() - 2));
                
                String nextLine = entitieFile.get(i + 1).trim();
                String atributo = nextLine.substring(8, nextLine.length() - 1).replaceAll(" ", ";");
                if(cont != 0){
                    String aux[] = atributo.split(";");switch (aux[0]){
                        case "String":
                            atributo += ";255;";
                            break;
                        default:
                            atributo += ";11;";
                    }
                } else {
                    cont++;
                    nextLine = entitieFile.get(i + 1).trim();
                    atributo += ";11;";
                }
                atributos.add(atributo);
            } 
        }
        
        for(String line: cgEntitie) {
            System.out.println(line);
        }
        
        manipulaArquivo.salvarArquivo(path, cgEntitie);
        
        GeradorDeGUI geradorDeGUI = new GeradorDeGUI(className, atributos);
        GeradorDeDAO geradorDeDAO = new GeradorDeDAO(className, atributos, classNameBD, atributosBD);
        
    }
    
}
