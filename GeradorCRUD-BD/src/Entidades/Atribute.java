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

    public Atribute(String nameJava, String typeJava, String nameBD, String typeBD, boolean isNull, String key, int size) {
        this.nameJava = nameJava;
        this.typeJava = typeJava;
        this.nameBD = nameBD;
        this.typeBD = typeBD;
        this.isNull = isNull;
        this.key = key;
        this.size = size;
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
    
    @Override
    public String toString() {
        return nameJava + ";" + typeJava + ";" + nameBD + ";" + typeBD + ";" + isNull + ";" + key + ";" + size + ";" + originTableFK;
    }

}
