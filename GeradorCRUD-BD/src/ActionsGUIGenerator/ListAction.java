package ActionsGUIGenerator;

import Entidades.Table;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class ListAction {

    StringTools st = new StringTools();

    public ListAction(List<String> cg, String parametroColunas, String className, String classNameMin, Table tableEntity) {
        cg.add("       btnList.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
        );

        cg.add("                List<" + (tableEntity.isHasAttribute() ? className : st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK())) + "> " + (tableEntity.isHasAttribute() ? classNameMin : tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + "List = dao" + (tableEntity.isHasAttribute() ? className : st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK())) + ".list();\n"
                + "                String[] col = {" + parametroColunas + "};\n"
                + "                Object[][] data = new Object[" + (tableEntity.isHasAttribute() ? classNameMin : tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + "List.size()][col.length];\n");

        cg.add(tableEntity.isHasAttribute()
                ? "                String aux[];\n\n"
                + "                for (int i = 0; i < " + classNameMin + "List.size(); i++) {\n"
                + "                    aux = " + classNameMin + "List.get(i).toString().split(\";\");\n"
                + "                    for (int j = 0; j < col.length; j++) {\n"
                + "                        try {\n"
                + "                            data[i][j] = aux[j] == null ? \"null\" : aux[j];;\n"
                + "                        } catch (Exception e) {\n"
                + "                            data[i][j] = \"null\";\n"
                + "                        }\n"
                + "                    }\n"
                + "                }"
                : "                for (" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + " " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Aux : " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "List) {\n"
                + "                    List<" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "> " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "s = dao" + st.firstLetterToUpperCase(tableEntity.getNxm().getMainAttribute().getOriginTableFK()) + ".obter(" + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Aux.getPlayerIdPlayer()).get" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "List();\n"
                + "                    for (int i = 0; i < " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "s.size(); i++) {\n"
                + "                        data[i][0] = " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Has" + st.firstLetterToUpperCase(tableEntity.getNxm().getSecondAttribute().getOriginTableFK()) + "s.get(i).get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getSecondAttribute().getOriginNameFK())) + "();\n"
                + "                        data[i][1] = " + tableEntity.getNxm().getMainAttribute().getOriginTableFK() + "Aux.get" + st.firstLetterToUpperCase(st.bdToJava(tableEntity.getNxm().getMainAttribute().getOriginNameFK())) + "();\n"
                + "                    }\n"
                + "                }");

        cg.add("                cardLayout.show(pnSouth, \"list\");\n\n"
                + "                scrollTable.setPreferredSize(table.getPreferredSize());\n"
                + "                pnList.add(table);\n"
                + "                pnList.add(scrollTable);\n"
                + "                scrollTable.setViewportView(table);\n"
                + "                model.setDataVector(data, col);\n"
                + "\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnDelete.setVisible(false);\n");

        if (tableEntity.isHasAttribute()) {
            cg.add("                btnUpdate.setVisible(false);\n");
        }

        cg.add("            }\n"
                + "        });\n"
        );
    }

}
