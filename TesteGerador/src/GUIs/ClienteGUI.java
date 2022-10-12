package GUIs;

import Entidades.Cliente;
import DAOs.DAOCliente;
import Entidades.Pessoa;
import DAOs.DAOPessoa;
import Entidades.TipoCliente;
import DAOs.DAOTipoCliente;
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

public class ClienteGUI extends JDialog {
    String action;

    Cliente cliente = new Cliente();
    DAOCliente daoCliente = new DAOCliente();

    DateTools dt = new DateTools();

    DAOPessoa daoPessoa = new DAOPessoa();
    DefaultComboBoxModel cbPessoaModel = new DefaultComboBoxModel();
    JComboBox cbPessoa = new JComboBox(cbPessoaModel);

    DAOTipoCliente daoTipoCliente = new DAOTipoCliente();
    DefaultComboBoxModel cbTipoClienteModel = new DefaultComboBoxModel();
    JComboBox cbTipoCliente = new JComboBox(cbTipoClienteModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Pessoa Id Cliente", "Cadastro Cliente", "Desligamento Cliente", "Divida Cliente", "Tipo Cliente Id Cliente"};
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

    JLabel lbPessoaIdCliente = new JLabel("Pessoa Id Cliente");
    JLabel lbCadastroCliente = new JLabel("Cadastro Cliente");
    JTextField tfCadastroCliente = new JTextField(10);

    JLabel lbDesligamentoCliente = new JLabel("Desligamento Cliente");
    JTextField tfDesligamentoCliente = new JTextField(10);

    JLabel lbDividaCliente = new JLabel("Divida Cliente");
    JTextField tfDividaCliente = new JTextField(20);

    JLabel lbTipoClienteIdCliente = new JLabel("Tipo Cliente Id Cliente");
    private List<Cliente> list = new ArrayList<>();

    public void clear() {
        tfCadastroCliente.setText("");
        tfDesligamentoCliente.setText("");
        tfDividaCliente.setText("");
    }

    public void enabled() {
        tfCadastroCliente.setEditable(true);
        tfDesligamentoCliente.setEditable(true);
        tfDividaCliente.setEditable(true);
        cbTipoCliente.setEnabled(true);
    }

    public void disabled() {
        tfCadastroCliente.setEditable(false);
        tfDesligamentoCliente.setEditable(false);
        tfDividaCliente.setEditable(false);
        cbTipoCliente.setEnabled(false);
    }


    public ClienteGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Cliente");

        pnCenter.setLayout(new GridLayout(4, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbPessoaIdCliente);
        pnNorth.add(cbPessoa);

        pnNorth.add(lbCadastroCliente);
        pnNorth.add(lbDesligamentoCliente);
        pnNorth.add(lbDividaCliente);
        pnNorth.add(lbTipoClienteIdCliente);
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

        pnCenter.add(lbCadastroCliente);
        pnCenter.add(tfCadastroCliente);

        pnCenter.add(lbDesligamentoCliente);
        pnCenter.add(tfDesligamentoCliente);

        pnCenter.add(lbDividaCliente);
        pnCenter.add(tfDividaCliente);

        pnCenter.add(lbTipoClienteIdCliente);
        pnCenter.add(cbTipoCliente);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Pessoa> pessoaList = daoPessoa.list();
        for (Pessoa pessoa : pessoaList) {
            cbPessoaModel.addElement(pessoa);
        }

        List<TipoCliente> tipoClienteList = daoTipoCliente.list();
        for (TipoCliente tipoCliente : tipoClienteList) {
            cbTipoClienteModel.addElement(tipoCliente);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                cliente = daoCliente.obter(((Pessoa) cbPessoa.getSelectedItem()).getIdPessoa());

                if (cliente != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfCadastroCliente.setText(dt.conversionDateToString(cliente.getCadastroCliente()));
                    tfDesligamentoCliente.setText(dt.conversionDateToString(cliente.getDesligamentoCliente()));
                    tfDividaCliente.setText(String.valueOf(cliente.getDividaCliente()));
                    cbTipoCliente.setSelectedItem(cliente.getTipoClienteIdCliente());
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

                tfCadastroCliente.requestFocus();

                cbPessoa.setEnabled(false);
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

                cbPessoa.setEnabled(true);
                cbPessoa.setEditable(true);

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoCliente.remover(cliente);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Cliente> clienteList = daoCliente.list();
                String[] col = {"Pessoa Id Cliente", "Cadastro Cliente", "Desligamento Cliente", "Divida Cliente", "Tipo Cliente Id Cliente"};
                Object[][] data = new Object[clienteList.size()][col.length];

                String aux[];

                for (int i = 0; i < clienteList.size(); i++) {
                    aux = clienteList.get(i).toString().split(";");
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
                    cliente =  new Cliente();
                }

                cliente.setPessoaIdCliente(((Pessoa) cbPessoa.getSelectedItem()).getIdPessoa());
                cliente.setCadastroCliente(dt.conversionStringToDate(tfCadastroCliente.getText()));
                cliente.setDesligamentoCliente(dt.conversionStringToDate(tfDesligamentoCliente.getText()));
                cliente.setDividaCliente(Double.parseDouble(tfDividaCliente.getText()));
                cliente.setTipoClienteIdCliente((TipoCliente) cbTipoCliente.getSelectedItem());

                if("create".equals(action)){
                    daoCliente.inserir(cliente);
                }

                if("update".equals(action)){
                    daoCliente.atualizar(cliente);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                cbPessoa.setEnabled(true);
                cbPessoa.setEditable(true);

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

                cbPessoa.setEnabled(false);
                cbPessoa.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                cbPessoa.setEnabled(true);
                cbPessoa.setEditable(true);

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
