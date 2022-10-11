package Entidades;

import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class Table {

    private String tableNameBD;
    private String tableNameJava;
    private List<Attribute> attributes;
    private boolean hasFK = false;
    private boolean hasDate = false;
    private boolean hasAttribute = false;
    private NxM nxm;

    public Table() {
    }

    public Attribute getAttributeByName(String attributeName) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (attributeName.equals(attributes.get(i).getNameBD())) {
                return attributes.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return tableNameBD + ";" + tableNameJava + ";" + attributes;
    }

    public boolean isHasAttribute() {
        return hasAttribute;
    }

    public void setHasAttribute(boolean hasAttribute) {
        this.hasAttribute = hasAttribute;
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
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

    public NxM getNxm() {
        return nxm;
    }

    public void setNxm(NxM nxm) {
        this.nxm = nxm;
    }

}
