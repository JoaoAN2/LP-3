package ToolsGUIGenerator;

import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Panels {

    public Panels(List<String> cg, String parametroColunas) {
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
    }

}
