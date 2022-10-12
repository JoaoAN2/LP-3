package GUIs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


 /**
 * @author JoaoAN2 11/10/2022 - 20:55:34
 */

public class GUIMenu extends JFrame {
    private Container cp;
    DefaultComboBoxModel cbMenuModel = new DefaultComboBoxModel();
    JComboBox cbMenu = new JComboBox(cbMenuModel);
    JLabel lbComboBox = new JLabel("Selecione o CRUD: ");
    JPanel pnLbComboBox = new JPanel();
    JPanel pnComboBox = new JPanel();

    JButton btnChoose = new JButton("Selecionar o CRUD");

    public GUIMenu() {
        cp = getContentPane();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Menu - Boticario");
        cp.setLayout(new BorderLayout());

        JLabel lbTitle = new JLabel("Menu");

        JPanel pnNorth = new JPanel();
        cp.add(BorderLayout.NORTH, pnNorth);
        pnNorth.add(lbTitle);

        JPanel pnWest = new JPanel(new GridLayout(1, 1));
        cp.add(BorderLayout.WEST, pnWest);

        JPanel pnCenter = new JPanel(new GridLayout(1, 1));
        cp.add(BorderLayout.CENTER, pnCenter);

        JPanel pnSouth = new JPanel();
        cp.add(BorderLayout.SOUTH, pnSouth);
        pnSouth.add(btnChoose);

        pnLbComboBox.add(lbComboBox);
        pnComboBox.add(cbMenu);

        pnWest.add(pnLbComboBox);
        pnCenter.add(pnComboBox);
        
        cbMenu.setFont(new Font("Arial", 1, 20));
        lbComboBox.setFont(new Font("Arial", 1, 24));

        cbMenuModel.addElement("Cargo");
        cbMenuModel.addElement("Cliente");
        cbMenuModel.addElement("Compra");
        cbMenuModel.addElement("CompraHasProduto");
        cbMenuModel.addElement("Funcionario");
        cbMenuModel.addElement("Pessoa");
        cbMenuModel.addElement("Produto");
        cbMenuModel.addElement("TipoCliente");
        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch ((String) cbMenu.getSelectedItem()) {

                    case "Cargo":
                        CargoGUI cargo = new CargoGUI();
                        break;
                    case "Cliente":
                        ClienteGUI cliente = new ClienteGUI();
                        break;
                    case "Compra":
                        CompraGUI compra = new CompraGUI();
                        break;
                    case "CompraHasProduto":
                        CompraHasProdutoGUI compraHasProduto = new CompraHasProdutoGUI();
                        break;
                    case "Funcionario":
                        FuncionarioGUI funcionario = new FuncionarioGUI();
                        break;
                    case "Pessoa":
                        PessoaGUI pessoa = new PessoaGUI();
                        break;
                    case "Produto":
                        ProdutoGUI produto = new ProdutoGUI();
                        break;
                    case "TipoCliente":
                        TipoClienteGUI tipoCliente = new TipoClienteGUI();
                        break;

                }
            }
        });

        setLocationRelativeTo(null);
        setSize(600, 150);
        setVisible(true);
    }
}
