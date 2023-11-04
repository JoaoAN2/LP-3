package GUIs;

import Entidades.State;
import DAOs.DAOState;
import Entidades.Federation;
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

public class StateGUI extends JDialog {
    String action;    Color mainColor = new Color(102, 255, 255);

    State state = new State();
    DAOState daoState = new DAOState();

    DAOFederation daoFederation = new DAOFederation();
    DefaultComboBoxModel cbFederationModel = new DefaultComboBoxModel();
    JComboBox cbFederation = new JComboBox(cbFederationModel);

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Sigla State", "Name State", "Federation Id State"};
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

    JLabel lbSiglaState = new JLabel("Sigla State");
    JTextField tfSiglaState = new JTextField(2);

    JLabel lbNameState = new JLabel("Name State");
    JTextField tfNameState = new JTextField(45);

    JLabel lbFederationIdState = new JLabel("Federation Id State");
    private List<State> list = new ArrayList<>();

    public void clear() {
        tfNameState.setText("");
    }

    public void enabled() {
        tfNameState.setEditable(true);
        cbFederation.setEnabled(true);
    }

    public void disabled() {
        tfNameState.setEditable(false);
        cbFederation.setEnabled(false);
    }


    public StateGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - State");

        pnCenter.setLayout(new GridLayout(2, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(mainColor);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbSiglaState);
        pnNorth.add(tfSiglaState);

        pnNorth.add(lbNameState);
        pnNorth.add(lbFederationIdState);
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

        pnCenter.add(lbNameState);
        pnCenter.add(tfNameState);

        pnCenter.add(lbFederationIdState);
        pnCenter.add(cbFederation);

        for (int i = 0; i < 5; i++) {
            pnEmpty.add(new JLabel(" "));
        }

        pnSouth.setLayout(cardLayout);
        pnSouth.add(pnEmpty, "empty");
        pnSouth.add(pnList, "list");

        List<Federation> federationList = daoFederation.list();
        for (Federation federation : federationList) {
            cbFederationModel.addElement(federation);
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cardLayout.show(pnSouth, "warning");

                state = daoState.obter(tfSiglaState.getText());

                if (state != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNameState.setText(state.getNameState());
                    cbFederation.setSelectedItem(state.getFederationIdState());
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

                tfNameState.requestFocus();

                tfSiglaState.setEnabled(false);
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

                tfSiglaState.setEnabled(true);
                tfSiglaState.setEditable(true);
                tfSiglaState.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoState.remover(state);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<State> stateList = daoState.list();
                String[] col = {"Sigla State", "Name State", "Federation Id State"};
                Object[][] data = new Object[stateList.size()][col.length];

                String aux[];

                for (int i = 0; i < stateList.size(); i++) {
                    aux = stateList.get(i).toString().split(";");
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
                    state =  new State();
                }

                state.setSiglaState(tfSiglaState.getText());
                state.setNameState(tfNameState.getText());
                state.setFederationIdState((Federation) cbFederation.getSelectedItem());

                if("create".equals(action)){
                    daoState.inserir(state);
                }

                if("update".equals(action)){
                    daoState.atualizar(state);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfSiglaState.setEnabled(true);
                tfSiglaState.setEditable(true);

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

                tfSiglaState.setEnabled(false);
                tfSiglaState.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfSiglaState.setEnabled(true);
                tfSiglaState.setEditable(true);
                tfSiglaState.setText("");

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
