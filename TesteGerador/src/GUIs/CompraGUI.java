package GUIs;

import Entidades.Compra;
import DAOs.DAOCompra;
import Entidades.Cliente;
import DAOs.DAOCliente;
import Entidades.Funcionario;
import DAOs.DAOFuncionario;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import Tools.DateTools;

 /**
 * @author JoaoAN2 11/10/2022 - 20:55:33
 */

public class CompraGUI extends JDialog {
    String action;

    Compra compra = new Compra();
    DAOCompra daoCompra = new DAOCompra();

    DateTools dt = new DateTools();

    DAOCliente daoCliente = new DAOCliente();
    DefaultComboBoxModel cbClienteModel = new DefaultComboBoxModel();
    JComboBox cbCliente = new JComboBox(cbClienteModel);

    DAOFuncionario daoFuncionario = new DAOFuncionario();
    DefaultComboBoxModel cbFuncionarioModel = new DefaultComboBoxModel();
    JComboBox cbFuncionario = new JComboBox(cbFuncionarioModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id Compra", "Data Compra", "Cliente Pessoa Id Compra", "Funcionario Pessoa Id Compra"};
    String[][] data = new String[0][col.length];
    DefaultTableModel model = new DefaultTableModel(data, col);

    CardLayout cardLayout = new CardLayout();
    JTable table = new JTable(model);
    JScrollPane scrollTable = new JScrollPane();
    JPanel pnEmpty = new JPanel(new GridLayout(6, 1));
    JButton btnSearch = new JButton("Buscar");
    JButton btnCreate = new JButton("Adicionar");
    JButton btnDelete = new JButton("Excluir");
    JButton btnList = new JButton("Listar");
    JButton btnUpdate = new JButton("Alterar");
    JButton btnSave = new JButton("Salvar");
    JButton btnCancel = new JButton("Cancelar");

    JLabel lbIdCompra = new JLabel("Id Compra");
    JTextField tfIdCompra = new JTextField(11);

    JLabel lbDataCompra = new JLabel("Data Compra");
    JTextField tfDataCompra = new JTextField(10);

    JLabel lbClientePessoaIdCompra = new JLabel("Cliente Pessoa Id Compra");
    JLabel lbFuncionarioPessoaIdCompra = new JLabel("Funcionario Pessoa Id Compra");
    private List<Compra> list = new ArrayList<>();

    public void clear() {
        tfDataCompra.setText("");
    }

    public void enabled() {
        tfDataCompra.setEditable(true);
        cbCliente.setEnabled(true);
        cbFuncionario.setEnabled(true);
    }

    public void disabled() {
        tfDataCompra.setEditable(false);
        cbCliente.setEnabled(false);
        cbFuncionario.setEnabled(false);
    }


    public CompraGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Compra");

        pnCenter.setLayout(new GridLayout(3, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdCompra);
        pnNorth.add(tfIdCompra);

        pnNorth.add(lbDataCompra);
        pnNorth.add(lbClientePessoaIdCompra);
        pnNorth.add(lbFuncionarioPessoaIdCompra);
        pnNorth.add(btnSearch);
        pnNorth.add(btnList);
        pnNorth.add(btnCreate);
        pnNorth.add(btnDelete);
        btnCreate.setVisible(false);
        btnDelete.setVisible(false);

        pnNorth.add(btnUpdate);
        pnNorth.add(btnSave);
        pnNorth.add(btnCancel);
        btnUpdate.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);

        disabled();

        pnCenter.add(lbDataCompra);
        pnCenter.add(tfDataCompra);

        pnCenter.add(lbClientePessoaIdCompra);
        pnCenter.add(cbCliente);

        pnCenter.add(lbFuncionarioPessoaIdCompra);
        pnCenter.add(cbFuncionario);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Cliente> clienteList = daoCliente.list();
        for (Cliente cliente : clienteList) {
            cbClienteModel.addElement(cliente);
        }

        List<Funcionario> funcionarioList = daoFuncionario.list();
        for (Funcionario funcionario : funcionarioList) {
            cbFuncionarioModel.addElement(funcionario);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                compra = daoCompra.obter(Integer.valueOf(tfIdCompra.getText()));

                if (compra != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfDataCompra.setText(dt.conversionDateToString(compra.getDataCompra()));
                    cbCliente.setSelectedItem(compra.getClientePessoaIdCompra());
                    cbFuncionario.setSelectedItem(compra.getFuncionarioPessoaIdCompra());
                } else {
                    clear();
                    btnUpdate.setVisible(false);

                    btnCreate.setVisible(true);
                    btnDelete.setVisible(false);
                }
            }
        });

       btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfDataCompra.requestFocus();

                tfIdCompra.setEnabled(false);
                btnCreate.setVisible(false);
                action = "create";
                enabled();
                btnSearch.setVisible(false);
                btnList.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);


            }
        });

       btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                int response = JOptionPane.showConfirmDialog(
                        cp,
                        "Tem certeza que deseja excluir?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                tfIdCompra.setEnabled(true);
                tfIdCompra.setEditable(true);
                tfIdCompra.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoCompra.remover(compra);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Compra> compraList = daoCompra.list();
                String[] col = {"Id Compra", "Data Compra", "Cliente Pessoa Id Compra", "Funcionario Pessoa Id Compra"};
                Object[][] data = new Object[compraList.size()][col.length];

                String aux[];

                for (int i = 0; i < compraList.size(); i++) {
                    aux = compraList.get(i).toString().split(";");
                    for (int j = 0; j < col.length; j++) {
                        try {
                            data[i][j] = aux[j] == null ? "null" : aux[j];;
                        } catch (Exception e) {
                            data[i][j] = "null";
                        }
                    }
                }
                cardLayout.show(pnSouth, "list");

                scrollTable.setPreferredSize(table.getPreferredSize());
                pnList.add(table);
                pnList.add(scrollTable);
                scrollTable.setViewportView(table);
                model.setDataVector(data, col);

                btnCreate.setVisible(false);
                btnDelete.setVisible(false);

                btnUpdate.setVisible(false);

            }
        });

       btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if("create".equals(action)) {
                    compra =  new Compra();
                }

                compra.setIdCompra(Integer.valueOf(tfIdCompra.getText()));
                compra.setDataCompra(dt.conversionStringToDate(tfDataCompra.getText()));
                compra.setClientePessoaIdCompra((Cliente) cbCliente.getSelectedItem());
                compra.setFuncionarioPessoaIdCompra((Funcionario) cbFuncionario.getSelectedItem());

                if("create".equals(action)){
                    daoCompra.inserir(compra);
                }

                if("update".equals(action)){
                    daoCompra.atualizar(compra);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdCompra.setEnabled(true);
                tfIdCompra.setEditable(true);

                clear();
                disabled();

            }
        });

       btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                btnSearch.setVisible(false);
                btnCreate.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);
                btnList.setVisible(false);
                btnUpdate.setVisible(false);

                tfIdCompra.setEnabled(false);
                tfIdCompra.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdCompra.setEnabled(true);
                tfIdCompra.setEditable(true);
                tfIdCompra.setText("");

                disabled();
                clear();

                btnCreate.setVisible(false);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
                btnCancel.setVisible(false);
                btnSave.setVisible(false);
                btnSearch.setVisible(true);
                btnList.setVisible(true);
            }
        });

        setModal(true);
        setSize(600,250);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
