package GUIs;

import Entidades.Tournaments;
import DAOs.DAOTournaments;
import Entidades.Tournament;
import DAOs.DAOTournament;
import Entidades.Player;
import DAOs.DAOPlayer;
import Entidades.TournamentsPK;
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
 * @author JoaoAN2 24/09/2022 - 22:14:39
 */

public class TournamentsGUI extends JDialog {
    Tournaments tournaments = new Tournaments();
    DAOTournaments daoTournaments = new DAOTournaments();
    String action;

    DAOTournament daoTournament = new DAOTournament();
    DefaultComboBoxModel cbTournamentModel = new DefaultComboBoxModel();
    JComboBox cbTournament = new JComboBox(cbTournamentModel);

    DAOPlayer daoPlayer = new DAOPlayer();
    DefaultComboBoxModel cbPlayerModel = new DefaultComboBoxModel();
    JComboBox cbPlayer = new JComboBox(cbPlayerModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Tournaments Id Tournament", "Tournaments Id Player", "Position Player", "Points Player"};
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

    JLabel lbTournamentsIdTournament = new JLabel("Tournaments Id Tournament");
    JLabel lbTournamentsIdPlayer = new JLabel("Tournaments Id Player");
    JLabel lbPositionPlayer = new JLabel("Position Player");
    JTextField tfPositionPlayer = new JTextField(11);

    JLabel lbPointsPlayer = new JLabel("Points Player");
    JTextField tfPointsPlayer = new JTextField(11);

    private List<Tournaments> list = new ArrayList<>();

    public void clear() {
        tfPointsPlayer.setText("");
    }

    public void enabled() {
        tfPointsPlayer.setEditable(true);
    }

    public void disabled() {
        tfPointsPlayer.setEditable(false);
    }


    public TournamentsGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Tournaments");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbTournamentsIdTournament);
        pnNorth.add(cbTournament);

        pnNorth.add(lbTournamentsIdPlayer);
        pnNorth.add(cbPlayer);

        pnNorth.add(lbPositionPlayer);
        pnNorth.add(tfPositionPlayer);

        pnNorth.add(lbPointsPlayer);
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

        pnCenter.add(lbPointsPlayer);
        pnCenter.add(tfPointsPlayer);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Tournament> tournamentsList = daoTournament.list();
        for (Tournament tournament : tournamentsList) {
            cbTournamentModel.addElement(tournament);
        }

        List<Player> players = daoPlayer.list();
        for (Player player : players) {
            cbPlayerModel.addElement(player);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                TournamentsPK tournamentsPK = new TournamentsPK();
                tournamentsPK.setTournamentsIdPlayer(((Player) cbPlayer.getSelectedItem()).getIdPlayer());
                tournamentsPK.setTournamentsIdTournament(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                tournamentsPK.setPositionPlayer(Integer.parseInt(tfPositionPlayer.getText()));
                
                tournaments = daoTournaments.obter(tournamentsPK);
                
                if (tournaments != null) {
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    btnDelete.setVisible(true);

                    tfPointsPlayer.setText(String.valueOf(tournaments.getPointsPlayer()));
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

                tfPointsPlayer.requestFocus();

                cbTournament.setEnabled(false);
                cbPlayer.setEnabled(false);
                tfPositionPlayer.setEnabled(false);
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
                    tournaments =  new Tournaments();
                }
                
                TournamentsPK tournamentsPK = new TournamentsPK();
                tournamentsPK.setTournamentsIdTournament(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                tournamentsPK.setTournamentsIdPlayer(((Player) cbPlayer.getSelectedItem()).getIdPlayer());
                tournamentsPK.setPositionPlayer(Integer.valueOf(tfPositionPlayer.getText()));
                tournaments.setTournamentsPK(tournamentsPK);
                tournaments.setPointsPlayer(Integer.valueOf(tfPointsPlayer.getText()));

                if("create".equals(action)){
                    daoTournaments.inserir(tournaments);
                }

                if("update".equals(action)){
                    daoTournaments.atualizar(tournaments);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                cbTournament.setEnabled(true);
                cbTournament.setEditable(true);

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

                tfPositionPlayer.setEnabled(true);
                tfPositionPlayer.setEditable(true);

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

                cbPlayer.setEnabled(false);
                cbPlayer.setEditable(false);

                tfPositionPlayer.setEnabled(false);
                tfPositionPlayer.setEditable(false);

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

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

                tfPositionPlayer.setEnabled(true);
                tfPositionPlayer.setEditable(true);
                tfPositionPlayer.setText("");

                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);
                btnSearch.setVisible(true);
                if(response == JOptionPane.YES_OPTION) {
                    daoTournaments.remover(tournaments);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Tournaments> tournamentsList = daoTournaments.list();
                String[] col = {"Tournaments Id Tournament", "Tournaments Id Player", "Position Player", "Points Player"};
                Object[][] data = new Object[tournamentsList.size()][col.length];
                String aux[];

                for (int i = 0; i < tournamentsList.size(); i++) {
                    aux = tournamentsList.get(i).toString().split(";");
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

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

                tfPositionPlayer.setEnabled(true);
                tfPositionPlayer.setEditable(true);
                tfPositionPlayer.setText("");

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
