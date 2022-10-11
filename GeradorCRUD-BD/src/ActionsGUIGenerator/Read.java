package ActionsGUIGenerator;

import Entidades.Attribute;
import Entidades.Table;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Read {

    StringTools st = new StringTools();

    public Read(List<String> cg, List<Attribute> atributos, String className, String classNameMin, Table tableEntity) {

        cg.add("        btnSearch.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                cardLayout.show(pnSouth, \"warning\");\n");

        if (tableEntity.isHasAttribute()) {
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
        } else {
            cg.add("                " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getMainAttribute().getOriginNameFK())) + "());\n"
                    + "                " + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getSecondAttribute().getOriginNameFK())) + "());\n"
                    + "                List<" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + " = " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + ".get" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List();"
            );
        }

        cg.add(tableEntity.isHasAttribute()
                ? "\n                if (" + classNameMin + " != null) {\n"
                : "\n                if (" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ".contains((" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + ") " + tableEntity.getNxm().getSecondAttribute().getOriginTableFK() + ")) {"
        );

        cg.add("                    btnCreate.setVisible(false);\n"
                + "                    btnDelete.setVisible(true);");

        if (tableEntity.isHasAttribute()) {
            cg.add("                    btnUpdate.setVisible(true);");

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
        }

        cg.add("                } else {");

        if (tableEntity.isHasAttribute()) {
            cg.add("                    clear();\n"
                    + "                    btnUpdate.setVisible(false);\n");
        }

        cg.add("                    btnCreate.setVisible(true);\n"
                + "                    btnDelete.setVisible(false);\n"
                + "                }"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
