package GUIs;

import Entidades.Federation;
import Tools.ManipulaArquivo;
import DAOs.DAOFederation;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @author JoaoAN2 26/04/2022 - 18:06:27
 */
public class FederationGUI extends JDialog {

    Federation federation = new Federation();
    DAOFederation daoFederation = new DAOFederation();
    ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    String action;

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1, 1));

    String[] col = new String[]{"Sigla da Federação", "Nome da Federação"};
    String[][] data = new String[0][col.length];
    DefaultTableModel model = new DefaultTableModel(data, col);

    CardLayout cardLayout = new CardLayout();
    JTable table = new JTable(model);
    JScrollPane scrollTable = new JScrollPane();
    JPanel pnEmpty = new JPanel(new GridLayout(6, 1));
    JButton btnSearch = new JButton("Buscar");
    JButton btnCreate = new JButton("Adicionar");
    JButton btnSave = new JButton("Salvar");
    JButton btnUpdate = new JButton("Alterar");
    JButton btnDelete = new JButton("Excluir");
    JButton btnList = new JButton("Listar");
    JButton btnCancel = new JButton("Cancelar");

    JLabel lbSiglaFederation = new JLabel("Sigla da Federação");
    JTextField tfSiglaFederation = new JTextField(3);

    JLabel lbNameFederation = new JLabel("Nome da Federação");
    JTextField tfNameFederation = new JTextField(45);

    private List<Federation> list = new ArrayList<>();

    public void clear() {
        tfNameFederation.setText("");
    }

    public void enabled() {
        tfNameFederation.setEditable(true);
    }

    public void disabled() {
        tfNameFederation.setEditable(false);
    }

    public FederationGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Federação");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorth.add(lbSiglaFederation);
        pnNorth.add(tfSiglaFederation);
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

        pnCenter.add(lbNameFederation);
        pnCenter.add(tfNameFederation);

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
                if (tfSiglaFederation.getText().trim().length() == 3) {
                    federation = daoFederation.obter(tfSiglaFederation.getText());
                    if (federation != null) {
                        btnCreate.setVisible(false);
                        btnUpdate.setVisible(true);
                        btnDelete.setVisible(true);

                        tfNameFederation.setText(federation.getNameFederation());
                    } else {
                        clear();
                        btnCreate.setVisible(true);
                        btnUpdate.setVisible(false);
                        btnDelete.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(cp, "Insira uma sigla de 3 caracteres!", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                    tfSiglaFederation.selectAll();
                    tfSiglaFederation.requestFocus();
                }
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tfNameFederation.requestFocus();
                tfSiglaFederation.setEnabled(false);
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
                if (!tfNameFederation.getText().trim().equals("")) {
                    if ("create".equals(action)) {
                        federation = new Federation();
                    }

                    federation.setSiglaFederation(tfSiglaFederation.getText());
                    federation.setNameFederation(tfNameFederation.getText());

                    if ("create".equals(action)) {
                        daoFederation.inserir(federation);
                    }

                    if ("update".equals(action)) {
                        daoFederation.atualizar(federation);
                    }

                    btnSearch.setVisible(true);
                    btnList.setVisible(true);
                    btnSave.setVisible(false);
                    btnCancel.setVisible(false);
                    btnDelete.setVisible(false);

                    tfSiglaFederation.setEnabled(true);
                    tfSiglaFederation.setEditable(true);
                    tfSiglaFederation.requestFocus();

                    clear();
                    disabled();

                } else {
                    JOptionPane.showMessageDialog(cp, "Dados inseridos de maneira inválida!", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                }

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
                tfNameFederation.requestFocus();
                tfSiglaFederation.setEditable(false);
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
                tfSiglaFederation.setEnabled(true);
                tfSiglaFederation.setEditable(true);
                tfSiglaFederation.requestFocus();
                tfSiglaFederation.setText("");
                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if (response == JOptionPane.YES_OPTION) {
                    daoFederation.remover(federation);
                }

            }
        });

        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Federation> federationList = daoFederation.list();
                String[] col = {"Sigla da Federação", "Nome da Federação"};
                Object[][] data = new Object[federationList.size()][col.length];
                String aux[];

                for (int i = 0; i < federationList.size(); i++) {
                    aux = federationList.get(i).toString().split(";");
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
                tfSiglaFederation.setText("");
                tfSiglaFederation.requestFocus();
                tfSiglaFederation.setEnabled(true);
                tfSiglaFederation.setEditable(true);

                clear();
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
        setSize(600, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
