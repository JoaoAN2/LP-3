package Main;

import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JoaoAN2
 */
class GeradorDeGUI {
    
    GeradorDeGUI(String className, List<String> atributos) {
        StringTools stringTools = new StringTools();
        String classNameMin = stringTools.firstLetterToLowerCase(className);
        String[] aux;
        List<String> cg = new ArrayList(); // Código gerado

        cg.add("package GUIs;\n");

        // Imports
        cg.add("import Entidades." + className + ";\n"
                + "import DAOs.DAO" + className + ";\n"
                + "import java.awt.BorderLayout;\n"
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
                + "import javax.swing.DefaultComboBoxModel;\n"
                + "import javax.swing.JButton;\n"
                + "import javax.swing.JComboBox;\n"
                + "import javax.swing.JDialog;\n"
                + "import javax.swing.JLabel;\n"
                + "import javax.swing.JOptionPane;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JScrollPane;\n"
                + "import javax.swing.JTable;\n"
                + "import javax.swing.JTextField;\n"
                + "import javax.swing.table.DefaultTableModel;"
        );

        cg.add("\n /**\n * @author JoaoAN2 " + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()) + "\n */\n");
        cg.add("public class " + className + "GUI extends JDialog {\n"
                + "    " + className + " " + classNameMin + " = new " + className + "();\n"
                + "    DAO" + className + " dao" + className + " = new DAO" + className + "();\n"
                + "    String action;\n"
        );

        String parametroColunas = "";
        for (int i = 0; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            parametroColunas += "\"" + stringTools.firstLetterToUpperCase(aux[1]) + "\", ";
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
            aux = atributos.get(i).split(";");
            cg.add("    JLabel lb" + stringTools.firstLetterToUpperCase(aux[1]) + " = new JLabel(\"" + stringTools.firstLetterToUpperCase(aux[1]) + "\");\n"
                    + "    JTextField tf" + stringTools.firstLetterToUpperCase(aux[1]) + " = new JTextField(" + aux[2] + ");\n"
            );
        }

        cg.add("    private List<" + className + "> list = new ArrayList<>();\n");

        // Funções
        cg.add("    public void clear() {");
        for (int i = 1; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            cg.add("        tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setText(\"\");");
        }
        cg.add("    }\n");

        cg.add("    public void enabled() {");
        for (int i = 1; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            cg.add("        tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEditable(true);");
        }
        cg.add("    }\n");

        cg.add("    public void disabled() {");
        for (int i = 1; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            cg.add("        tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEditable(false);");
        }
        cg.add("    }\n");

        // Construtor GUI
        cg.add("\n    public " + className + "GUI() {\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        cp = getContentPane();\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "        setTitle(\"CRUD - " + className + "\");\n\n"
                + "        pnCenter.setLayout(new GridLayout("+ (atributos.size() - 1) +", col.length - 1));\n"
                + "        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));\n"
                + "\n"
                + "        cp.add(pnNorth, BorderLayout.NORTH);\n"
                + "        cp.add(pnSouth, BorderLayout.SOUTH);\n"
                + "        cp.add(pnCenter, BorderLayout.CENTER);\n"
                + "\n"
                + "        pnNorth.setBackground(Color.cyan);\n"
                + "        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));\n"
                + "\n"
                + "        pnNorth.add(lb" + stringTools.firstLetterToUpperCase(atributos.get(0).split(";")[1]) + ");\n"
                + "        pnNorth.add(tf" + stringTools.firstLetterToUpperCase(atributos.get(0).split(";")[1]) + ");\n"
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
            aux = atributos.get(i).split(";");
            cg.add("        pnCenter.add(lb" + stringTools.firstLetterToUpperCase(aux[1]) + ");\n"
                    + "        pnCenter.add(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ");\n"
            );
        }

        cg.add("        for (int i = 0; i < 5; i++) {\n"
                + "            pnEmpty.add(new JLabel(\" \"));\n"
                + "        }\n"
                + "\n"
                + "        pnSouth.setLayout(cardLayout);\n"
                + "        pnSouth.add(pnEmpty, \"empty\");\n"
                + "        pnSouth.add(pnList, \"list\");\n");

        // Buscar
        cg.add("        btnSearch.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                cardLayout.show(pnSouth, \"warning\");");
        
        aux = atributos.get(0).split(";");
        switch (aux[0]) {
            case "String":
                cg.add("                " + classNameMin + " = dao" + className + ".obter(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText());");
                break;
            case "int":
                cg.add("                " + classNameMin + " = dao" + className + ".obter(Integer.valueOf(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText()));");
                break;
            default:
                cg.add("                " + classNameMin + " = dao" + className + ".obter(ErroDeTipagem.valueOf(tf" + stringTools.firstLetterToUpperCase(aux[1]) + "));");
        }
        
        cg.add("                if (" + classNameMin + " != null) {\n"
                + "                    btnCreate.setVisible(false);\n"
                + "                    btnUpdate.setVisible(true);\n"
                + "                    btnDelete.setVisible(true);\n"
        );
        
        for(int i = 1; i < atributos.size(); i++){
            aux = atributos.get(i).split(";");
            switch (aux[0]){
                case "String":
                    cg.add("                    tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setText(" + classNameMin + ".get" + stringTools.firstLetterToUpperCase(aux[1]) + "());");
                    break;
                default:
                    cg.add("                    tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setText(String.valueOf(" + classNameMin + ".get" + stringTools.firstLetterToUpperCase(aux[1]) + "()));");
                    break;
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
        aux = atributos.get(0).split(";");
        cg.add("       btnCreate.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                tf" + stringTools.firstLetterToUpperCase(atributos.get(1).split(";")[1]) + ".requestFocus();\n"
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEnabled(false);\n"
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
        
        for(int i = 0; i < atributos.size(); i++) {
            aux = atributos.get(i).split(";");
            switch(aux[0]){
                case "String":
                    cg.add("                " + classNameMin + ".set" + stringTools.firstLetterToUpperCase(aux[1]) + "(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText());");
                    break;
                case "int":
                    cg.add("                " + classNameMin + ".set" + stringTools.firstLetterToUpperCase(aux[1]) + "(Integer.valueOf(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText()));");
                    break;
                case "double":
                    cg.add("                " + classNameMin + ".set" + stringTools.firstLetterToUpperCase(aux[1]) + "(Double.parseDouble(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText()));");
                    break;
                case "Short":
                    cg.add("                " + classNameMin + ".set" + stringTools.firstLetterToUpperCase(aux[1]) + "(Short.valueOf(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText()));");
                    break;
                default:
                    cg.add("                " + classNameMin + ".set" + stringTools.firstLetterToUpperCase(aux[1]) + "("+ aux[0] +".valueOf(tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".getText()));");
            }
        }
        
        cg.add("\n                if(\"create\".equals(action)){\n"
                + "                    dao" + className + ".inserir(" + classNameMin + ");\n"
                + "                }");
        
        cg.add("\n                if(\"update\".equals(action)){\n"
                + "                    dao" + className + ".atualizar(" + classNameMin + ");\n"
                + "                }\n");
        
        aux = atributos.get(0).split(";");
        cg.add("                btnSearch.setVisible(true);\n"
                 + "                btnList.setVisible(true);\n"
                 + "                btnSave.setVisible(false);\n"
                 + "                btnCancel.setVisible(false);\n"
                 + "                btnDelete.setVisible(false);\n"
                 + "\n"
                 + "                tf"+ stringTools.firstLetterToUpperCase(aux[1]) +".setEnabled(true);\n"
                 + "                tf"+ stringTools.firstLetterToUpperCase(aux[1]) +".setEditable(true);\n"
                 + "                tf"+ stringTools.firstLetterToUpperCase(aux[1]) +".requestFocus();\n"
                 + "                clear();\n"
                 + "                disabled();\n"
        );
        
        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM SALVAR
        
        // Alterar
        aux = atributos.get(0).split(";");
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
                + "                tf" + stringTools.firstLetterToUpperCase(atributos.get(1).split(";")[1]) + ".requestFocus();\n"
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEditable(false);\n"
                + "                enabled();\n"
                + "\n"
                + "                action = \"update\";"
        );
        
        cg.add("            }"); // Action
        cg.add("        });\n"); // Listener
        // FIM Alterar
        
        // Excluir
        aux = atributos.get(0).split(";");
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
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEnabled(true);\n"
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEditable(true);\n"
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".requestFocus();\n"                
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setText(\"\");\n"
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
        aux = atributos.get(0).split(";");
        cg.add("        btnCancel.addActionListener(new ActionListener() {\n" 
                + "            @Override\n" 
                + "            public void actionPerformed(ActionEvent ae) {\n" 
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setText(\"\");\n" 
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".requestFocus();\n" 
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEnabled(true);\n" 
                + "                tf" + stringTools.firstLetterToUpperCase(aux[1]) + ".setEditable(true);\n" 
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
        manipulaArquivo.salvarArquivo("src/GUIs/" + className + "GUI.java", cg);
    }
    
}
