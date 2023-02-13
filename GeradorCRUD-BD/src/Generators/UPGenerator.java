package Generators;

import Entidades.Config;
import Entidades.JDBC;
import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class UPGenerator {

    public UPGenerator(JDBC jdbc, Config config) throws IOException {
        List<String> cg = new ArrayList();
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        StringTools st = new StringTools();
        cg.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<persistence version=\"2.1\" xmlns=\"http://xmlns.jcp.org/xml/ns/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd\">\n"
                + "  <persistence-unit name=\"UP\" transaction-type=\"RESOURCE_LOCAL\">\n"
                + "    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>");

        for (Table table : jdbc.getTables()) {
            if (table.isHasAttribute()) {
                cg.add("    <class>Entidades." + st.firstLetterToUpperCase(table.getTableNameJava()) + "</class>");
            }
        }

        cg.add("    <properties>\n"
                + "      <property name=\"javax.persistence.jdbc.url\" value=\"" + jdbc.getUrl() + "?zeroDateTimeBehavior=CONVERT_TO_NULL\"/>\n"
                + "      <property name=\"javax.persistence.jdbc.user\" value=\"" + jdbc.getUserName() + "\"/>\n"
                + "      <property name=\"javax.persistence.jdbc.driver\" value=\"" + jdbc.getJdbcDriver() + "\"/>\n"
                + "      <property name=\"javax.persistence.jdbc.password\" value=\"" + jdbc.getPassword() + "\"/>\n"
                + "    </properties>\n"
                + "  </persistence-unit>\n"
                + "</persistence>");

        for (String line : cg) {
            System.out.println(line);
        }

        manipulaArquivo.criarArquivoEDiretorio(config.getPath() + "/src/META-INF/persistence.xml");
        manipulaArquivo.salvarArquivo(config.getPath() + "/src/META-INF/persistence.xml", cg);

        System.out.println(config.getPath() + "/src/META-INF/persistence.xml");
    }

}
