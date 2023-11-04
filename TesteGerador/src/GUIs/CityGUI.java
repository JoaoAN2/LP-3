package GUIs;

import Entidades.City;
import DAOs.DAOCity;
import Entidades.State;
import DAOs.DAOState;
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
 * @author JoaoAN2 04/11/2023 - 14:13:46
 */

public class CityGUI extends JDialog {
    String action;    Color mainColor = new Color(102, 255, 255);

    City city = new City();
    DAOCity daoCity = new DAOCity();

    DAOState daoState = new DAOState();
    DefaultComboBoxModel cbStateModel = new DefaultComboBoxModel();
    JComboBox cbState = new JComboBox(cbStateModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Id City", "Name City", "State Sigla City"};
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

    JLabel lbIdCity = new JLabel("Id City");
    JTextField tfIdCity = new JTextField(11);

    JLabel lbNameCity = new JLabel("Name City");
    JTextField tfNameCity = new JTextField(45);

    JLabel lbStateSiglaCity = new JLabel("State Sigla City");
    private List<City> list = new ArrayList<>();

    public void clear() {
        tfNameCity.setText("");
    }

    public void enabled() {
        tfNameCity.setEditable(true);
        cbState.setEnabled(true);
    }

    public void disabled() {
        tfNameCity.setEditable(false);
        cbState.setEnabled(false);
    }


    public CityGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - City");

        pnCenter.setLayout(new GridLayout(2, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(mainColor);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbIdCity);
        pnNorth.add(tfIdCity);

        pnNorth.add(lbNameCity);
        pnNorth.add(lbStateSiglaCity);
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

        pnCenter.add(lbNameCity);
        pnCenter.add(tfNameCity);

        pnCenter.add(lbStateSiglaCity);
        pnCenter.add(cbState);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<State> stateList = daoState.list();
        for (State state : stateList) {
            cbStateModel.addElement(state);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                city = daoCity.obter(Integer.valueOf(tfIdCity.getText()));

                if (city != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNameCity.setText(city.getNameCity());
                    cbState.setSelectedItem(city.getStateSiglaCity());
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

                tfNameCity.requestFocus();

                tfIdCity.setEnabled(false);
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

                tfIdCity.setEnabled(true);
                tfIdCity.setEditable(true);
                tfIdCity.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoCity.remover(city);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<City> cityList = daoCity.list();
                String[] col = {"Id City", "Name City", "State Sigla City"};
                Object[][] data = new Object[cityList.size()][col.length];

                String aux[];

                for (int i = 0; i < cityList.size(); i++) {
                    aux = cityList.get(i).toString().split(";");
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
                    city =  new City();
                }

                city.setIdCity(Integer.valueOf(tfIdCity.getText()));
                city.setNameCity(tfNameCity.getText());
                city.setStateSiglaCity((State) cbState.getSelectedItem());

                if("create".equals(action)){
                    daoCity.inserir(city);
                }

                if("update".equals(action)){
                    daoCity.atualizar(city);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfIdCity.setEnabled(true);
                tfIdCity.setEditable(true);

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

                tfIdCity.setEnabled(false);
                tfIdCity.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfIdCity.setEnabled(true);
                tfIdCity.setEditable(true);
                tfIdCity.setText("");

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
