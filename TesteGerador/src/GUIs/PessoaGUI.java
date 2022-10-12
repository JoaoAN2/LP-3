package GUIs;

import Entidades.Pessoa;
import DAOs.DAOPessoa;
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
import Tools.DateTools;

 /**
 * @author JoaoAN2 11/10/2022 - 20:55:34
 */

public class PessoaGUI extends JDialog {
    String action;

    Pessoa pessoa = new Pessoa();
    DAOPessoa daoPessoa = new DAOPessoa();

    DateTools dt = new DateTools();

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id Pessoa", "Nome Pessoa", "Data Nascimento Pessoa"};
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

    JLabel lbIdPessoa = new JLabel("Id Pessoa");
    JTextField tfIdPessoa = new JTextField(11);

    JLabel lbNomePessoa = new JLabel("Nome Pessoa");
    JTextField tfNomePessoa = new JTextField(45);

    JLabel lbDataNascimentoPessoa = new JLabel("Data Nascimento Pessoa");
    JTextField tfDataNascimentoPessoa = new JTextField(10);

    private List<Pessoa> list = new ArrayList<>();

    public void clear() {
        tfNomePessoa.setText("");
        tfDataNascimentoPessoa.setText("");
    }

    public void enabled() {
        tfNomePessoa.setEditable(true);
        tfDataNascimentoPessoa.setEditable(true);
    }

    public void disabled() {
        tfNomePessoa.setEditable(false);
        tfDataNascimentoPessoa.setEditable(false);
    }


    public PessoaGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Pessoa");

        pnCenter.setLayout(new GridLayout(2, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdPessoa);
        pnNorth.add(tfIdPessoa);

        pnNorth.add(lbNomePessoa);
        pnNorth.add(lbDataNascimentoPessoa);
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

        pnCenter.add(lbNomePessoa);
        pnCenter.add(tfNomePessoa);

        pnCenter.add(lbDataNascimentoPessoa);
        pnCenter.add(tfDataNascimentoPessoa);

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

                pessoa = daoPessoa.obter(Integer.valueOf(tfIdPessoa.getText()));

                if (pessoa != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNomePessoa.setText(pessoa.getNomePessoa());
                    tfDataNascimentoPessoa.setText(dt.conversionDateToString(pessoa.getDataNascimentoPessoa()));
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

                tfNomePessoa.requestFocus();

                tfIdPessoa.setEnabled(false);
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

                tfIdPessoa.setEnabled(true);
                tfIdPessoa.setEditable(true);
                tfIdPessoa.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoPessoa.remover(pessoa);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Pessoa> pessoaList = daoPessoa.list();
                String[] col = {"Id Pessoa", "Nome Pessoa", "Data Nascimento Pessoa"};
                Object[][] data = new Object[pessoaList.size()][col.length];

                String aux[];

                for (int i = 0; i < pessoaList.size(); i++) {
                    aux = pessoaList.get(i).toString().split(";");
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
                    pessoa =  new Pessoa();
                }

                pessoa.setIdPessoa(Integer.valueOf(tfIdPessoa.getText()));
                pessoa.setNomePessoa(tfNomePessoa.getText());
                pessoa.setDataNascimentoPessoa(dt.conversionStringToDate(tfDataNascimentoPessoa.getText()));

                if("create".equals(action)){
                    daoPessoa.inserir(pessoa);
                }

                if("update".equals(action)){
                    daoPessoa.atualizar(pessoa);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdPessoa.setEnabled(true);
                tfIdPessoa.setEditable(true);

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

                tfIdPessoa.setEnabled(false);
                tfIdPessoa.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdPessoa.setEnabled(true);
                tfIdPessoa.setEditable(true);
                tfIdPessoa.setText("");

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
