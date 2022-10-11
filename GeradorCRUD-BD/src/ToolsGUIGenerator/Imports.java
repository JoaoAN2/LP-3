package ToolsGUIGenerator;

import Entidades.Attribute;
import Entidades.Table;
import Tools.StringTools;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Imports {

    StringTools st = new StringTools();

    public Imports(List<String> cg, Table tableEntity) {

        List<Attribute> atributos = tableEntity.getAttributes();
        String className = st.firstLetterToUpperCase(tableEntity.getTableNameJava());

        if (tableEntity.isHasAttribute()) {
            cg.add("import Entidades." + className + ";\n"
                    + "import DAOs.DAO" + className + ";");
        }

        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).getOriginTableFK() != null) {
                cg.add("import Entidades." + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ";\n"
                        + "import DAOs.DAO" + st.firstLetterToUpperCase(st.bdToJava(atributos.get(i).getOriginTableFK())) + ";");
                tableEntity.setHasFK(true);
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
                + "import javax.swing.JTextField;"
        );

        if (tableEntity.isHasFK()) {
            cg.add("import javax.swing.DefaultComboBoxModel;\n"
                    + "import javax.swing.JComboBox;");
        }

        if (tableEntity.isHasDate()) {
            cg.add("import Tools.DateTools;");
        }
    }

}
