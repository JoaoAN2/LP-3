package Entidades;

import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class Table {

    private String tableNameBD;
    private String tableNameJava;
    private List<Atribute> atributes;
    private boolean hasFK = false;
    private boolean hasDate = false;

    public Table() {
    }

    public Table(String tableNameBD, String tableNameJava, List<Atribute> atributes) {
        this.tableNameBD = tableNameBD;
        this.tableNameJava = tableNameJava;
        this.atributes = atributes;
    }

    public String getTableNameBD() {
        return tableNameBD;
    }

    public void setTableNameBD(String tableNameBD) {
        this.tableNameBD = tableNameBD;
    }

    public String getTableNameJava() {
        return tableNameJava;
    }

    public void setTableNameJava(String tableNameJava) {
        this.tableNameJava = tableNameJava;
    }

    public List<Atribute> getAtributes() {
        return atributes;
    }

    public void setAtributes(List<Atribute> atributes) {
        this.atributes = atributes;
    }

    public Atribute getAtributeByName(String atributeName) {
        for (int i = 0; i < this.atributes.size(); i++) {
            if (atributeName.equals(atributes.get(i).getNameBD())) {
                return atributes.get(i);
            }
        }
        return null;
    }

    public boolean isHasFK() {
        return hasFK;
    }

    public void setHasFK(boolean hasFK) {
        this.hasFK = hasFK;
    }

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    @Override
    public String toString() {
        return tableNameBD + ";" + tableNameJava + ";" + atributes;
    }

}
