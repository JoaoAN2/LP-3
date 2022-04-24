package GUIs;

import Entidades.Pessoa;
import DAOs.DAOPessoa;
import Tools.DateTools;
import Tools.ManipulaArquivo;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JoaoAN2
 */
public class GUIPessoa extends JDialog {

    Pessoa pessoa = new Pessoa();
    String action;

    DAOPessoa daoPessoa = new DAOPessoa();
    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    DateTools dateTools = new DateTools();

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();

    JPanel pnError = new JPanel();
    JPanel pnWarning = new JPanel(new GridLayout(1, 1));
    JPanel pnList = new JPanel(new GridLayout(1, 1));
    CardLayout cardLayout = new CardLayout();

    JLabel lbPK = new JLabel("ID Pessoa");
    JLabel lbNome = new JLabel("Nome");
    JLabel lbAltura = new JLabel("Altura");

    JTextField tfPK = new JTextField(9);
    JTextField tfNome = new JTextField(50);
    JTextField tfAltura = new JTextField(4);

    JButton btnSearch = new JButton("Buscar");
    JButton btnCreate = new JButton("Adicionar");
    JButton btnSave = new JButton("Salvar");
    JButton btnUpdate = new JButton("Alterar");
    JButton btnDelete = new JButton("Excluir");
    JButton btnList = new JButton("Listar");
    JButton btnCancel = new JButton("Cancelar");

    String[] col = new String[]{"Id", "Nome", "Altura"};
    String[][] data = new String[0][5];
    DefaultTableModel model = new DefaultTableModel(data, col);
    JTable table = new JTable(model);
    JScrollPane scrollTable = new JScrollPane();
    JPanel pnEmpty = new JPanel(new GridLayout(6, 1));

    public void printList(List<Pessoa> pessoasList) {
        for (int i = 0; i < pessoasList.size(); i++) {
            System.out.println(
                    pessoasList.get(i).getIdPessoa()+ " - "
                    + pessoasList.get(i).getNomePessoa()+ " - "
                    + pessoasList.get(i).getAlturaPessoa()
            );
        }
    }

    public void clear() {
        tfNome.setText("");
        tfAltura.setText("");
    }

    public void enabled() {
        tfNome.setEditable(true);
        tfAltura.setEditable(true);
    }

    public void disabled() {
        tfNome.setEditable(false);
        tfAltura.setEditable(false);
    }

    public GUIPessoa() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Pessoa");

        pnCenter.setLayout(new GridLayout(2, 2));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorth.add(lbPK);
        pnNorth.add(tfPK);
        pnNorth.add(btnSearch);
        pnNorth.add(btnList);
        pnNorth.add(btnCreate);
        pnNorth.add(btnUpdate);
        pnNorth.add(btnDelete);
        pnNorth.add(btnSave);
        pnNorth.add(btnCancel);

        btnCreate.setVisible(false);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);

        disabled();

        pnCenter.add(lbNome);
        pnCenter.add(tfNome);
        pnCenter.add(lbAltura);
        pnCenter.add(tfAltura);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnWarning, "warning");
        pnSouth.add(pnList, "list");
        pnSouth.add(pnError, "error");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    pessoa = daoPessoa.obter(Integer.parseInt(tfPK.getText()));
                    if (pessoa != null) {
                        btnCreate.setVisible(false);
                        btnUpdate.setVisible(true);
                        btnDelete.setVisible(true);

                        tfNome.setText(pessoa.getNomePessoa());

                        tfAltura.setText(String.valueOf(pessoa.getAlturaPessoa()));
                    } else {
                        clear();
                        btnCreate.setVisible(true);
                        btnUpdate.setVisible(false);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(cp, "Insira um valor válido!", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                    tfPK.selectAll();
                    tfPK.requestFocus();
                }
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tfNome.requestFocus();

                tfPK.setEnabled(false);
                enabled();

                btnSearch.setVisible(false);
                btnCreate.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);
                btnList.setVisible(false);

                action = "create";
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {

                    Pessoa oldPessoa = pessoa;

                    if ("create".equals(action)) {
                        pessoa = new Pessoa();
                    }

                    pessoa.setIdPessoa(Integer.valueOf(tfPK.getText()));
                    pessoa.setNomePessoa(tfNome.getText());
                    pessoa.setAlturaPessoa(Double.parseDouble(tfAltura.getText()));

                    if ("create".equals(action)) {
                        daoPessoa.inserir(pessoa);
                    }

                    if ("update".equals(action)) {
                        daoPessoa.atualizar(pessoa);
                    }

                    btnList.setVisible(true);
                    btnSearch.setVisible(true);
                    btnSave.setVisible(false);
                    btnCancel.setVisible(false);
                    btnDelete.setVisible(false);

                    tfPK.setEnabled(true);
                    tfPK.setEditable(true);
                    tfPK.requestFocus();
                    clear();
                    disabled();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(cp, "Dados inseridos de maneira inválida!", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSearch.setVisible(false);
                btnUpdate.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);

                tfNome.requestFocus();

                tfPK.setEditable(false);
                enabled();

                action = "update";
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

                tfPK.setEnabled(true);
                tfPK.setEditable(true);
                tfPK.requestFocus();
                tfPK.setText("");
                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if (response == JOptionPane.YES_OPTION) {
                    daoPessoa.remover(pessoa);
                }
            }
        });

        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                List<Pessoa> pessoasList = daoPessoa.list();
                String[] col = {"ID", "Nome", "Altura"};
                Object[][] data = new Object[pessoasList.size()][col.length];
                String aux[];

                for (int i = 0; i < pessoasList.size(); i++) {
                    aux = pessoasList.get(i).toString().split(";");
                    for (int j = 0; j < col.length; j++) {
                        data[i][j] = aux[j];
                    }
                }

                cardLayout.show(pnSouth, "list");
                scrollTable.setPreferredSize(table.getPreferredSize());
                pnList.add(table);
                pnList.add(scrollTable);
                scrollTable.setViewportView(table);
                model.setDataVector(data, col);

                btnCreate.setVisible(false);
                btnUpdate.setVisible(false);
                btnDelete.setVisible(false);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tfPK.setText("");
                tfPK.requestFocus();
                tfPK.setEnabled(true);
                tfPK.setEditable(true);

                disabled();

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
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
