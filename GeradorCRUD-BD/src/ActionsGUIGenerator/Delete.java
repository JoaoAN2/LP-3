package ActionsGUIGenerator;

import Entidades.Attribute;
import Entidades.Table;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Delete {

    StringTools st = new StringTools();

    public Delete(List<String> cg, List<Attribute> atributos, String className, String classNameMin, Table tableEntity) {
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

        if (tableEntity.isHasAttribute()) {
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
        }

        cg.add("                btnDelete.setVisible(false);\n"
                + "                btnSearch.setVisible(true);\n"
        );

        if (tableEntity.isHasAttribute()) {
            cg.add("                clear();\n"
                    + "                disabled();\n"
                    + "                btnUpdate.setVisible(false);\n"
                    + "                btnCancel.setVisible(false);\n"
            );
        }

        cg.add("                if(response == JOptionPane.YES_OPTION) {\n");

            cg.add(tableEntity.isHasAttribute()
                    ? "                    dao" + className + ".remover(" + classNameMin + ");\n"
                    : "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getMainAttribute().getOriginNameFK())) + "());\n"
                    + "                " + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getSecondAttribute().getOriginNameFK())) + "());\n"
                    + "                List<" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + " = " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ".get" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List();\n"
                    + "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".remove(" + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + ");\n"
                    + "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ".set" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List(" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ");\n"
                    + "                dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".atualizar(" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ");"
            );

        cg.add("                }\n");
        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
