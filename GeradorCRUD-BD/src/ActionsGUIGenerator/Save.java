package ActionsGUIGenerator;

import Entidades.Attribute;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Save {
    
    StringTools st = new StringTools();
    
    public Save(List<String> cg, List<Attribute> atributos, String className, String classNameMin) {
        cg.add("       btnSave.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                if(\"create\".equals(action)) {\n"
                + "                    " + classNameMin + " =  new " + className + "();\n"
                + "                }\n"
        );

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() == null) {
                switch (atributos.get(i).getTypeJava()) {
                    case "String":
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText());");
                        break;
                    case "int":
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(Integer.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText()));");
                        break;
                    case "double":
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(Double.parseDouble(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText()));");
                        break;
                    case "Short":
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(Short.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText()));");
                        break;
                    case "Date":
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(dt.conversionStringToDate(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText()));");
                        break;
                    default:
                        cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(" + atributos.get(i).getTypeJava() + ".valueOf(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".getText()));");
                }
            } else {
                String pkCb = "                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(";
                pkCb += atributos.get(i).getKey().equals("PRI") ? "(" : "";
                pkCb += "(" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ")";
                pkCb += " cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ".getSelectedItem())";
                pkCb += atributos.get(i).getKey().equals("PRI") ? ".get" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginNameFK())) + "());" : ";";
                cg.add(pkCb);
            }
        }

        cg.add("\n                if(\"create\".equals(action)){\n"
                + "                    dao" + className + ".inserir(" + classNameMin + ");\n"
                + "                }");

        cg.add("\n                if(\"update\".equals(action)){\n"
                + "                    dao" + className + ".atualizar(" + classNameMin + ");\n"
                + "                }\n");

        cg.add("                btnSearch.setVisible(true);\n"
                + "                btnList.setVisible(true);\n"
                + "                btnSave.setVisible(false);\n"
                + "                btnCancel.setVisible(false);\n"
                + "                btnDelete.setVisible(false);\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEnabled(true);\n"
                            + "                tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(true);\n");
                } else {
                    cg.add("                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(true);\n"
                            + "                cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEditable(true);\n");
                }
            }
        }

        cg.add("                clear();\n"
                + "                disabled();\n"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
    }

}
