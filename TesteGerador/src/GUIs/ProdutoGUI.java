package GUIs;

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

 /**
 * @author JoaoAN2 11/10/2022 - 20:55:34
 */

public class ProdutoGUI extends JDialog {
    String action;

    Produto produto = new Produto();
    DAOProduto daoProduto = new DAOProduto();

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id Produto", "Nome Produto", "Estoque Produto", "Preco Unitario Produto"};
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

    JLabel lbIdProduto = new JLabel("Id Produto");
    JTextField tfIdProduto = new JTextField(11);

    JLabel lbNomeProduto = new JLabel("Nome Produto");
    JTextField tfNomeProduto = new JTextField(45);

    JLabel lbEstoqueProduto = new JLabel("Estoque Produto");
    JTextField tfEstoqueProduto = new JTextField(11);

    JLabel lbPrecoUnitarioProduto = new JLabel("Preco Unitario Produto");
    JTextField tfPrecoUnitarioProduto = new JTextField(20);

    private List<Produto> list = new ArrayList<>();

    public void clear() {
        tfNomeProduto.setText("");
        tfEstoqueProduto.setText("");
        tfPrecoUnitarioProduto.setText("");
    }

    public void enabled() {
        tfNomeProduto.setEditable(true);
        tfEstoqueProduto.setEditable(true);
        tfPrecoUnitarioProduto.setEditable(true);
    }

    public void disabled() {
        tfNomeProduto.setEditable(false);
        tfEstoqueProduto.setEditable(false);
        tfPrecoUnitarioProduto.setEditable(false);
    }


    public ProdutoGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Produto");

        pnCenter.setLayout(new GridLayout(3, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdProduto);
        pnNorth.add(tfIdProduto);

        pnNorth.add(lbNomeProduto);
        pnNorth.add(lbEstoqueProduto);
        pnNorth.add(lbPrecoUnitarioProduto);
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

        pnCenter.add(lbNomeProduto);
        pnCenter.add(tfNomeProduto);

        pnCenter.add(lbEstoqueProduto);
        pnCenter.add(tfEstoqueProduto);

        pnCenter.add(lbPrecoUnitarioProduto);
        pnCenter.add(tfPrecoUnitarioProduto);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                produto = daoProduto.obter(Integer.valueOf(tfIdProduto.getText()));

                if (produto != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNomeProduto.setText(produto.getNomeProduto());
                    tfEstoqueProduto.setText(String.valueOf(produto.getEstoqueProduto()));
                    tfPrecoUnitarioProduto.setText(String.valueOf(produto.getPrecoUnitarioProduto()));
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

                tfNomeProduto.requestFocus();

                tfIdProduto.setEnabled(false);
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

                tfIdProduto.setEnabled(true);
                tfIdProduto.setEditable(true);
                tfIdProduto.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoProduto.remover(produto);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Produto> produtoList = daoProduto.list();
                String[] col = {"Id Produto", "Nome Produto", "Estoque Produto", "Preco Unitario Produto"};
                Object[][] data = new Object[produtoList.size()][col.length];

                String aux[];

                for (int i = 0; i < produtoList.size(); i++) {
                    aux = produtoList.get(i).toString().split(";");
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
                    produto =  new Produto();
                }

                produto.setIdProduto(Integer.valueOf(tfIdProduto.getText()));
                produto.setNomeProduto(tfNomeProduto.getText());
                produto.setEstoqueProduto(Integer.valueOf(tfEstoqueProduto.getText()));
                produto.setPrecoUnitarioProduto(Double.parseDouble(tfPrecoUnitarioProduto.getText()));

                if("create".equals(action)){
                    daoProduto.inserir(produto);
                }

                if("update".equals(action)){
                    daoProduto.atualizar(produto);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdProduto.setEnabled(true);
                tfIdProduto.setEditable(true);

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

                tfIdProduto.setEnabled(false);
                tfIdProduto.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdProduto.setEnabled(true);
                tfIdProduto.setEditable(true);
                tfIdProduto.setText("");

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
