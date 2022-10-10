package Tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zorawski
 */
public class LerJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /**
         * List<String> atributos = new ArrayList(); // Lista de atributos.
         * String className = "Cargo"; // Nome da classe.
         *
         * // Atributos da classe. atributos.add("int;idFunction;11");
         * atributos.add("String;nome_functionl;45");
         *
         * @Column(name = "sigla_federation") 
         * private String siglaFederation;
         *
         *
         */
        String path = "src/Entidades/Federation.java";
        String nameFile = path.split("/")[path.split("/").length - 1];
        List<String> atributos = new ArrayList();

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> list = manipulaArquivo.abrirArquivo(path);
        
        int cont = 0;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).trim().length() >= 7 && list.get(i).trim().substring(0, 7).equals("@Column") && cont != 0) {
                String nextLine = list.get(i + 1).trim();
                String atributo = nextLine.substring(8, nextLine.length() - 1).replaceAll(" ", ";");
                
                String aux[] = atributo.split(";");
                
                switch (aux[0]){
                    case "String":
                        atributo += ";255";
                        break;
                    default:
                        atributo += ";11";
                }
                
                atributos.add(atributo);
            } else if (list.get(i).trim().length() >= 7 && list.get(i).trim().substring(0, 7).equals("@Column") && cont == 0) {
                cont++;
                String nextLine = list.get(i + 1).trim();
                String atributo = nextLine.substring(8, nextLine.length() - 1).replaceAll(" ", ";") + ";11";
                atributos.add(atributo);
            }
        }

        for (String lineAtributes : atributos) {
            System.out.println(lineAtributes);
        }

    }

}
