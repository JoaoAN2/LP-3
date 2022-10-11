package GUIs;

import Entidades.Player;
import DAOs.DAOPlayer;
import Entidades.Federation;
import DAOs.DAOFederation;
import Entidades.Gender;
import DAOs.DAOGender;
import Entidades.Title;
import DAOs.DAOTitle;
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
 * @author JoaoAN2 10/10/2022 - 23:59:18
 */

public class PlayerGUI extends JDialog {
    String action;

    Player player = new Player();
    DAOPlayer daoPlayer = new DAOPlayer();

    DateTools dt = new DateTools();

    DAOFederation daoFederation = new DAOFederation();
    DefaultComboBoxModel cbFederationModel = new DefaultComboBoxModel();
    JComboBox cbFederation = new JComboBox(cbFederationModel);

    DAOGender daoGender = new DAOGender();
    DefaultComboBoxModel cbGenderModel = new DefaultComboBoxModel();
    JComboBox cbGender = new JComboBox(cbGenderModel);

    DAOTitle daoTitle = new DAOTitle();
    DefaultComboBoxModel cbTitleModel = new DefaultComboBoxModel();
    JComboBox cbTitle = new JComboBox(cbTitleModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id Player", "Name Player", "Points Player", "Birthday Player", "Federation Sigla Player", "Gender Sigla Player", "Title Sigla Player", "Profile Picture Url"};
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

    JLabel lbIdPlayer = new JLabel("Id Player");
    JTextField tfIdPlayer = new JTextField(11);

    JLabel lbNamePlayer = new JLabel("Name Player");
    JTextField tfNamePlayer = new JTextField(100);

    JLabel lbPointsPlayer = new JLabel("Points Player");
    JTextField tfPointsPlayer = new JTextField(11);

    JLabel lbBirthdayPlayer = new JLabel("Birthday Player");
    JTextField tfBirthdayPlayer = new JTextField(10);

    JLabel lbFederationSiglaPlayer = new JLabel("Federation Sigla Player");
    JLabel lbGenderSiglaPlayer = new JLabel("Gender Sigla Player");
    JLabel lbTitleSiglaPlayer = new JLabel("Title Sigla Player");
    JLabel lbProfilePictureUrl = new JLabel("Profile Picture Url");
    JTextField tfProfilePictureUrl = new JTextField(255);

    private List<Player> list = new ArrayList<>();

    public void clear() {
        tfNamePlayer.setText("");
        tfPointsPlayer.setText("");
        tfBirthdayPlayer.setText("");
        tfProfilePictureUrl.setText("");
    }

    public void enabled() {
        tfNamePlayer.setEditable(true);
        tfPointsPlayer.setEditable(true);
        tfBirthdayPlayer.setEditable(true);
        cbFederation.setEnabled(true);
        cbGender.setEnabled(true);
        cbTitle.setEnabled(true);
        tfProfilePictureUrl.setEditable(true);
    }

    public void disabled() {
        tfNamePlayer.setEditable(false);
        tfPointsPlayer.setEditable(false);
        tfBirthdayPlayer.setEditable(false);
        cbFederation.setEnabled(false);
        cbGender.setEnabled(false);
        cbTitle.setEnabled(false);
        tfProfilePictureUrl.setEditable(false);
    }


    public PlayerGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Player");

        pnCenter.setLayout(new GridLayout(7, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdPlayer);
        pnNorth.add(tfIdPlayer);

        pnNorth.add(lbNamePlayer);
        pnNorth.add(lbPointsPlayer);
        pnNorth.add(lbBirthdayPlayer);
        pnNorth.add(lbFederationSiglaPlayer);
        pnNorth.add(lbGenderSiglaPlayer);
        pnNorth.add(lbTitleSiglaPlayer);
        pnNorth.add(lbProfilePictureUrl);
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

        pnCenter.add(lbNamePlayer);
        pnCenter.add(tfNamePlayer);

        pnCenter.add(lbPointsPlayer);
        pnCenter.add(tfPointsPlayer);

        pnCenter.add(lbBirthdayPlayer);
        pnCenter.add(tfBirthdayPlayer);

        pnCenter.add(lbFederationSiglaPlayer);
        pnCenter.add(cbFederation);

        pnCenter.add(lbGenderSiglaPlayer);
        pnCenter.add(cbGender);

        pnCenter.add(lbTitleSiglaPlayer);
        pnCenter.add(cbTitle);

        pnCenter.add(lbProfilePictureUrl);
        pnCenter.add(tfProfilePictureUrl);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Federation> federations = daoFederation.list();
        for (Federation federation : federations) {
            cbFederationModel.addElement(federation);
        }

        List<Gender> genders = daoGender.list();
        for (Gender gender : genders) {
            cbGenderModel.addElement(gender);
        }

        List<Title> titles = daoTitle.list();
        for (Title title : titles) {
            cbTitleModel.addElement(title);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                player = daoPlayer.obter(Integer.valueOf(tfIdPlayer.getText()));

                if (player != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNamePlayer.setText(player.getNamePlayer());
                    tfPointsPlayer.setText(String.valueOf(player.getPointsPlayer()));
                    tfBirthdayPlayer.setText(dt.conversionDateToString(player.getBirthdayPlayer()));
                    cbFederation.setSelectedItem(player.getFederationSiglaPlayer());
                    cbGender.setSelectedItem(player.getGenderSiglaPlayer());
                    cbTitle.setSelectedItem(player.getTitleSiglaPlayer());
                    tfProfilePictureUrl.setText(player.getProfilePictureUrl());
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

                tfNamePlayer.requestFocus();

                tfIdPlayer.setEnabled(false);
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

                tfIdPlayer.setEnabled(true);
                tfIdPlayer.setEditable(true);
                tfIdPlayer.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoPlayer.remover(player);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Player> playerList = daoPlayer.list();
                String[] col = {"Id Player", "Name Player", "Points Player", "Birthday Player", "Federation Sigla Player", "Gender Sigla Player", "Title Sigla Player", "Profile Picture Url"};
                Object[][] data = new Object[playerList.size()][col.length];

                String aux[];

                for (int i = 0; i < playerList.size(); i++) {
                    aux = playerList.get(i).toString().split(";");
                    for (int j = 0; j < col.length; j++) {
                        try {
                            data[i][j] = aux[j] == null ? "null" : aux[j];
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
                    player =  new Player();
                }

                player.setIdPlayer(Integer.valueOf(tfIdPlayer.getText()));
                player.setNamePlayer(tfNamePlayer.getText());
                player.setPointsPlayer(Integer.valueOf(tfPointsPlayer.getText()));
                player.setBirthdayPlayer(dt.conversionStringToDate(tfBirthdayPlayer.getText()));
                player.setFederationSiglaPlayer((Federation) cbFederation.getSelectedItem());
                player.setGenderSiglaPlayer((Gender) cbGender.getSelectedItem());
                player.setTitleSiglaPlayer((Title) cbTitle.getSelectedItem());
                player.setProfilePictureUrl(tfProfilePictureUrl.getText());

                if("create".equals(action)){
                    daoPlayer.inserir(player);
                }

                if("update".equals(action)){
                    daoPlayer.atualizar(player);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdPlayer.setEnabled(true);
                tfIdPlayer.setEditable(true);

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

                tfIdPlayer.setEnabled(false);
                tfIdPlayer.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdPlayer.setEnabled(true);
                tfIdPlayer.setEditable(true);
                tfIdPlayer.setText("");

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
