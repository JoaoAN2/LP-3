package Tools;

// @author Radames
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/*
IMPORTANTE
Caso não seja informado um caminho completo
o arquivo será salvo na pasta atual
No caso, dentro do próprio projeto.
 */
public class ManipulaArquivo {

    public ManipulaArquivo() {
    }

    public boolean existeOArquivo(String caminhoENomeArquivo) {
        BufferedReader arquivoReader;
        File arq = new File(caminhoENomeArquivo);
        if (arq.exists()) {
            try {
                arquivoReader = new BufferedReader(new FileReader(caminhoENomeArquivo));
            } catch (Exception e) {
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean criarArquivoVazio(String caminhoENomeArquivo) {
        try {
            BufferedReader arquivoReader;
            FileWriter f = new FileWriter(caminhoENomeArquivo);
            f.close();
            try {
                arquivoReader = new BufferedReader(new FileReader(caminhoENomeArquivo));
            } catch (Exception e) {
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> abrirArquivo(String caminho) {
        List<String> texto = new ArrayList<String>();
        try {
            //OpenFile
            FileReader arquivo = new FileReader(caminho);
            BufferedReader conteudoDoArquivo = new BufferedReader(arquivo);
            String linha = conteudoDoArquivo.readLine();
            while (linha != null) {
                texto.add(linha);
                linha = conteudoDoArquivo.readLine();
            }
            conteudoDoArquivo.close();
        } catch (Exception e) {//Catch exception if any
            texto = null;
            System.err.println("Erro: " + e.getMessage());
        }
        return texto;
    }

    public int salvarArquivo(String caminho, List<String> texto) {
        try {
            // Create file 
            FileWriter arquivo = new FileWriter(caminho);
            BufferedWriter conteudoDoArquivo = new BufferedWriter(arquivo);
            for (int i = 0; i < texto.size(); i++) {
                conteudoDoArquivo.write(texto.get(i) + System.getProperty("line.separator")); // 
            }
            conteudoDoArquivo.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            return 1; //houve erro
        }
        return 0;
    }

    public void criarArquivoEDiretorio(String path) throws IOException {
        File dir = new File(path.substring(0, path.lastIndexOf("/") + 1));
        File file = new File(path.substring(path.lastIndexOf("/") + 1));
        criarDiretorio(dir);
        criarArquivo(file);
    }

    public void criarDiretorio(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void criarArquivo(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static void copyFile(File src, File dest) throws IOException {
        try (InputStream is = new FileInputStream(src);
                OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }
    }

    public void copiarArquivo(String originPathAndFile, String destinyPathAndFile) throws IOException {
        File originFile = new File(originPathAndFile);
        criarArquivoEDiretorio(destinyPathAndFile);
        File destinyFile = new File(destinyPathAndFile);

        try (InputStream is = new FileInputStream(originFile);
                OutputStream os = new FileOutputStream(destinyFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }
    }

    public void copiarDiretorio(File originDir, File destinyDIr) throws IOException {
        if (originDir.isDirectory()) {
            if (!destinyDIr.exists()) {
                destinyDIr.mkdir();
            }
            String[] children = originDir.list();
            for (int i = 0; i < children.length; i++) {
                copiarDiretorio(new File(originDir, children[i]),
                        new File(destinyDIr, children[i]));
            }
        } else {
            copyFile(originDir, destinyDIr);
        }
    }
}
