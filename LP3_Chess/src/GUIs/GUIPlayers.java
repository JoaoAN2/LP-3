package GUIs;

import Entities.Player;
import Controlls.ControllPlayers;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * id; name; sigla_federation; birthday; pointsFIDE;
 *
 * @author JoaoAN2
 */
public class GUIPlayers extends JDialog {

    String path = "chess.csv";

    Player player = new Player();
    String action;

    ControllPlayers controll = new ControllPlayers();
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

    JLabel lbPK = new JLabel("ID FIDE");
    JLabel lbName = new JLabel("Nome");
    JLabel lbFederation = new JLabel("Federação (País)");
    JLabel lbBirthday = new JLabel("Data de Nascimento");
    JLabel lbPoints = new JLabel("Rating FIDE");

    JTextField tfPK = new JTextField(9);
    JTextField tfName = new JTextField(50);

    DefaultComboBoxModel cbFederationModel = new DefaultComboBoxModel();
    JComboBox cbFederation = new JComboBox(cbFederationModel);

    JTextField tfBirthday = new JTextField(10);
    JTextField tfPoints = new JTextField(4);

    JButton btnSearch = new JButton("Buscar");
    JButton btnCreate = new JButton("Adicionar");
    JButton btnSave = new JButton("Salvar");
    JButton btnUpdate = new JButton("Alterar");
    JButton btnDelete = new JButton("Excluir");
    JButton btnList = new JButton("Listar");
    JButton btnCancel = new JButton("Cancelar");

    String[] col = new String[]{"Id", "Nome", "Sigla Federação", "Data de Nascimento", "Pontos"};
    String[][] data = new String[0][5];
    DefaultTableModel model = new DefaultTableModel(data, col);
    JTable table = new JTable(model);
    JScrollPane scrollTable = new JScrollPane();
    JPanel pnEmpty = new JPanel(new GridLayout(6, 1));

    public void printList(List<Player> playersList) {
        for (int i = 0; i < playersList.size(); i++) {
            System.out.println(
                    playersList.get(i).getId() + " - "
                    + playersList.get(i).getName() + " - "
                    + playersList.get(i).getFederation() + " - "
                    + dateTools.conversionDateToString(playersList.get(i).getBirthday()) + " - "
                    + playersList.get(i).getPoints()
            );
        }
    }

    public void clear() {
        tfName.setText("");
        tfBirthday.setText("");
        tfPoints.setText("");
    }

    public void enabled() {
        tfName.setEditable(true);
        cbFederation.setEnabled(true);
        tfBirthday.setEditable(true);
        tfPoints.setEditable(true);
    }

    public void disabled() {
        tfName.setEditable(false);
        cbFederation.setEnabled(false);
        tfBirthday.setEditable(false);
        tfPoints.setEditable(false);
    }

    public GUIPlayers() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Chess");

        pnCenter.setLayout(new GridLayout(4, 2));
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

        pnCenter.add(lbName);
        pnCenter.add(tfName);
        pnCenter.add(lbFederation);
        pnCenter.add(cbFederation);
        pnCenter.add(lbBirthday);
        pnCenter.add(tfBirthday);
        pnCenter.add(lbPoints);
        pnCenter.add(tfPoints);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnWarning, "warning");
        pnSouth.add(pnList, "list");
        pnSouth.add(pnError, "error");

        controll.loadData(path);

        List<String> federations = new ManipulaArquivo().abrirArquivo("federations.csv");
        for (String federation : federations) {
            String[] aux = federation.split(";");
            cbFederationModel.addElement(federation.replace(";", " - "));
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    player = controll.read(Integer.parseInt(tfPK.getText()));
                    if (player != null) {
                        btnCreate.setVisible(false);
                        btnUpdate.setVisible(true);
                        btnDelete.setVisible(true);

                        tfName.setText(player.getName());

                        int i;
                        for (i = 0; i < federations.size(); i++) {
                            if (federations.get(i).split(";")[0].equals(player.getFederation())) {
                                System.out.println("");
                                cbFederation.setSelectedIndex(i);
                                break;
                            }
                        }

                        tfBirthday.setText(dateTools.conversionDateToString(player.getBirthday()));
                        tfPoints.setText(String.valueOf(player.getPoints()));
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
                tfName.requestFocus();

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
                if (dateTools.conversionStringToDate(tfBirthday.getText()) != null) {
                    try {

                        Player oldPlayer = player;

                        if ("create".equals(action)) {
                            player = new Player();
                        }

                        player.setId(Integer.valueOf(tfPK.getText()));
                        player.setName(tfName.getText());
                        player.setFederation(String.valueOf(cbFederation.getSelectedItem()).split(" - ")[0]);
                        player.setBirthday(dateTools.conversionStringToDate(tfBirthday.getText()));
                        player.setPoints(Integer.valueOf(tfPoints.getText()));

                        if ("create".equals(action)) {
                            controll.create(player);
                        }

                        if ("update".equals(action)) {
                            controll.update(player, oldPlayer);
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
                } else {
                    JOptionPane.showMessageDialog(cp, "Problema na data de Nascimento", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                }
                controll.saveData(path);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnSearch.setVisible(false);
                btnUpdate.setVisible(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);

                tfName.requestFocus();

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
                    controll.delete(player);
                    controll.saveData(path);
                }
            }
        });

        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                List<Player> playersList = controll.list();
                String[] col = {"ID", "Nome", "Sigla Federação", "Data de Nascimento", "Pontos"};
                Object[][] data = new Object[playersList.size()][col.length];
                String aux[];

                for (int i = 0; i < playersList.size(); i++) {
                    aux = playersList.get(i).toString().split(";");
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

                System.out.println(player.toString());

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
