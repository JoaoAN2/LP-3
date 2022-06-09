package Main;

import Tools.JDBC;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoaoAN
 */
class GUI extends JFrame {

    Container cp;

    JLabel lbTitle = new JLabel("Gerador CRUD - BD");
    JLabel lbHostName = new JLabel("Endereço BD:");
    JLabel lbPort = new JLabel("Porta:");
    JLabel lbDatabaseName = new JLabel("Nome do BD:");
    JLabel lbUserName = new JLabel("Usuário:");
    JLabel lbPassword = new JLabel("Senha:");
    JLabel lbStatus = new JLabel("Insira os dados e teste a conexão!");

    JTextField tfHostName = new JTextField(25);
    JTextField tfPort = new JTextField(4);
    JTextField tfUserName = new JTextField(35);
    JTextField tfPassword = new JPasswordField(35);
    JTextField tfDatabaseName = new JTextField(35);

    JPanel pnTfHostName = new JPanel();
    JPanel pnTfUserName = new JPanel();
    JPanel pnTfPassword = new JPanel();
    JPanel pnTfDatabaseName = new JPanel();

    JPanel pnLbHostName = new JPanel();
    JPanel pnLbUserName = new JPanel();
    JPanel pnLbPassword = new JPanel();
    JPanel pnLbDatabaseName = new JPanel();

    JButton btnConnectionTest = new JButton("Testar conexão");

    public GUI() {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel pnNorth = new JPanel();
        cp.add(BorderLayout.NORTH, pnNorth);
        pnNorth.add(lbTitle);

        JPanel pnWest = new JPanel(new GridLayout(4, 1));
        cp.add(BorderLayout.WEST, pnWest);

        JPanel pnCenter = new JPanel(new GridLayout(4, 1));
        cp.add(BorderLayout.CENTER, pnCenter);

        JPanel pnSouth = new JPanel();
        cp.add(BorderLayout.SOUTH, pnSouth);

        pnLbHostName.add(lbHostName);
        pnLbDatabaseName.add(lbDatabaseName);
        pnLbUserName.add(lbUserName);
        pnLbPassword.add(lbPassword);

        pnTfHostName.add(tfHostName);
        pnTfHostName.add(lbPort);
        pnTfHostName.add(tfPort);
        pnTfDatabaseName.add(tfDatabaseName);
        pnTfUserName.add(tfUserName);
        pnTfPassword.add(tfPassword);

        pnWest.add(pnLbHostName);
        pnCenter.add(pnTfHostName);
        pnWest.add(pnLbDatabaseName);
        pnCenter.add(pnTfDatabaseName);
        pnWest.add(pnLbUserName);
        pnCenter.add(pnTfUserName);
        pnWest.add(pnLbPassword);
        pnCenter.add(pnTfPassword);

        pnSouth.add(btnConnectionTest);
        pnSouth.add(lbStatus);

        // Testando conexão
        btnConnectionTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JDBC jdbc = new JDBC();
                System.out.println("Testando");
                ResultSet res;

                boolean test = true;
                if (test) {
                    jdbc.setHostName("localhost");
                    jdbc.setUserName("root");
                    jdbc.setPassword("2na7N#tU");
                    jdbc.setJdbcDriver("com.mysql.cj.jdbc.Driver");
                    jdbc.setDataBaseName("chess");
                    jdbc.setDataBasePrefix("jdbc:mysql://");
                    jdbc.setDataBasePort("3306");
                } else {
                    jdbc.setHostName(tfHostName.getText());
                    jdbc.setUserName(tfUserName.getText());
                    jdbc.setPassword(tfPassword.getText());
                    jdbc.setJdbcDriver("com.mysql.cj.jdbc.Driver");
                    jdbc.setDataBaseName(tfDatabaseName.getText());
                    jdbc.setDataBasePrefix("jdbc:mysql://");
                    jdbc.setDataBasePort(tfPort.getText());
                }

                Connection con = jdbc.getConnection();
                if (con != null) {
                    try {
                        res = con.createStatement().executeQuery("desc player;");
                        while (res.next()) {
                            System.out.println("desc " + res.getString(5));
                        }
                        lbStatus.setText("BD \"" + jdbc.getDataBaseName() + "\" conectado com sucesso!");
                    } catch (SQLException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    lbStatus.setText("Dados inseridos de maneira inválida!");
                }
            }
        });

        setTitle("Gerador de CRUD - BD");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
