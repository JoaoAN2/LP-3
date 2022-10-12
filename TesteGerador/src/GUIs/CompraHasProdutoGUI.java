package GUIs;

import Entidades.CompraHasProduto;
import DAOs.DAOCompraHasProduto;
import Entidades.CompraHasProdutoPK;
import Entidades.Compra;
import DAOs.DAOCompra;
import Entidades.Produto;
import DAOs.DAOProduto;
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

 /**
 * @author JoaoAN2 11/10/2022 - 20:55:33
 */

public class CompraHasProdutoGUI extends JDialog {
    String action;

    CompraHasProduto compraHasProduto = new CompraHasProduto();
    DAOCompraHasProduto daoCompraHasProduto = new DAOCompraHasProduto();

    DAOCompra daoCompra = new DAOCompra();
    DefaultComboBoxModel cbCompraModel = new DefaultComboBoxModel();
    JComboBox cbCompra = new JComboBox(cbCompraModel);

    DAOProduto daoProduto = new DAOProduto();
    DefaultComboBoxModel cbProdutoModel = new DefaultComboBoxModel();
    JComboBox cbProduto = new JComboBox(cbProdutoModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Compra Id Compra", "Produto Id Produto", "Quantidade", "Preco Total"};
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

    JLabel lbCompraIdCompra = new JLabel("Compra Id Compra");
    JLabel lbProdutoIdProduto = new JLabel("Produto Id Produto");
    JLabel lbQuantidade = new JLabel("Quantidade");
    JTextField tfQuantidade = new JTextField(45);

    JLabel lbPrecoTotal = new JLabel("Preco Total");
    JTextField tfPrecoTotal = new JTextField(20);

    private List<CompraHasProduto> list = new ArrayList<>();

    public void clear() {
        tfQuantidade.setText("");
        tfPrecoTotal.setText("");
    }

    public void enabled() {
        tfQuantidade.setEditable(true);
        tfPrecoTotal.setEditable(true);
    }

    public void disabled() {
        tfQuantidade.setEditable(false);
        tfPrecoTotal.setEditable(false);
    }


    public CompraHasProdutoGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - CompraHasProduto");

        pnCenter.setLayout(new GridLayout(2, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbCompraIdCompra);
        pnNorth.add(cbCompra);

        pnNorth.add(lbProdutoIdProduto);
        pnNorth.add(cbProduto);

        pnNorth.add(lbQuantidade);
        pnNorth.add(lbPrecoTotal);
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

        pnCenter.add(lbQuantidade);
        pnCenter.add(tfQuantidade);

        pnCenter.add(lbPrecoTotal);
        pnCenter.add(tfPrecoTotal);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Compra> compraList = daoCompra.list();
        for (Compra compra : compraList) {
            cbCompraModel.addElement(compra);
        }

        List<Produto> produtoList = daoProduto.list();
        for (Produto produto : produtoList) {
            cbProdutoModel.addElement(produto);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                CompraHasProdutoPK compraHasProdutoPK = new CompraHasProdutoPK();
                compraHasProdutoPK.setCompraIdCompra(((Compra) cbCompra.getSelectedItem()).getIdCompra());
                compraHasProdutoPK.setProdutoIdProduto(((Produto) cbProduto.getSelectedItem()).getIdProduto());
                compraHasProduto = daoCompraHasProduto.obter(compraHasProdutoPK);

                if (compraHasProduto != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfQuantidade.setText(compraHasProduto.getQuantidade());
                    tfPrecoTotal.setText(String.valueOf(compraHasProduto.getPrecoTotal()));
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

                tfQuantidade.requestFocus();

                cbCompra.setEnabled(false);
                cbProduto.setEnabled(false);
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

                cbCompra.setEnabled(true);
                cbCompra.setEditable(true);

                cbProduto.setEnabled(true);
                cbProduto.setEditable(true);

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoCompraHasProduto.remover(compraHasProduto);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<CompraHasProduto> compraHasProdutoList = daoCompraHasProduto.list();
                String[] col = {"Compra Id Compra", "Produto Id Produto", "Quantidade", "Preco Total"};
                Object[][] data = new Object[compraHasProdutoList.size()][col.length];

                String aux[];

                for (int i = 0; i < compraHasProdutoList.size(); i++) {
                    aux = compraHasProdutoList.get(i).toString().split(";");
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
                    compraHasProduto =  new CompraHasProduto();
                }

                CompraHasProdutoPK compraHasProdutoPK = new CompraHasProdutoPK();
                compraHasProdutoPK.setCompraIdCompra(((Compra) cbCompra.getSelectedItem()).getIdCompra());
                compraHasProdutoPK.setProdutoIdProduto(((Produto) cbProduto.getSelectedItem()).getIdProduto());
                compraHasProduto.setCompraHasProdutoPK(compraHasProdutoPK);
                compraHasProduto.setQuantidade(tfQuantidade.getText());
                compraHasProduto.setPrecoTotal(Double.parseDouble(tfPrecoTotal.getText()));

                if("create".equals(action)){
                    daoCompraHasProduto.inserir(compraHasProduto);
                }

                if("update".equals(action)){
                    daoCompraHasProduto.atualizar(compraHasProduto);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                cbCompra.setEnabled(true);
                cbCompra.setEditable(true);

                cbProduto.setEnabled(true);
                cbProduto.setEditable(true);

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

                cbCompra.setEnabled(false);
                cbCompra.setEditable(false);

                cbProduto.setEnabled(false);
                cbProduto.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                cbCompra.setEnabled(true);
                cbCompra.setEditable(true);

                cbProduto.setEnabled(true);
                cbProduto.setEditable(true);

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
