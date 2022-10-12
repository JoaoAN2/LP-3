package ActionsGUIGenerator;

import Entidades.Attribute;
import Entidades.Table;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Create {

    StringTools st = new StringTools();

    public Create(List<String> cg, List<Attribute> atributos, Table tableEntity) {
        cg.add("       btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n");

        if (tableEntity.getNumberPk() != atributos.size() && atributos.get(tableEntity.getNumberPk()).getOriginTableFK() == null) {
            cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(tableEntity.getNumberPk()).getNameJava()) + ".requestFocus();\n");
        }

        if (tableEntity.isHasAttribute()) {
            for (int i = 0; i < atributos.size(); i++) {
                if (atributos.get(i).getKey().equals("PRI")) {
                    if (atributos.get(i).getOriginTableFK() == null) {
                        cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEnabled(false);");
                    } else {
                        cg.add("                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(false);");
                    }
                }
            }
        }

        cg.add("                btnCreate.setVisible(false);\n"
                + "                action = \"create\";");

        cg.add(tableEntity.isHasAttribute()
                ? "                enabled();\n"
                + "                btnSearch.setVisible(false);\n"
                + "                btnList.setVisible(false);\n"
                + "                btnSave.setVisible(true);\n"
                + "                btnCancel.setVisible(true);\n"
                + "\n"
                : "                btnSearch.setVisible(true);\n"
                + "                btnList.setVisible(true);\n"
                + "                " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxmPkOnly().getMainAttribute().getOriginNameFK())) + "());\n"
                + "                " + tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginNameFK())) + "());\n"
                + "                List<" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + " = " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + ".get" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + "List();\n"
                + "                " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".add(" + tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK() + ");\n"
                + "                " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + ".set" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + "List(" + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ");\n"
                + "                dao" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ".atualizar(" + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + ");"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
