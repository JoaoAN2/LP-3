package Tools;

/**
 *
 * @author JoaoAN2
 */
public class Testes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String teste = "VARCHAR(255)";
        int size = 0;
        if (teste.indexOf("VARCHAR(") != -1) {
            System.out.println(teste.indexOf("("));
            System.out.println(teste.substring(0, teste.indexOf("(")));
        }
    }
    
}
