package ToolsGUIGenerator;

import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Buttons {

    public Buttons(List<String> cg) {
        cg.add("    JButton btnSearch = new JButton(\"Buscar\");\n"
                + "    JButton btnCreate = new JButton(\"Adicionar\");\n"
                + "    JButton btnSave = new JButton(\"Salvar\");\n"
                + "    JButton btnUpdate = new JButton(\"Alterar\");\n"
                + "    JButton btnDelete = new JButton(\"Excluir\");\n"
                + "    JButton btnList = new JButton(\"Listar\");\n"
                + "    JButton btnCancel = new JButton(\"Cancelar\");\n");
    }

}
