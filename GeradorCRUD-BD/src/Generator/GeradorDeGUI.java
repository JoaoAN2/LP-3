package Generator;

import ActionsGUIGenerator.Cancell;
import ActionsGUIGenerator.Create;
import ActionsGUIGenerator.Delete;
import ActionsGUIGenerator.ListAction;
import ActionsGUIGenerator.Read;
import ActionsGUIGenerator.Save;
import ActionsGUIGenerator.Update;
import Entidades.Atribute;
import Entidades.Table;
import Tools.ManipulaArquivo;
import Tools.StringTools;
import ToolsGUIGenerator.Buttons;
import ToolsGUIGenerator.Imports;
import ToolsGUIGenerator.Panels;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JoaoAN2
 */
public class GeradorDeGUI {

    public GeradorDeGUI(Table tableEntity) {
        StringTools st = new StringTools();
        List<Atribute> atributos = tableEntity.getAtributes();
        String className = tableEntity.getTableNameJava();
        String classNameMin = st.firstLetterToLowerCase(className);
        List<String> cg = new ArrayList(); // Código gerado
        int numberAtributes = 0;
        int numberPK = 0;

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getKey().equals("PRI")) {
                numberPK++;
            } else {
                numberAtributes++;
            }
        }

        cg.add("package GUIs;\n");

        Imports imports = new Imports(cg, atributos, className, classNameMin, tableEntity);
        cg.add("\n /**\n * @author JoaoAN2 " + new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()) + "\n */\n");
        cg.add("public class " + className + "GUI extends JDialog {\n"
                + "    " + className + " " + classNameMin + " = new " + className + "();\n"
                + "    DAO" + className + " dao" + className + " = new DAO" + className + "();\n"
                + "    String action;\n"
        );

        if (tableEntity.isHasDate()) {
            cg.add("    DateTools dt = new DateTools();\n");
        }

        if (tableEntity.isHasFK()) {
            for (int i = 0; i < atributos.size(); i++) {
                if (atributos.get(i).getOriginTableFK() != null) {
                    cg.add(
                            "    DAO" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + " dao" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + " = new DAO" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + "();\n"
                            + "    DefaultComboBoxModel cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + "Model = new DefaultComboBoxModel();\n"
                            + "    JComboBox cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + " = new JComboBox(cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + "Model);\n"
                    );
                }
            }
        }

        String parametroColunas = "";
        for (int i = 0; i < atributos.size(); i++) {
            parametroColunas += "\"" + st.firstLetterToUpperCase(atributos.get(i).getLabelName()) + "\", ";
        }
        parametroColunas = parametroColunas.substring(0, parametroColunas.length() - 2);

        // Paineís
        Panels panels = new Panels(cg, parametroColunas);
        Buttons buttons = new Buttons(cg);

        // Labels e TextFields
        for (int i = 0; i < atributos.size(); i++) {
            cg.add("    JLabel lb" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + " = new JLabel(\"" + st.firstLetterToUpperCase(atributos.get(i).getLabelName()) + "\");");

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
        for (int i = 0; i < atributos.size(); i++) {
            if (!atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("        tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(true);");
                } else {
                    cg.add("        cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ".setEnabled(true);");
                }
            }
        }
        cg.add("    }\n");

        cg.add("    public void disabled() {");
        for (int i = 0; i < atributos.size(); i++) {
            if (!atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() == null) {
                    cg.add("        tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ".setEditable(false);");
                } else {
                    cg.add("        cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ".setEnabled(false);");
                }
            }
        }
        cg.add("    }\n");

        // Construtor GUI
        cg.add("\n    public " + className + "GUI() {\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        cp = getContentPane();\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "        setTitle(\"CRUD - " + className + "\");\n\n"
                + "        pnCenter.setLayout(new GridLayout(" + numberAtributes + ", col.length - 1));\n"
                + "        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));\n"
                + "\n"
                + "        cp.add(pnNorth, BorderLayout.NORTH);\n"
                + "        cp.add(pnSouth, BorderLayout.SOUTH);\n"
                + "        cp.add(pnCenter, BorderLayout.CENTER);\n"
                + "\n"
                + "        pnNorth.setBackground(Color.cyan);\n"
                + "        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));\n"
                + "\n");

        for (int i = 0; i < atributos.size(); i++) {
            cg.add("        pnNorth.add(lb" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ");");
            if (atributos.get(i).getKey().equals("PRI")) {
                if (atributos.get(i).getOriginTableFK() != null) {
                    cg.add("        pnNorth.add(cb" + st.firstLetterToUpperCase(atributos.get(i).getOriginTableFK()) + ");\n");
                } else {
                    cg.add("        pnNorth.add(tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ");\n");
                }
            }
        }

        cg.add("        pnNorth.add(btnSearch);\n"
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

        for (int i = 0; i < atributos.size(); i++) {
            if (!atributos.get(i).getKey().equals("PRI")) {
                String element = atributos.get(i).getOriginTableFK() == null ? "tf" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) : "cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK()));

                cg.add("        pnCenter.add(lb" + st.firstLetterToUpperCase(atributos.get(i).getNameJava()) + ");\n"
                        + "        pnCenter.add(" + element + ");\n");
            }
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
                cg.add("        List<" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + "> " + st.bdToJava(atributos.get(i).getOriginTableFK()) + "s = dao" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ".list();\n"
                        + "        for (" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + " " + st.bdToJava(atributos.get(i).getOriginTableFK()) + " : " + st.bdToJava(atributos.get(i).getOriginTableFK()) + "s) {\n"
                        + "            cb" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + "Model.addElement(" + st.bdToJava(atributos.get(i).getOriginTableFK()) + ");\n"
                        + "        }\n");
            }
        }

        Read read = new Read(cg, atributos, className, classNameMin);
        Create create = new Create(cg, atributos, numberPK);
        Save save = new Save(cg, atributos, className, classNameMin);
        Update update = new Update(cg, atributos);
        Delete delete = new Delete(cg, atributos, className, classNameMin);
        ListAction listAction = new ListAction(cg, parametroColunas, className, classNameMin);
        Cancell cancell = new Cancell(cg, atributos);

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
        manipulaArquivo.salvarArquivo("/home/joaoan2/projects/LP-3/TesteGerador/src/GUIs/" + className + "GUI.java", cg);
    }

}
