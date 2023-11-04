package GUIs;

import Entidades.Referee;
import DAOs.DAOReferee;
import Entidades.Player;
import DAOs.DAOPlayer;
import Entidades.CatReferee;
import DAOs.DAOCatReferee;
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
 * @author JoaoAN2 04/11/2023 - 14:13:47
 */

public class RefereeGUI extends JDialog {
    String action;    Color mainColor = new Color(102, 255, 255);

    Referee referee = new Referee();
    DAOReferee daoReferee = new DAOReferee();

    DAOPlayer daoPlayer = new DAOPlayer();
    DefaultComboBoxModel cbPlayerModel = new DefaultComboBoxModel();
    JComboBox cbPlayer = new JComboBox(cbPlayerModel);

    DAOCatReferee daoCatReferee = new DAOCatReferee();
    DefaultComboBoxModel cbCatRefereeModel = new DefaultComboBoxModel();
    JComboBox cbCatReferee = new JComboBox(cbCatRefereeModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Player Id Player", "Cat Referee Id Referee"};
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

    JLabel lbPlayerIdPlayer = new JLabel("Player Id Player");
    JLabel lbCatRefereeIdReferee = new JLabel("Cat Referee Id Referee");
    private List<Referee> list = new ArrayList<>();

    public void clear() {
    }

    public void enabled() {
        cbCatReferee.setEnabled(true);
    }

    public void disabled() {
        cbCatReferee.setEnabled(false);
    }


    public RefereeGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Referee");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(mainColor);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbPlayerIdPlayer);
        pnNorth.add(cbPlayer);

        pnNorth.add(lbCatRefereeIdReferee);
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

        pnCenter.add(lbCatRefereeIdReferee);
        pnCenter.add(cbCatReferee);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Player> playerList = daoPlayer.list();
        for (Player player : playerList) {
            cbPlayerModel.addElement(player);
        }

        List<CatReferee> catRefereeList = daoCatReferee.list();
        for (CatReferee catReferee : catRefereeList) {
            cbCatRefereeModel.addElement(catReferee);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                referee = daoReferee.obter(((Player) cbPlayer.getSelectedItem()).getIdPlayer());

                if (referee != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    cbCatReferee.setSelectedItem(referee.getCatRefereeIdReferee());
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

                cbPlayer.setEnabled(false);
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

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoReferee.remover(referee);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Referee> refereeList = daoReferee.list();
                String[] col = {"Player Id Player", "Cat Referee Id Referee"};
                Object[][] data = new Object[refereeList.size()][col.length];

                String aux[];

                for (int i = 0; i < refereeList.size(); i++) {
                    aux = refereeList.get(i).toString().split(";");
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
                    referee =  new Referee();
                }

                referee.setPlayerIdPlayer(((Player) cbPlayer.getSelectedItem()).getIdPlayer());
                referee.setCatRefereeIdReferee((CatReferee) cbCatReferee.getSelectedItem());

                if("create".equals(action)){
                    daoReferee.inserir(referee);
                }

                if("update".equals(action)){
                    daoReferee.atualizar(referee);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

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

                cbPlayer.setEnabled(false);
                cbPlayer.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                cbPlayer.setEnabled(true);
                cbPlayer.setEditable(true);

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
