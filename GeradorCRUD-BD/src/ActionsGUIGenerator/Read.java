/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ActionsGUIGenerator;

import Entidades.Atribute;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Read {

    StringTools st = new StringTools();
    
    public Read(List<String> cg, List<Atribute> atributos, String className, String classNameMin) {
        cg.add("        btnSearch.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                cardLayout.show(pnSouth, \"warning\");\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    switch (atributos.get(i).getTypeJava()) {
                        case "String":
                            cg.add("                " + classNameMin + " = dao" + className + ".obter(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".getText());");
                            break;
                        case "int":
                            cg.add("                " + classNameMin + " = dao" + className + ".obter(Integer.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".getText()));");
                            break;
                        default:
                            cg.add("                " + classNameMin + " = dao" + className + ".obter(ErroDeTipagem.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + "));");
                    }
                } else {
                    cg.add("                " + classNameMin + " = dao" + className + ".obter(((" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".getSelectedItem()).get" + st.bdToJava(st.firstLetterToUpperCase(atributos.get(i).getOriginNameFK())) + "());");
                }
            }
        }

        cg.add("\n                if (" + classNameMin + " != null) {\n"
                + "                    btnCreate.setVisible(false);\n"
                + "                    btnUpdate.setVisible(true);\n"
                + "                    btnDelete.setVisible(true);\n"
        );

        for (int i = 1; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() == null) {
                switch (atributos.get(i).getTypeJava()) {
                    case "String":
                        cg.add("                    tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setText(" + classNameMin + ".get" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "());");
                        break;
                    case "Date":
                        cg.add("                    tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setText(dt.conversionDateToString(" + classNameMin + ".get" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "()));");
                        break;
                    default:
                        cg.add("                    tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setText(String.valueOf(" + classNameMin + ".get" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "()));");
                        break;
                }
            } else {
                cg.add("                    cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ".setSelectedItem(" + classNameMin + ".get" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "());");
            }
        }

        cg.add("                } else {\n"
                + "                    clear();\n"
                + "                    btnCreate.setVisible(true);\n"
                + "                    btnUpdate.setVisible(false);\n"
                + "                    btnDelete.setVisible(false);\n"
                + "                }"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
