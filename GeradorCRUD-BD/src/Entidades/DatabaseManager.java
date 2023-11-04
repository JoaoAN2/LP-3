package Entidades;

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
public class DatabaseManager {

    StringTools st = new StringTools();

    private Connection con;
    private List<Table> tables;

    public DatabaseManager(Connection con) {
        this.con = con;
    }

    public void setTables() {
        List<Table> tables = new ArrayList();
        try {
            ResultSet rsTables = con.createStatement().executeQuery("SHOW TABLES;");
            while (rsTables.next()) {
                Table table = new Table();
                table.setTableNameBD(rsTables.getString(1));
                table.setTableNameJava(st.bdToJava(rsTables.getString(1)));
                tables.add(table);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadAttributes(tables);
        setForeignKeys(tables);
        
        this.tables = tables;
    }
    
    public List<Table> getTables() {
        if (this.tables == null) {
            setTables();
        }
        
        return tables;
    }
    
    public void loadAttributes(List<Table> tables) {
        for (Table table : tables) {
            List<Attribute> attributes = getAttributes(table.getTableNameBD());
            table.setAttributes(attributes);
        }
    }

    public List<Attribute> getAttributes(String tableNameBD) {
        List<Attribute> attributes = new ArrayList();

        try {
            ResultSet rsDesc = con.createStatement().executeQuery("DESC " + tableNameBD);

            while (rsDesc.next()) {

                Attribute attribute = new Attribute();

                attribute.setNameBD(rsDesc.getString(1));
                attribute.setTypeBD(rsDesc.getString(2));
                attribute.setIsNull(rsDesc.getString(3).equals("YES"));
                attribute.setKey(rsDesc.getString(4));

                attributes.add(attribute);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return attributes;
    }

    public Table getTableByName(String tableName, List<Table> tables) {
        for (Table table : tables) {
            if (table.getTableNameBD().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    public void setForeignKeys(List<Table> tables) {

        for (Table table : tables) {
            try {

                ResultSet rsFK = con.createStatement().executeQuery("SELECT\n"
                        + "   constraint_name,\n"
                        + "   column_name,\n"
                        + "   table_name,\n"
                        + "   referenced_table_name, \n"
                        + "   referenced_column_name\n"
                        + "\n"
                        + "FROM information_schema.KEY_COLUMN_USAGE\n"
                        + "WHERE REFERENCED_TABLE_NAME = '" + table.getTableNameBD() + "'");

                while (rsFK.next()) {

                    try {
                        Table tableFK = getTableByName(rsFK.getString("table_name"), tables);
                        Attribute attributeFK = tableFK.getAttributeByName(rsFK.getString("column_name"));
                        attributeFK.setOriginTableFK(rsFK.getString("referenced_table_name"));
                        attributeFK.setOriginNameFK(rsFK.getString("referenced_column_name"));
                        tableFK.setHasFK(true);
                    } catch (Exception e) {
                        System.out.println("Algum erro");
                    }

                }

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
