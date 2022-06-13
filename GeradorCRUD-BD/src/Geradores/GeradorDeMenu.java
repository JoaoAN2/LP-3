package Geradores;

import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JoaoAN2
 */
public class GeradorDeMenu {

    public GeradorDeMenu(List<Table> tables, String bdName) {
        List<String> cg = new ArrayList();
        StringTools st = new StringTools();
        cg.add("package GUIs;\n");

        // Imports
        cg.add("import java.awt.BorderLayout;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.Font;\n"
                + "import java.awt.GridLayout;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.awt.event.ActionListener;\n"
                + "import javax.swing.DefaultComboBoxModel;\n"
                + "import javax.swing.JButton;\n"
                + "import javax.swing.JComboBox;\n"
                + "import javax.swing.JFrame;\n"
                + "import javax.swing.JLabel;\n"
                + "import javax.swing.JPanel;\n"
        );

        cg.add("\n /**\n * @author JoaoAN2 " + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()) + "\n */\n");
        cg.add("public class GUIMenu extends JFrame {\n"
                + "    private Container cp;\n"
                + "    DefaultComboBoxModel cbMenuModel = new DefaultComboBoxModel();\n"
                + "    JComboBox cbMenu = new JComboBox(cbMenuModel);\n"
                + "    JLabel lbComboBox = new JLabel(\"Selecione o CRUD: \");\n"
                + "    JPanel pnLbComboBox = new JPanel();\n"
                + "    JPanel pnComboBox = new JPanel();\n\n"
                + "    JButton btnChoose = new JButton(\"Selecionar o CRUD\");\n"
        );

        cg.add("    public GUIMenu() {\n"
                + "        cp = getContentPane();\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        setTitle(\"Menu - " + st.firstLetterToUpperCase(st.bdToJava(bdName)) + "\");\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "\n"
                + "        JLabel lbTitle = new JLabel(\"Menu\");\n"
                + "\n"
                + "        JPanel pnNorth = new JPanel();\n"
                + "        cp.add(BorderLayout.NORTH, pnNorth);\n"
                + "        pnNorth.add(lbTitle);\n"
                + "\n"
                + "        JPanel pnWest = new JPanel(new GridLayout(1, 1));\n"
                + "        cp.add(BorderLayout.WEST, pnWest);\n"
                + "\n"
                + "        JPanel pnCenter = new JPanel(new GridLayout(1, 1));\n"
                + "        cp.add(BorderLayout.CENTER, pnCenter);\n"
                + "\n"
                + "        JPanel pnSouth = new JPanel();\n"
                + "        cp.add(BorderLayout.SOUTH, pnSouth);\n"
                + "        pnSouth.add(btnChoose);\n"
                + "\n"
                + "        pnLbComboBox.add(lbComboBox);\n"
                + "        pnComboBox.add(cbMenu);\n"
                + "\n"
                + "        pnWest.add(pnLbComboBox);\n"
                + "        pnCenter.add(pnComboBox);\n"
                + "        \n"
                + "        cbMenu.setFont(new Font(\"Arial\", 1, 20));\n"
                + "        lbComboBox.setFont(new Font(\"Arial\", 1, 24));\n"
        );

        for (int i = 0; i < tables.size(); i++) {
            cg.add("        cbMenuModel.addElement(\"" + st.firstLetterToUpperCase(tables.get(i).getTableNameJava()) + "\");");
        }

        cg.add("        btnChoose.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                switch ((String) cbMenu.getSelectedItem()) {\n");

        for (int i = 0; i < tables.size(); i++) {
            cg.add("                    case \"" + st.firstLetterToUpperCase(tables.get(i).getTableNameJava()) + "\":\n"
                    + "                        " + st.firstLetterToUpperCase(tables.get(i).getTableNameJava()) + "GUI " + tables.get(i).getTableNameJava() + " = new " + st.firstLetterToUpperCase(tables.get(i).getTableNameJava()) + "GUI();\n"
                    + "                        break;");
        }

        cg.add("\n"
                + "                }\n"
                + "            }\n"
                + "        });\n"
                + "\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setSize(600, 150);\n"
                + "        setVisible(true);\n"
                + "    }\n"
                + "}");

        for (String linha : cg) {
            System.out.println(linha);
        }

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo("/home/joaoan2/NetBeansProjects/TesteGerador/src/GUIs/GUIMenu.java", cg);
    }
}
