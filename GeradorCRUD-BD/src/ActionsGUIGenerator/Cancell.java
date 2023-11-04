package ActionsGUIGenerator;

import Entidades.Attribute;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Cancell {

    StringTools st = new StringTools();
    
    public Cancell(List<String> cg, List<Attribute> atributos) {
        cg.add("        btnCancel.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEnabled(true);\n"
                            + "                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(true);\n"
                            + "                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setText(\"\");\n");
                } else {
                    cg.add("                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(true);\n"
                            + "                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEditable(true);\n");
                }
            }
        }

        cg.add("                disabled();\n"
                + "                clear();\n"
                + "\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n"
                + "                btnDelete.setVisible(false);\n"
                + "                btnCancel.setVisible(false);\n"
                + "                btnSave.setVisible(false);\n"
                + "                btnSearch.setVisible(true);\n"
                + "                btnList.setVisible(true);\n"
                + "            }\n"
                + "        });\n");
    }
}
