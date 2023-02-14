package GUIs;

import Entidades.Tournament;
import DAOs.DAOTournament;
import Entidades.City;
import DAOs.DAOCity;
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
 * @author Usuário Teste 13/02/2023 - 20:04:49
 */

public class TournamentGUI extends JDialog {
    String action;    Color mainColor = new Color(102, 102, 0);

    Tournament tournament = new Tournament();
    DAOTournament daoTournament = new DAOTournament();

    DateTools dt = new DateTools();

    DAOCity daoCity = new DAOCity();
    DefaultComboBoxModel cbCityModel = new DefaultComboBoxModel();
    JComboBox cbCity = new JComboBox(cbCityModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id Tournament", "Start Date Tournament", "End Date Tournament", "Rounds Tournament", "City Id Tournament"};
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

    JLabel lbIdTournament = new JLabel("Id Tournament");
    JTextField tfIdTournament = new JTextField(11);

    JLabel lbStartDateTournament = new JLabel("Start Date Tournament");
    JTextField tfStartDateTournament = new JTextField(10);

    JLabel lbEndDateTournament = new JLabel("End Date Tournament");
    JTextField tfEndDateTournament = new JTextField(10);

    JLabel lbRoundsTournament = new JLabel("Rounds Tournament");
    JTextField tfRoundsTournament = new JTextField(11);

    JLabel lbCityIdTournament = new JLabel("City Id Tournament");
    private List<Tournament> list = new ArrayList<>();

    public void clear() {
        tfStartDateTournament.setText("");
        tfEndDateTournament.setText("");
        tfRoundsTournament.setText("");
    }

    public void enabled() {
        tfStartDateTournament.setEditable(true);
        tfEndDateTournament.setEditable(true);
        tfRoundsTournament.setEditable(true);
        cbCity.setEnabled(true);
    }

    public void disabled() {
        tfStartDateTournament.setEditable(false);
        tfEndDateTournament.setEditable(false);
        tfRoundsTournament.setEditable(false);
        cbCity.setEnabled(false);
    }


    public TournamentGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Tournament");

        pnCenter.setLayout(new GridLayout(4, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(mainColor);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdTournament);
        pnNorth.add(tfIdTournament);

        pnNorth.add(lbStartDateTournament);
        pnNorth.add(lbEndDateTournament);
        pnNorth.add(lbRoundsTournament);
        pnNorth.add(lbCityIdTournament);
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

        pnCenter.add(lbStartDateTournament);
        pnCenter.add(tfStartDateTournament);

        pnCenter.add(lbEndDateTournament);
        pnCenter.add(tfEndDateTournament);

        pnCenter.add(lbRoundsTournament);
        pnCenter.add(tfRoundsTournament);

        pnCenter.add(lbCityIdTournament);
        pnCenter.add(cbCity);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<City> cityList = daoCity.list();
        for (City city : cityList) {
            cbCityModel.addElement(city);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                tournament = daoTournament.obter(Integer.valueOf(tfIdTournament.getText()));

                if (tournament != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfStartDateTournament.setText(dt.conversionDateToString(tournament.getStartDateTournament()));
                    tfEndDateTournament.setText(dt.conversionDateToString(tournament.getEndDateTournament()));
                    tfRoundsTournament.setText(String.valueOf(tournament.getRoundsTournament()));
                    cbCity.setSelectedItem(tournament.getCityIdTournament());
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

                tfStartDateTournament.requestFocus();

                tfIdTournament.setEnabled(false);
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

                tfIdTournament.setEnabled(true);
                tfIdTournament.setEditable(true);
                tfIdTournament.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoTournament.remover(tournament);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Tournament> tournamentList = daoTournament.list();
                String[] col = {"Id Tournament", "Start Date Tournament", "End Date Tournament", "Rounds Tournament", "City Id Tournament"};
                Object[][] data = new Object[tournamentList.size()][col.length];

                String aux[];

                for (int i = 0; i < tournamentList.size(); i++) {
                    aux = tournamentList.get(i).toString().split(";");
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
                    tournament =  new Tournament();
                }

                tournament.setIdTournament(Integer.valueOf(tfIdTournament.getText()));
                tournament.setStartDateTournament(dt.conversionStringToDate(tfStartDateTournament.getText()));
                tournament.setEndDateTournament(dt.conversionStringToDate(tfEndDateTournament.getText()));
                tournament.setRoundsTournament(Integer.valueOf(tfRoundsTournament.getText()));
                tournament.setCityIdTournament((City) cbCity.getSelectedItem());

                if("create".equals(action)){
                    daoTournament.inserir(tournament);
                }

                if("update".equals(action)){
                    daoTournament.atualizar(tournament);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdTournament.setEnabled(true);
                tfIdTournament.setEditable(true);

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

                tfIdTournament.setEnabled(false);
                tfIdTournament.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdTournament.setEnabled(true);
                tfIdTournament.setEditable(true);
                tfIdTournament.setText("");

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
