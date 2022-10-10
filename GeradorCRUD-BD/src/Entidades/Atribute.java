package Entidades;

/**
 *
 * @author JoaoAN2
 */
public class Atribute {

    private String nameJava;
    private String typeJava;
    private String nameBD;
    private String typeBD;
    private boolean isNull;
    private String key;
    private int size;
    private String originTableFK;
    private String originNameFK;
    private String labelName;

    public Atribute(String nameJava, String typeJava, String nameBD, String typeBD, boolean isNull, String key, int size, String originTableFK, String originNameFK, String labelName) {
        this.nameJava = nameJava;
        this.typeJava = typeJava;
        this.nameBD = nameBD;
        this.typeBD = typeBD;
        this.isNull = isNull;
        this.key = key;
        this.size = size;
        this.originTableFK = originTableFK;
        this.originNameFK = originNameFK;
        this.labelName = labelName;
    }

    public Atribute() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNameJava() {
        return nameJava;
    }

    public void setNameJava(String nameJava) {
        this.nameJava = nameJava;
    }

    public String getNameBD() {
        return nameBD;
    }

    public void setNameBD(String nameBD) {
        this.nameBD = nameBD;
    }

    public String getTypeBD() {
        return typeBD;
    }

    public void setTypeBD(String typeBD) {
        this.typeBD = typeBD;
    }

    public boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTypeJava() {
        return typeJava;
    }

    public void setTypeJava(String typeJava) {
        this.typeJava = typeJava;
    }

    public String getOriginTableFK() {
        return originTableFK;
    }

    public void setOriginTableFK(String originTableFK) {
        this.originTableFK = originTableFK;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getOriginNameFK() {
        return originNameFK;
    }

    public void setOriginNameFK(String originNameFK) {
        this.originNameFK = originNameFK;
    }

    @Override
    public String toString() {
        return nameJava + ";" + typeJava + ";" + nameBD + ";" + typeBD + ";" + isNull + ";" + key + ";" + size + ";" + originTableFK + ";" + originNameFK + ";" + labelName;
    }

}
