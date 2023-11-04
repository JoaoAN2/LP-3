package ActionButtons;

import Entidades.Attribute;
import Entidades.Config;
import Entidades.DatabaseManager;
import Entidades.JDBC;
import Entidades.Table;
import Generators.DAOsGenerator;
import Generators.EntitiesGenerator;
import Generators.GUIsGenerator;
import Generators.MenuGenerator;
import Generators.UPGenerator;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class Generate {

    public Generate(JDBC jdbc, Config config) throws IOException {
        StringTools st = new StringTools();
        DatabaseManager dbManager = new DatabaseManager(jdbc.getConnection());
        List<Table> tables = dbManager.getTables();

        CreateDirectories createDirectories = new CreateDirectories(config);

        for (Table table : tables) {
            
            GUIsGenerator guiGenerator = new GUIsGenerator(table, config);

            if (table.isHasAttribute()) {
                DAOsGenerator daoGenerator = new DAOsGenerator(table, config);
                EntitiesGenerator entityGenerator = new EntitiesGenerator(st.firstLetterToUpperCase(table.getTableNameJava()), table.getAttributes(), table, false, config);
                if (table.isHasNxm()) {
                    EntitiesGenerator entityGeneratorPK = new EntitiesGenerator(st.firstLetterToUpperCase(table.getTableNameJava()) + "PK", table.getAttributes(), table, true, config);
                }
            }
        }

        MenuGenerator geradorDeMenu = new MenuGenerator(tables, jdbc.getDataBaseName(), config);
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();

        manipulaArquivo.copiarArquivo("src/DAOs/DAOGenerico.java", config.getPath() + "/src/DAOs/DAOGenerico.java");
        UPGenerator upGenerator = new UPGenerator(tables, jdbc, config);
    }
}
