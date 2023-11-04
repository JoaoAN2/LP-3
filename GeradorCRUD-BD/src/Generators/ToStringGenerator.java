package Generators;

import Entidades.Attribute;
import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class ToStringGenerator {

    StringTools st = new StringTools();
    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();

    public String generateToStringNxm(String className, List<Attribute> attributes) {
        String toString = "        return " + st.firstLetterToLowerCase(className) + "PK + \";\" + ";
        for (Attribute attribute : attributes) {

            if (attribute.getTypeJava().equals("Date")) {
                toString += "dt.conversionDateToString(";
            }

            if (!attribute.getKey().equals("PRI")) {
                toString += attribute.getNameJava();
            }

            if (attribute.getTypeJava().equals("Date")) {
                toString += ")";
            }
            
            if (!attribute.getKey().equals("PRI")) {
                toString += " + \";\" + ";
            }
        }
        return toString.substring(0, toString.length() - 9) + ";";
    }

    public String generateToString(List<Attribute> attributes) {
        String toString = "        return ";
        for (Attribute attribute : attributes) {

            if (attribute.getTypeJava().equals("Date")) {
                toString += "dt.conversionDateToString(";
            }

            if (attribute.getOriginTableFK() == null || (attribute.getOriginTableFK() != null && attribute.getKey().equals("PRI"))) {
                toString += attribute.getNameJava();
            } else {
                toString += attribute.getNameJava() + ".get" + st.firstLetterToUpperCase(st.bdToJava(attribute.getOriginNameFK())) + "()";
            }

            if (attribute.getTypeJava().equals("Date")) {
                toString += ")";
            }
            toString += " + \";\" + ";
        }
        return toString.substring(0, toString.length() - 9) + ";";
    }

    public String generateToStringPK(List<Attribute> attributes) {
        String toString = "        return ";
        for (Attribute attribute : attributes) {

            if (attribute.getTypeJava().equals("Date") && attribute.getKey().equals("PRI")) {
                toString += "dt.conversionDateToString(";
            }

            if (attribute.getKey().equals("PRI")) {
                toString += attribute.getNameJava() + " + \";\" + ";
            }

            if (attribute.getTypeJava().equals("Date") && attribute.getKey().equals("PRI")) {
                toString += ")";
            }
        }
        return toString.substring(0, toString.length() - 9) + ";";
    }

}
