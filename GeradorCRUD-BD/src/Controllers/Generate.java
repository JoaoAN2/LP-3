package Controllers;

import Entidades.Atribute;
import Entidades.JDBC;
import Entidades.Table;
import GUIs.GUI;
import Generator.GeradorDeDAO;
import Generator.GeradorDeEntidades;
import Generator.GeradorDeGUI;
import Generator.GeradorDeMenu;
import Tools.StringTools;
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

    public Generate(JDBC jdbc) {
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
                List<Atribute> atributes = new ArrayList();
                ResultSet rsDesc = con.createStatement().executeQuery("DESC " + tables.get(i).getTableNameBD());
                while (rsDesc.next()) {
                    Atribute atribute = new Atribute();
                    atribute.setNameJava(st.bdToJava(rsDesc.getString(1)));
                    atribute.setTypeJava(st.convertTypeBDToJava(rsDesc.getString(2)));
                    atribute.setNameBD(rsDesc.getString(1));
                    atribute.setSize(st.sizeAtributes(rsDesc.getString(2)));
                    atribute.setTypeBD(rsDesc.getString(2));
                    atribute.setIsNull(rsDesc.getString(3).equals("YES"));
                    atribute.setKey(rsDesc.getString(4));
                    atribute.setLabelName(st.labelJava(rsDesc.getString(1)));
                    atributes.add(atribute);
                }
                tables.get(i).setAtributes(atributes);

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
                        Atribute atributeFK = tableFK.getAtributeByName(rsFK.getString("column_name"));
                        atributeFK.setOriginTableFK(rsFK.getString("referenced_table_name"));
                        atributeFK.setOriginNameFK(rsFK.getString("referenced_column_name"));
                    } catch (Exception e) {
                        System.out.println("Algum erro");
                    }
                }
            }

            for (int i = 0; i < tables.size(); i++) {
                GeradorDeGUI geradorDeGUI = new GeradorDeGUI(tables.get(i));
                GeradorDeDAO geradorDeDAO = new GeradorDeDAO(st.firstLetterToUpperCase(tables.get(i).getTableNameJava()), tables.get(i).getAtributes(), tables.get(i).getTableNameBD());
                GeradorDeEntidades geradorDeEntidades = new GeradorDeEntidades(st.firstLetterToUpperCase(tables.get(i).getTableNameJava()), tables.get(i).getAtributes());
            }
            GeradorDeMenu geradorDeMenu = new GeradorDeMenu(tables, jdbc.getDataBaseName());
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
