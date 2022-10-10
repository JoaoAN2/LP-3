package ActionsGUIGenerator;

import Entidades.Atribute;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Delete {
    StringTools st = new StringTools();
    
    public Delete(List<String> cg, List<Atribute> atributos, String className, String classNameMin) {
        cg.add("       btnDelete.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "\n"
                + "                int response = JOptionPane.showConfirmDialog(\n"
                + "                        cp,\n"
                + "                        \"Tem certeza que deseja excluir?\",\n"
                + "                        \"Confirmar\",\n"
                + "                        JOptionPane.YES_NO_OPTION,\n"
                + "                        JOptionPane.QUESTION_MESSAGE\n"
                + "                );"
                + "\n");

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

        cg.add("                clear();\n"
                + "                disabled();\n"
                + "                btnDelete.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n"
                + "                btnCancel.setVisible(false);\n"
                + "                btnSearch.setVisible(true);\n"
                + "                if(response == JOptionPane.YES_OPTION) {\n"
                + "                    dao" + className + ".remover(" + classNameMin + ");\n"
                + "                }\n"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
