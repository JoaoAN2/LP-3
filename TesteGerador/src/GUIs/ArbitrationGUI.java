package GUIs;

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
 * @author JoaoAN2 10/10/2022 - 23:59:18
 */

public class ArbitrationGUI extends JDialog {
    String action;

    Tournament tournament = new Tournament();
    DAOTournament daoTournament = new DAOTournament();
    DefaultComboBoxModel cbTournamentModel = new DefaultComboBoxModel();
    JComboBox cbTournament = new JComboBox(cbTournamentModel);

    Referee referee = new Referee();
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
    JButton btnDelete = new JButton("Excluir");
    JButton btnList = new JButton("Listar");
    JLabel lbTournamentIdTournament = new JLabel("Tournament Id Tournament");
    JLabel lbRefereePlayerIdPlayer = new JLabel("Referee Player Id Player");

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
        pnNorth.add(btnDelete);
        btnCreate.setVisible(false);
        btnDelete.setVisible(false);

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

                referee = daoReferee.obter(((Referee) cbReferee.getSelectedItem()).getPlayerIdPlayer());
                tournament = daoTournament.obter(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                List<Tournament> refereeHasTournament = referee.getTournamentList();

                if (refereeHasTournament.contains((Tournament) tournament)) {
                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                } else {
                    btnCreate.setVisible(true);
                    btnDelete.setVisible(false);
                }
            }
        });

       btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                btnCreate.setVisible(false);
                action = "create";
                btnSearch.setVisible(true);
                btnList.setVisible(true);
                referee = daoReferee.obter(((Referee) cbReferee.getSelectedItem()).getPlayerIdPlayer());
                tournament = daoTournament.obter(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                List<Tournament> refereeHasTournament = referee.getTournamentList();
                refereeHasTournament.add(tournament);
                referee.setTournamentList(refereeHasTournament);
                daoReferee.atualizar(referee);
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

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                if(response == JOptionPane.YES_OPTION) {

                referee = daoReferee.obter(((Referee) cbReferee.getSelectedItem()).getPlayerIdPlayer());
                tournament = daoTournament.obter(((Tournament) cbTournament.getSelectedItem()).getIdTournament());
                List<Tournament> refereeHasTournament = referee.getTournamentList();
                refereeHasTournament.remove(tournament);
                referee.setTournamentList(refereeHasTournament);
                daoReferee.atualizar(referee);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Referee> refereeList = daoReferee.list();
                String[] col = {"Tournament Id Tournament", "Referee Player Id Player"};
                Object[][] data = new Object[refereeList.size()][col.length];

                for (Referee refereeAux : refereeList) {
                    List<Tournament> refereeHasTournaments = daoReferee.obter(refereeAux.getPlayerIdPlayer()).getTournamentList();
                    for (int i = 0; i < refereeHasTournaments.size(); i++) {
                        data[i][0] = refereeHasTournaments.get(i).getIdTournament();
                        data[i][1] = refereeAux.getPlayerIdPlayer();
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

            }
        });

        setModal(true);
        setSize(600,250);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
