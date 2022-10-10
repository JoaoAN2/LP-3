package GUIs;

import Entidades.Arbitration;
import DAOs.DAOArbitration;
import Entidades.Tournament;
import DAOs.DAOTournament;
import Entidades.Referee;
import DAOs.DAOReferee;
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
 * @author JoaoAN2 01/10/2022 - 18:59:38
 */

public class ArbitrationGUI extends JDialog {
    Arbitration arbitration = new Arbitration();
    DAOArbitration daoArbitration = new DAOArbitration();
    String action;

    DAOTournament daoTournament = new DAOTournament();
    DefaultComboBoxModel cbTournamentModel = new DefaultComboBoxModel();
    JComboBox cbTournament = new JComboBox(cbTournamentModel);

    DAOReferee daoReferee = new DAOReferee();
    DefaultComboBoxModel cbRefereeModel = new DefaultComboBoxModel();
    JComboBox cbReferee = new JComboBox(cbRefereeModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Tournament Id Tournament", "Referee Player Id Player"};
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

    JLabel lbTournamentIdTournament = new JLabel("Tournament Id Tournament");
    JLabel lbRefereePlayerIdPlayer = new JLabel("Referee Player Id Player");
    private List<Arbitration> list = new ArrayList<>();

    public void clear() {
    }

    public void enabled() {
    }

    public void disabled() {
    }


    public ArbitrationGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Arbitration");

        pnCenter.setLayout(new GridLayout(0, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbTournamentIdTournament);
        pnNorth.add(cbTournament);

        pnNorth.add(lbRefereePlayerIdPlayer);
        pnNorth.add(cbReferee);

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

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Tournament> tournaments = daoTournament.list();
        for (Tournament tournament : tournaments) {
            cbTournamentModel.addElement(tournament);
        }

        List<Referee> referees = daoReferee.list();
        for (Referee referee : referees) {
            cbRefereeModel.addElement(referee);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                arbitration = daoArbitration.obter(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                arbitration = daoArbitration.obter(((Referee) cbReferee.getSelectedItem()).getPlayerIdPlayer());

                if (arbitration != null) {
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    btnDelete.setVisible(true);

                    cbReferee.setSelectedItem(arbitration.getRefereePlayerIdPlayer());
                } else {
                    clear();
                    btnCreate.setVisible(true);
                    btnUpdate.setVisible(false);
                    btnDelete.setVisible(false);
                }
            }
        });

       btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                cbTournament.setEnabled(false);
                cbReferee.setEnabled(false);
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
                if("create".equals(action)) {
                    arbitration =  new Arbitration();
                }

                arbitration.setTournamentIdTournament(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                arbitration.setRefereePlayerIdPlayer(((Referee) cbReferee.getSelectedItem()).getPlayerIdPlayer());

                if("create".equals(action)){
                    daoArbitration.inserir(arbitration);
                }

                if("update".equals(action)){
                    daoArbitration.atualizar(arbitration);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                cbTournament.setEnabled(true);
                cbTournament.setEditable(true);

                cbReferee.setEnabled(true);
                cbReferee.setEditable(true);

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

                cbTournament.setEnabled(false);
                cbTournament.setEditable(false);

                cbReferee.setEnabled(false);
                cbReferee.setEditable(false);

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

                cbTournament.setEnabled(true);
                cbTournament.setEditable(true);

                cbReferee.setEnabled(true);
                cbReferee.setEditable(true);

                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);
                btnSearch.setVisible(true);
                if(response == JOptionPane.YES_OPTION) {
                    daoArbitration.remover(arbitration);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Arbitration> arbitrationList = daoArbitration.list();
                String[] col = {"Tournament Id Tournament", "Referee Player Id Player"};
                Object[][] data = new Object[arbitrationList.size()][col.length];
                String aux[];

                for (int i = 0; i < arbitrationList.size(); i++) {
                    aux = arbitrationList.get(i).toString().split(";");
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

                cbTournament.setEnabled(true);
                cbTournament.setEditable(true);

                cbReferee.setEnabled(true);
                cbReferee.setEditable(true);

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
