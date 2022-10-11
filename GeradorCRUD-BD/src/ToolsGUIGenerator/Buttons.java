package ToolsGUIGenerator;

import Entidades.Table;
import java.util.List;

/**
 *
 * @author joaoan2
 */
public class Buttons {

    public Buttons(List<String> cg, Table tableEntity) {
        cg.add("    JButton btnSearch = new JButton(\"Buscar\");\n"
                + "    JButton btnCreate = new JButton(\"Adicionar\");\n"
                + "    JButton btnDelete = new JButton(\"Excluir\");\n"
                + "    JButton btnList = new JButton(\"Listar\");");

        if (tableEntity.isHasAttribute()) {
            cg.add("    JButton btnUpdate = new JButton(\"Alterar\");\n"
                    + "    JButton btnSave = new JButton(\"Salvar\");\n"
                    + "    JButton btnCancel = new JButton(\"Cancelar\");\n");
        }
    }

}
