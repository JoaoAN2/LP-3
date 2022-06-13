package Geradores;

import Entidades.Atribute;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JoaoAN2
 */
public class GeradorDeGUI {

    public GeradorDeGUI(String className, List<Atribute> atributos) {
        StringTools st = new StringTools();
        String classNameMin = st.firstLetterToLowerCase(className);
        List<String> cg = new ArrayList(); // Código gerado
        boolean hasDate = false;
        boolean hasFK = false;

        cg.add("package GUIs;\n");

        // Imports
        cg.add("import Entidades." + className + ";\n"
                + "import DAOs.DAO" + className + ";\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() != null) {
                cg.add("import Entidades." + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ";\n"
                        + "import DAOs.DAO" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ";\n");
                hasFK = true;
            }
            if (atributos.get(i).getTypeJava().equals("Date")) {
                hasDate = true;
            }
        }

        cg.add("import java.awt.BorderLayout;\n"
                + "import java.awt.CardLayout;\n"
                + "import java.awt.Color;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.FlowLayout;\n"
                + "import java.awt.GridLayout;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.awt.event.ActionListener;\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "import javax.swing.BorderFactory;\n"
                + "import javax.swing.JButton;\n"
                + "import javax.swing.table.DefaultTableModel;\n"
                + "import javax.swing.JDialog;\n"
                + "import javax.swing.JLabel;\n"
                + "import javax.swing.JOptionPane;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JScrollPane;\n"
                + "import javax.swing.JTable;\n"
                + "import javax.swing.JTextField;\n"
        );

        if (hasFK) {
            cg.add("import javax.swing.DefaultComboBoxModel;\n"
                    + "import javax.swing.JComboBox;\n");
        }
        if (hasDate) {
            cg.add("import Tools.DateTools;");
        }

        // Fim Imports
        cg.add("\n /**\n * @author JoaoAN2 " + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()) + "\n */\n");
        cg.add("public class " + className + "GUI extends JDialog {\n"
                + "    " + className + " " + classNameMin + " = new " + className + "();\n"
                + "    DAO" + className + " dao" + className + " = new DAO" + className + "();\n"
                + "    String action;\n"
        );

        if (hasDate) {
            cg.add("    DateTools dt = new DateTools();\n");
        }

        if (hasFK) {
            for (int i = 0; i < atributos.size(); i++) {
                if (atributos.get(i).getOriginTableFK() != null) {
                    cg.add(
                            "    DAO" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + " dao" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + " = new DAO" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + "();\n"
                            + "    DefaultComboBoxModel cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + "Model = new DefaultComboBoxModel();\n"
                            + "    JComboBox cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + " = new JComboBox(cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + "Model);\n"
                    );
                }
            }
        }

        String parametroColunas = "";
        for (int i = 0; i < atributos.size(); i++) {
            parametroColunas += "\"" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "\", ";
        }
        parametroColunas = parametroColunas.substring(0, parametroColunas.length() - 2);

        // Paineís
        cg.add("    Container cp;\n"
                + "    JPanel pnNorth = new JPanel();\n"
                + "    JPanel pnSouth = new JPanel();\n"
                + "    JPanel pnCenter = new JPanel();\n"
                + "    JPanel pnList = new JPanel(new GridLayout(1,1));\n\n"
                + "    String[] col = new String[]{" + parametroColunas + "};\n"
                + "    String[][] data = new String[0][col.length];\n"
                + "    DefaultTableModel model = new DefaultTableModel(data, col);\n\n"
                + "    CardLayout cardLayout = new CardLayout();\n"
                + "    JTable table = new JTable(model);\n"
                + "    JScrollPane scrollTable = new JScrollPane();\n"
                + "    JPanel pnEmpty = new JPanel(new GridLayout(6, 1));");

        // Botões
        cg.add("    JButton btnSearch = new JButton(\"Buscar\");\n"
                + "    JButton btnCreate = new JButton(\"Adicionar\");\n"
                + "    JButton btnSave = new JButton(\"Salvar\");\n"
                + "    JButton btnUpdate = new JButton(\"Alterar\");\n"
                + "    JButton btnDelete = new JButton(\"Excluir\");\n"
                + "    JButton btnList = new JButton(\"Listar\");\n"
                + "    JButton btnCancel = new JButton(\"Cancelar\");\n");

        // Labels e TextFields
        for (int i = 0; i < atributos.size(); i++) {
            cg.add("    JLabel lb" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + " = new JLabel(\"" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "\");\n");

            if (atributos.get(i).getOriginTableFK() == null) {
                cg.add("    JTextField tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + " = new JTextField(" + atributos.get(i).getSize() + ");\n");
            }
        }

        cg.add("    private List<" + className + "> list = new ArrayList<>();\n");

        // Funções
        cg.add("    public void clear() {");
        for (int i = 1; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() == null) {
                cg.add("        tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setText(\"\");");
            }
        }
        cg.add("    }\n");

        cg.add("    public void enabled() {");
        for (int i = 1; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() == null) {
                cg.add("        tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(true);");
            } else {
                cg.add("        cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(true);");
            }
        }
        cg.add("    }\n");

        cg.add("    public void disabled() {");
        for (int i = 1; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() == null) {
                cg.add("        tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(false);");
            } else {
                cg.add("        cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setEnabled(false);");
            }
        }
        cg.add("    }\n");

        // Construtor GUI
        cg.add("\n    public " + className + "GUI() {\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        cp = getContentPane();\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "        setTitle(\"CRUD - " + className + "\");\n\n"
                + "        pnCenter.setLayout(new GridLayout(" + (atributos.size() - 1) + ", col.length - 1));\n"
                + "        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));\n"
                + "\n"
                + "        cp.add(pnNorth, BorderLayout.NORTH);\n"
                + "        cp.add(pnSouth, BorderLayout.SOUTH);\n"
                + "        cp.add(pnCenter, BorderLayout.CENTER);\n"
                + "\n"
                + "        pnNorth.setBackground(Color.cyan);\n"
                + "        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));\n"
                + "\n"
                + "        pnNorth.add(lb" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ");\n"
                + "        pnNorth.add(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ");\n"
                + "        pnNorth.add(btnSearch);\n"
                + "        pnNorth.add(btnList);\n"
                + "        pnNorth.add(btnCreate);\n"
                + "        pnNorth.add(btnUpdate);\n"
                + "        pnNorth.add(btnDelete);\n"
                + "        pnNorth.add(btnSave);\n"
                + "        pnNorth.add(btnCancel);\n"
                + "\n"
                + "        btnCreate.setVisible(false);\n"
                + "        btnUpdate.setVisible(false);\n"
                + "        btnDelete.setVisible(false);\n"
                + "        btnSave.setVisible(false);\n"
                + "        btnCancel.setVisible(false);\n"
                + "\n"
                + "        disabled();\n");

        for (int i = 1; i < atributos.size(); i++) {
            String element = atributos.get(i).getOriginTableFK() == null ? "tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) : "cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK());

            cg.add("        pnCenter.add(lb" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ");\n"
                    + "        pnCenter.add(" + element + ");\n"
            );
        }

        cg.add("        for (int i = 0; i < 5; i++) {\n"
                + "            pnEmpty.add(new JLabel(\" \"));\n"
                + "        }\n"
                + "\n"
                + "        pnSouth.setLayout(cardLayout);\n"
                + "        pnSouth.add(pnEmpty, \"empty\");\n"
                + "        pnSouth.add(pnList, \"list\");\n");

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() != null) {
                cg.add("        List<" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + "> " + atributos.get(i).getOriginTableFK() + "s = dao" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".list();\n"
                        + "        for (" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + " " + atributos.get(i).getOriginTableFK() + " : " + atributos.get(i).getOriginTableFK() + "s) {\n"
                        + "            cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + "Model.addElement(" + atributos.get(i).getOriginTableFK() + ".toString().replace(\";\",\"-\"));\n"
                        + "        }\n");
            }
        }

        // Buscar
        cg.add("        btnSearch.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                cardLayout.show(pnSouth, \"warning\");");

        switch (atributos.get(0).getTypeJava()) {
            case "String":
                cg.add("                " + classNameMin + " = dao" + className + ".obter(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".getText());");
                break;
            case "int":
                cg.add("                " + classNameMin + " = dao" + className + ".obter(Integer.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".getText()));");
                break;
            default:
                cg.add("                " + classNameMin + " = dao" + className + ".obter(ErroDeTipagem.valueOf(tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + "));");
        }

        cg.add("                if (" + classNameMin + " != null) {\n"
                + "                    btnCreate.setVisible(false);\n"
                + "                    btnUpdate.setVisible(true);\n"
                + "                    btnDelete.setVisible(true);\n"
        );

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
                cg.add("                    for (int i = 0; i < " + atributos.get(i).getOriginTableFK() + "s.size(); i++) {\n"
                        + "                        if (" + classNameMin + ".get" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "() == " + atributos.get(i).getOriginTableFK() + "s.get(i)) { \n"
                        + "                            cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".setSelectedIndex(i);\n"
                        + "                            break;\n"
                        + "                        }\n"
                        + "                    }\n");
            }
        }

        cg.add("                } else {\n"
                + "                    clear();\n"
                + "                    btnCreate.setVisible(true);\n"
                + "                    btnUpdate.setVisible(false);\n"
                + "                    btnDelete.setVisible(false);\n"
                + "                }"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener

        // FIM BUSCAR
        // Adicionar
        cg.add("       btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(1).getNameJava()) + ".requestFocus();\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEnabled(false);\n"
                + "                enabled();\n"
                + "\n"
                + "                btnSearch.setVisible(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnSave.setVisible(true);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnList.setVisible(false);\n"
                + "\n"
                + "                action = \"create\";"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM ADICIONAR

        // Salvar
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
                cg.add("                " + classNameMin + ".set" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + "(" + atributos.get(i).getOriginTableFK() + "s.get(cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ".getSelectedIndex()));");
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
                + "                btnDelete.setVisible(false);\n"
                + "\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEnabled(true);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEditable(true);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".requestFocus();\n"
                + "                clear();\n"
                + "                disabled();\n"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM SALVAR

        // Alterar
        cg.add("       btnUpdate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "\n"
                + "                btnSearch.setVisible(false);\n"
                + "                btnCreate.setVisible(false);\n"
                + "                btnSave.setVisible(true);\n"
                + "                btnCancel.setVisible(true);\n"
                + "                btnList.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(1).getNameJava()) + ".requestFocus();\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEditable(false);\n"
                + "                enabled();\n"
                + "\n"
                + "                action = \"update\";"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM Alterar

        // Excluir
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
                + "\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEnabled(true);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEditable(true);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".requestFocus();\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setText(\"\");\n"
                + "                clear();\n"
                + "                disabled();\n"
                + "                btnDelete.setVisible(false);\n"
                + "                btnUpdate.setVisible(false);\n"
                + "                btnCancel.setVisible(false);\n\n"
                + "                if(response == JOptionPane.YES_OPTION) {\n"
                + "                    dao" + className + ".remover(" + classNameMin + ");\n"
                + "                }\n"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM EXCLUIR

        // Listar
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
                + "                btnDelete.setVisible(false);"
        );

        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener

        // FIM LISTAR
        // Cancelar
        cg.add("        btnCancel.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setText(\"\");\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".requestFocus();\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEnabled(true);\n"
                + "                tf" + st.firstLetterToUpperCase(atributos.get(0).getNameJava()) + ".setEditable(true);\n"
                + "\n"
                + "                disabled();\n"
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
        // FIM CANCELAR

        // CONFIGURAÇÕES DA GUI
        cg.add("        setModal(true);\n"
                + "        setSize(600,250);\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setVisible(true);");

        cg.add("    }"); // Fim construtor GUI

        cg.add("}"); // Fim da classe

        for (String linha : cg) {
            System.out.println(linha);
        }

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo("/home/joaoan2/NetBeansProjects/TesteGerador/src/GUIs/" + className + "GUI.java", cg);
    }

}
