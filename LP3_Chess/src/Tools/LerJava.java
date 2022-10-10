package Tools;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class LerJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String path = "src/Entidades/Federation.java";
        String nameFile = path.split("/")[path.split("/").length - 1];
        List<String> atributos = new ArrayList();

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> list = manipulaArquivo.abrirArquivo(path);

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).trim().length() >= 7 && list.get(i).trim().substring(0, 7).equals("@Column")) {
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
            }
        }

        for (String lineAtributes : atributos) {
            System.out.println(lineAtributes);
        }

    }

}
