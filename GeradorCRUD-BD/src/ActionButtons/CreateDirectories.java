package ActionButtons;

import Entidades.Config;
import Tools.ManipulaArquivo;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author joaoan2
 */
public class CreateDirectories {

    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    
    public CreateDirectories(Config config) throws IOException {
        manipulaArquivo.criarDiretorio(new File(config.getPath() + "DAOs"));
        manipulaArquivo.criarDiretorio(new File(config.getPath() + "GUIs"));
        manipulaArquivo.criarDiretorio(new File(config.getPath() + "Tools"));
        manipulaArquivo.copiarArquivo("src/Tools/DateTools.java", config.getPath() + "Tools/DateTools.java");
        manipulaArquivo.copiarArquivo("src/Tools/StringTools.java", config.getPath() + "Tools/StringTools.java");
    }
    
}
