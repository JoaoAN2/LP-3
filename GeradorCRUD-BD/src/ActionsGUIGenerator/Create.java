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

    public Create(List<String> cg, List<Attribute> atributos, int numberPK, Table tableEntity) {
        cg.add("       btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n");

        if (numberPK != atributos.size() && atributos.get(numberPK).getOriginTableFK() == null) {
            cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(numberPK).getNameJava()) + ".requestFocus();\n");
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
                + "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getMainAttribute().getOriginNameFK())) + "());\n"
                + "                " + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getSecondAttribute().getOriginNameFK())) + "());\n"
                + "                List<" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + " = " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ".get" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List();\n"
                + "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".add(" + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + ");\n"
                + "                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ".set" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List(" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ");\n"
                + "                dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".atualizar(" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ");"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
