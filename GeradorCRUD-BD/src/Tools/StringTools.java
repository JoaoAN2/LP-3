package Tools;

/**
 *
 * @author JoaoAN
 */
public class StringTools {

    public String capitalize(String x) {
        String res = "";
        for (int i = 0; i < x.length(); i++) {
            if (i == 0) {
                res = res + x.toUpperCase().charAt(i);
            } else{
                res = res + x.toLowerCase().charAt(i);
            }
        }
        return res;
    }
    
    public String firstLetterToLowerCase(String x) {
        String res = "";
        for (int i = 0; i < x.length(); i++) {
            if (i == 0) {
                res = res + x.toLowerCase().charAt(i);
            } else{
                res = res + x.charAt(i);
            }
        }
        return res;
    }
    
    public String firstLetterToUpperCase(String x) {
        String res = "";
        for (int i = 0; i < x.length(); i++) {
            if (i == 0) {
                res = res + x.toUpperCase().charAt(i);
            } else{
                res = res + x.charAt(i);
            }
        }
        return res;
    }
    
    public String bdToJava(String x) {
        while (x.contains("_")) {
            int index = x.indexOf("_") + 1;
            x = x.substring(0, index - 1) + x.substring(index, index + 1).toUpperCase() + x.substring(index + 1);
        }
        return x;
    }
    
    public String convertTypeBDToJava(String typeBD) {
        String typeJava;
        if (typeBD.contains("varchar(")) {
            typeJava = "String";
        } else if(typeBD.equals("int") || typeBD.equals("double")) {
            typeJava = typeBD;
        } else {
            typeJava = firstLetterToUpperCase(typeBD);
        }
        return typeJava;
    }
    
    public int sizeAtributes(String type) {
        int size = 0;
        if (type.contains("varchar(")) {
            size =  Integer.valueOf(type.substring(type.indexOf("(") + 1, type.length() -1));
        } else if (type.equals("int")) {
            size = 11;
        } else if (type.equals("double")) {
            size = 20;
        } else if(type.equals("date")) {
            size = 10;
        }
        return size;
    }
}
