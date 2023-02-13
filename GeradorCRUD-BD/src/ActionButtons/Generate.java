package ActionButtons;

import Entidades.Attribute;
import Entidades.Config;
import Entidades.JDBC;
import Entidades.Table;
import GUIs.GUI;
import Generators.DAOsGenerator;
import Generators.EntitiesGenerator;
import Generators.GUIsGenerator;
import Generators.MenuGenerator;
import Generators.UPGenerator;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoaoAN2
 */
public class Generate {

    public Generate(JDBC jdbc, Config config) throws IOException {
        StringTools st = new StringTools();
        Connection con = jdbc.getConnection();
        try {
            List<Table> tables = new ArrayList();
            ResultSet rsTables = con.createStatement().executeQuery("SHOW TABLES;");

            while (rsTables.next()) {
                Table table = new Table();
                table.setTableNameBD(rsTables.getString(1));
                table.setTableNameJava(st.bdToJava(rsTables.getString(1)));
                tables.add(table);
                jdbc.setTables(tables);
            }

            for (int i = 0; i < tables.size(); i++) {
                List<Attribute> attributes = new ArrayList();
                ResultSet rsDesc = con.createStatement().executeQuery("DESC " + tables.get(i).getTableNameBD());
                while (rsDesc.next()) {
                    Attribute attribute = new Attribute();
                    attribute.setNameJava(st.bdToJava(rsDesc.getString(1)));
                    attribute.setTypeJava(st.convertTypeBDToJava(rsDesc.getString(2)));
                    attribute.setNameBD(rsDesc.getString(1));
                    attribute.setSize(st.sizeAttributes(rsDesc.getString(2)));
                    attribute.setTypeBD(rsDesc.getString(2));
                    attribute.setIsNull(rsDesc.getString(3).equals("YES"));
                    attribute.setKey(rsDesc.getString(4));
                    attribute.setLabelName(st.labelJava(rsDesc.getString(1)));
                    attributes.add(attribute);
                }
                tables.get(i).setAttributes(attributes);

            }

            for (int i = 0; i < tables.size(); i++) {
                ResultSet rsFK = con.createStatement().executeQuery("SELECT\n"
                        + "   constraint_name,\n"
                        + "   column_name,\n"
                        + "   table_name,\n"
                        + "   referenced_table_name, \n"
                        + "   referenced_column_name\n"
                        + "\n"
                        + "FROM information_schema.KEY_COLUMN_USAGE\n"
                        + "WHERE REFERENCED_TABLE_NAME = '" + tables.get(i).getTableNameBD() + "'");
                while (rsFK.next()) {
                    try {
                        Table tableFK = jdbc.getTableByName(rsFK.getString("table_name"));
                        Attribute attributeFK = tableFK.getAttributeByName(rsFK.getString("column_name"));
                        attributeFK.setOriginTableFK(rsFK.getString("referenced_table_name"));
                        attributeFK.setOriginNameFK(rsFK.getString("referenced_column_name"));
                        tableFK.setHasFK(true);
                    } catch (Exception e) {
                        System.out.println("Algum erro");
                    }
                }
            }

            CreateDirectories createDirectories = new CreateDirectories(config);

            for (Table table : tables) {
                GUIsGenerator geradorDeGUI = new GUIsGenerator(table, config);

                if (table.isHasAttribute()) {
                    DAOsGenerator geradorDeDAO = new DAOsGenerator(table, config);
                    EntitiesGenerator geradorDeEntidades = new EntitiesGenerator(st.firstLetterToUpperCase(table.getTableNameJava()), table.getAttributes(), table, false, config);
                    if (table.isHasNxm()) {
                        EntitiesGenerator geradorDeEntidadesPK = new EntitiesGenerator(st.firstLetterToUpperCase(table.getTableNameJava()) + "PK", table.getAttributes(), table, true, config);
                    }
                }
            }

            MenuGenerator geradorDeMenu = new MenuGenerator(tables, jdbc.getDataBaseName(), config);
            ManipulaArquivo manipulaArquivo = new ManipulaArquivo();

            manipulaArquivo.copiarArquivo("src/DAOs/DAOGenerico.java", config.getPath() + "/src/DAOs/DAOGenerico.java");
            UPGenerator upGenerator = new UPGenerator(jdbc, config);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
