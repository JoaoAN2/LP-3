package ActionsGUIGenerator;

import Entidades.Attribute;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Update {
    
    StringTools st = new StringTools();
    
    public Update(List<String> cg, List<Attribute> atributos) {
        cg.add("       btnUpdate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "\n"
                + "                btnSearch.setVisible(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnSave.setVisible(true);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnList.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEnabled(false);\n"
                            + "                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(false);\n");
                } else {
                    cg.add("                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(false);\n"
                            + "                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEditable(false);\n");
                }
            }
        }

        cg.add("                enabled();\n"
                + "\n"
                + "                action = \"update\";"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
