package Entidades;

/**
 *
 * @author joaoan2
 */
public class NxmPkOnly {

    private Attribute mainAttribute;
    private Attribute secondAttribute;

    public NxmPkOnly() {
    }

    public void setMainAttribute(Attribute mainAttribute) {
        this.mainAttribute = mainAttribute;
    }

    public void setSecondAttribute(Attribute secondAttribute) {
        this.secondAttribute = secondAttribute;
    }

    public Attribute getMainAttribute() {
        return mainAttribute;
    }

    public Attribute getSecondAttribute() {
        return secondAttribute;
    }

    public void setMainAndSecondAttribute(Attribute a, Attribute b) {
        this.mainAttribute = a.getNameBD().compareTo(b.getNameBD()) < 0
                ? a
                : b;
        this.secondAttribute = a.getNameBD().compareTo(b.getNameBD()) < 0
                ? b
                : a;
    }

    @Override
    public String toString() {
        return mainAttribute + ";" + secondAttribute;
    }
}
