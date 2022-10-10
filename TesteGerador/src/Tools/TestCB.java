package Tools;

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
 * @author zorawski 31/08/2022 - 20:17:39
 */

public class TestCB extends JFrame {
    private Container cp;
    DefaultComboBoxModel cbMenuModel = new DefaultComboBoxModel();
    JComboBox cbMenu = new JComboBox(cbMenuModel);
    JLabel lbComboBox = new JLabel("Selecione o CRUD: ");
    JPanel pnLbComboBox = new JPanel();
    JPanel pnComboBox = new JPanel();

    JButton btnChoose = new JButton("Selecionar o CRUD");

    public TestCB() {
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
                System.out.println(cbMenu.getSelectedItem());
                
            }
        });

        setLocationRelativeTo(null);
        setSize(600, 150);
        setVisible(true);
    }
}
