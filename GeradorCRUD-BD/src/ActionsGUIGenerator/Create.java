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
public class Create {
    
    StringTools st = new StringTools();

    public Create(List<String> cg, List<Atribute> atributos, int numberPK) {
         cg.add("       btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n");

        if (numberPK != atributos.size() && atributos.get(numberPK).getOriginTableFK() == null) {
            cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(numberPK).getNameJava()) + ".requestFocus();\n");
        }

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEnabled(false);");
                } else {
                    cg.add("                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(false);");
                }
            }
        }

        cg.add("                enabled();\n"
                + "\n"
                + "                btnSearch.setVisible(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnSave.setVisible(true);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnList.setVisible(false);\n"
                + "\n"
                + "                action = \"create\";"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }
    
}
