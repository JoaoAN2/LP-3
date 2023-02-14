package GUIs;

import Entidades.Gender;
import DAOs.DAOGender;
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

 /**
 * @author Usuário Teste 13/02/2023 - 20:04:49
 */

public class GenderGUI extends JDialog {
    String action;    Color mainColor = new Color(102, 102, 0);

    Gender gender = new Gender();
    DAOGender daoGender = new DAOGender();

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Sigla Gender", "Full Gender"};
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

    JLabel lbSiglaGender = new JLabel("Sigla Gender");
    JTextField tfSiglaGender = new JTextField(3);

    JLabel lbFullGender = new JLabel("Full Gender");
    JTextField tfFullGender = new JTextField(20);

    private List<Gender> list = new ArrayList<>();

    public void clear() {
        tfFullGender.setText("");
    }

    public void enabled() {
        tfFullGender.setEditable(true);
    }

    public void disabled() {
        tfFullGender.setEditable(false);
    }


    public GenderGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Gender");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(mainColor);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbSiglaGender);
        pnNorth.add(tfSiglaGender);

        pnNorth.add(lbFullGender);
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

        pnCenter.add(lbFullGender);
        pnCenter.add(tfFullGender);

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

                gender = daoGender.obter(tfSiglaGender.getText());

                if (gender != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfFullGender.setText(gender.getFullGender());
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

                tfFullGender.requestFocus();

                tfSiglaGender.setEnabled(false);
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

                tfSiglaGender.setEnabled(true);
                tfSiglaGender.setEditable(true);
                tfSiglaGender.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoGender.remover(gender);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Gender> genderList = daoGender.list();
                String[] col = {"Sigla Gender", "Full Gender"};
                Object[][] data = new Object[genderList.size()][col.length];

                String aux[];

                for (int i = 0; i < genderList.size(); i++) {
                    aux = genderList.get(i).toString().split(";");
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
                    gender =  new Gender();
                }

                gender.setSiglaGender(tfSiglaGender.getText());
                gender.setFullGender(tfFullGender.getText());

                if("create".equals(action)){
                    daoGender.inserir(gender);
                }

                if("update".equals(action)){
                    daoGender.atualizar(gender);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfSiglaGender.setEnabled(true);
                tfSiglaGender.setEditable(true);

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

                tfSiglaGender.setEnabled(false);
                tfSiglaGender.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfSiglaGender.setEnabled(true);
                tfSiglaGender.setEditable(true);
                tfSiglaGender.setText("");

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
