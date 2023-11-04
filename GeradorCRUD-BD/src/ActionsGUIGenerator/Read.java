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

        if (!tableEntity.isHasNxm()) {
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

            if (tableEntity.isHasAttribute()) {
                cg.add("                " + className + "PK " + classNameMin + "PK = new " + className + "PK();");

                for (Attribute atributo : atributos) {
                    if (atributo.getKey().equals("PRI")) {
                        if (atributo.getOriginTableFK() == null) {
                            switch (atributo.getTypeJava()) {
                                case "String":
                                    cg.add("                " + classNameMin + "PK.set" + st.firstLetterToUpperCase(atributo.getNameJava()) + "(tf" + st.firstLetterToUpperCase(atributo.getNameJava()) + ".getText());");
                                    break;
                                case "int":
                                    cg.add("                " + classNameMin + "PK.set" + st.firstLetterToUpperCase(atributo.getNameJava()) + "(Integer.parseInt(tf" + st.firstLetterToUpperCase(atributo.getNameJava()) + ".getText()));");
                                    break;
                                default:
                                    cg.add("                " + classNameMin + "PK.set" + st.firstLetterToUpperCase(atributo.getNameJava()) + "(ErroDeTipagem.valueOf(tf" + st.firstLetterToUpperCase(atributo.getNameJava()) + "));");
                            }
                        } else {
                            cg.add("                " + classNameMin + "PK.set" + st.firstLetterToUpperCase(atributo.getNameJava()) + "(((" + st.firstLetterToUpperCase(atributo.getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(atributo.getOriginTableFK()) + ".getSelectedItem()).get" + st.bdToJava(st.firstLetterToUpperCase(atributo.getOriginNameFK())) + "());");
                        }
                    }
                }

                cg.add("                " + classNameMin + " = dao" + className + ".obter(" + classNameMin + "PK);");
            } else {
                cg.add("                " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxmPkOnly().getMainAttribute().getOriginNameFK())) + "());\n"
                        + "                " + tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK() + " = dao" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".obter(((" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ") cb" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".getSelectedItem()).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginNameFK())) + "());\n"
                        + "                List<" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + " = " + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + ".get" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + "List();"
                );
            }
        }

        cg.add(tableEntity.isHasAttribute()
                ? "\n                if (" + classNameMin + " != null) {\n"
                : "\n                if (" + tableEntity.getNxmPkOnly().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ".contains((" + st.firstLetterToUpperCase(tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK()) + ") " + tableEntity.getNxmPkOnly().getSecondAttribute().getOriginTableFK() + ")) {"
        );

        cg.add("                    btnCreate.setVisible(false);\n"
                + "                    btnDelete.setVisible(true);");

        if (tableEntity.isHasAttribute()) {
            cg.add("                    btnUpdate.setVisible(true);");

            for (Attribute attribute : atributos) {
                if (!attribute.getKey().equals("PRI")) {
                    if (attribute.getOriginTableFK() == null) {
                        switch (attribute.getTypeJava()) {
                            case "String":
                                cg.add("                    tf" + st.firstLetterToUpperCase(attribute.getNameJava()) + ".setText(" + classNameMin + ".get" + st.firstLetterToUpperCase(attribute.getNameJava()) + "());");
                                break;
                            case "Date":
                                cg.add("                    tf" + st.firstLetterToUpperCase(attribute.getNameJava()) + ".setText(dt.conversionDateToString(" + classNameMin + ".get" + st.firstLetterToUpperCase(attribute.getNameJava()) + "()));");
                                break;
                            default:
                                cg.add("                    tf" + st.firstLetterToUpperCase(attribute.getNameJava()) + ".setText(String.valueOf(" + classNameMin + ".get" + st.firstLetterToUpperCase(attribute.getNameJava()) + "()));");
                                break;
                        }
                    } else {
                        cg.add("                    cb" + st.firstLetterToUpperCase(st.bdToJava(attribute.getOriginTableFK())) + ".setSelectedItem(" + classNameMin + ".get" + st.firstLetterToUpperCase(attribute.getNameJava()) + "());");
                    }
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
