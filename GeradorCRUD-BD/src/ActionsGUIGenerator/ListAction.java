package ActionsGUIGenerator;

import java.util.List;

/**
 *
 * @author joaoan2
 */
public class ListAction {

    public ListAction(List<String> cg, String parametroColunas, String className, String classNameMin) {
        cg.add("       btnList.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "\n"
                + "                List<" + className + "> " + classNameMin + "List = dao" + className + ".list();\n"
                + "                String[] col = {" + parametroColunas + "};\n"
                + "                Object[][] data = new Object[" + classNameMin + "List.size()][col.length];\n"
                + "                String aux[];\n\n"
                + "                for (int i = 0; i < " + classNameMin + "List.size(); i++) {\n"
                + "                    aux = " + classNameMin + "List.get(i).toString().split(\";\");\n"
                + "                    for (int j = 0; j < col.length; j++) {\n"
                + "                        data[i][j] = aux[j];\n"
                + "                    }\n"
                + "                }\n\n"
                + "                cardLayout.show(pnSouth, \"list\");\n\n"
                + "                scrollTable.setPreferredSize(table.getPreferredSize());\n"
                + "                pnList.add(table);\n"
                + "                pnList.add(scrollTable);\n"
                + "                scrollTable.setViewportView(table);\n"
                + "                model.setDataVector(data, col);\n"
                + "\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n"
                + "                btnDelete.setVisible(false);\n"
                + "            }\n"
                + "        });\n"
        );
    }

}
