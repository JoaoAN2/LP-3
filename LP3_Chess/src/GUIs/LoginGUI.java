package GUIs;

import Entidades.Login;
import DAOs.DAOLogin;
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
 * @author JoaoAN2 24/09/2022 - 22:14:35
 */

public class LoginGUI extends JDialog {
    Login login = new Login();
    DAOLogin daoLogin = new DAOLogin();
    String action;

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Email", "Password", "Autority"};
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

    JLabel lbEmail = new JLabel("Email");
    JTextField tfEmail = new JTextField(255);

    JLabel lbPassword = new JLabel("Password");
    JTextField tfPassword = new JTextField(255);

    JLabel lbAutority = new JLabel("Autority");
    JTextField tfAutority = new JTextField(11);

    private List<Login> list = new ArrayList<>();

    public void clear() {
        tfPassword.setText("");
        tfAutority.setText("");
    }

    public void enabled() {
        tfPassword.setEditable(true);
        tfAutority.setEditable(true);
    }

    public void disabled() {
        tfPassword.setEditable(false);
        tfAutority.setEditable(false);
    }


    public LoginGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Login");

        pnCenter.setLayout(new GridLayout(2, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbEmail);
        pnNorth.add(tfEmail);

        pnNorth.add(lbPassword);
        pnNorth.add(lbAutority);
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

        pnCenter.add(lbPassword);
        pnCenter.add(tfPassword);

        pnCenter.add(lbAutority);
        pnCenter.add(tfAutority);

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

                login = daoLogin.obter(tfEmail.getText());

                if (login != null) {
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    btnDelete.setVisible(true);

                    tfPassword.setText(login.getPassword());
                    tfAutority.setText(String.valueOf(login.getAutority()));
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

                tfPassword.requestFocus();

                tfEmail.setEnabled(false);
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
                    login =  new Login();
                }

                login.setEmail(tfEmail.getText());
                login.setPassword(tfPassword.getText());
                login.setAutority(Integer.valueOf(tfAutority.getText()));

                if("create".equals(action)){
                    daoLogin.inserir(login);
                }

                if("update".equals(action)){
                    daoLogin.atualizar(login);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfEmail.setEnabled(true);
                tfEmail.setEditable(true);

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

                tfEmail.setEnabled(false);
                tfEmail.setEditable(false);

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

                tfEmail.setEnabled(true);
                tfEmail.setEditable(true);
                tfEmail.setText("");

                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);
                btnSearch.setVisible(true);
                if(response == JOptionPane.YES_OPTION) {
                    daoLogin.remover(login);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Login> loginList = daoLogin.list();
                String[] col = {"Email", "Password", "Autority"};
                Object[][] data = new Object[loginList.size()][col.length];
                String aux[];

                for (int i = 0; i < loginList.size(); i++) {
                    aux = loginList.get(i).toString().split(";");
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

                tfEmail.setEnabled(true);
                tfEmail.setEditable(true);
                tfEmail.setText("");

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
