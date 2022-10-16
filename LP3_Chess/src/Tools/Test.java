/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Tools;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author joaoan2
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String string = "teste.js";
        int positionLastBar = string.lastIndexOf("/");
        System.out.println(positionLastBar);
        System.out.println(string.substring(0, string.lastIndexOf("/") + 1));
        System.out.println(string.substring(string.lastIndexOf("/") + 1));
        
        File file = new File("src/Tools/teste.java");
        file.createNewFile();
    }
    
}
