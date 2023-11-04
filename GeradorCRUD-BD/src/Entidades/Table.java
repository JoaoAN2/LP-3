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
    private NxmPkOnly nxmPkOnly;
    private int numberPk = 0;
    private int numberAttributes = 0;
    private boolean hasFK = false;
    private boolean hasDate = false;
    private boolean hasAttribute = false;
    private boolean hasNxm = false;

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

        for (Attribute attribute : attributes) {

            if (attribute.getTypeJava().equals("Date")) {
                setHasDate(true);
            }

            if (attribute.getKey().equals("PRI")) {
                setNumberPk(getNumberPk() + 1);
            } else {
                setNumberAttributes(getNumberAttributes() + 1);
            }
            
        }

        setHasNxm(this.numberPk > 1);
        setHasAttribute(this.numberAttributes > 0);
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

    public NxmPkOnly getNxmPkOnly() {
        return nxmPkOnly;
    }

    public void setNxmPkOnly(NxmPkOnly nxmPkOnly) {
        this.nxmPkOnly = nxmPkOnly;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNameBD=" + tableNameBD + ", tableNameJava=" + tableNameJava + ", attributes=" + attributes + ", nxmPkOnly=" + nxmPkOnly + ", numberPk=" + numberPk + ", numberAttributes=" + numberAttributes + ", hasFK=" + hasFK + ", hasDate=" + hasDate + ", hasAttribute=" + hasAttribute + ", hasNxm=" + hasNxm + '}';
    }

    public int getNumberPk() {
        return numberPk;
    }

    public void setNumberPk(int numberPk) {
        this.numberPk = numberPk;
    }

    public boolean isHasNxm() {
        return hasNxm;
    }

    public void setHasNxm(boolean hasNxm) {
        this.hasNxm = hasNxm;
    }

    public int getNumberAttributes() {
        return numberAttributes;
    }

    public void setNumberAttributes(int numberAttributes) {
        this.numberAttributes = numberAttributes;
    }

}
