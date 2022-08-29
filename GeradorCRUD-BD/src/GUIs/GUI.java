package GUIs;

import Controllers.Connect;
import Controllers.Generate;
import Entidades.JDBC;
import Tools.StringTools;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.Connection;

/**
 *
 * @author JoaoAN2
 */
public class GUI extends JFrame {

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
    JButton btnCancel = new JButton("Cancelar");
    JButton btnGenerate = new JButton("Gerar Sistema");

    StringTools st = new StringTools();

    public void disabled() {
        tfDatabaseName.setEditable(false);
        tfHostName.setEditable(false);
        tfPassword.setEditable(false);
        tfPort.setEditable(false);
        tfUserName.setEditable(false);
    }

    public void enable() {
        tfDatabaseName.setEditable(true);
        tfHostName.setEditable(true);
        tfPassword.setEditable(true);
        tfPort.setEditable(true);
        tfUserName.setEditable(true);
    }

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

        pnSouth.add(lbStatus);
        pnSouth.add(btnConnectionTest);
        pnSouth.add(btnCancel);
        pnSouth.add(btnGenerate);

        btnGenerate.setVisible(false);
        btnCancel.setVisible(false);

        JDBC jdbc = new JDBC();
        // Testando conexão
        btnConnectionTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Testando");
                // Connect connect = new Connect(jdbc, tfHostName.getText(), tfUserName.getText(), tfPassword.getText(), "com.mysql.cj.jdbc.Driver", tfDatabaseName.getText(), "jdbc:mysql://", tfPort.getText());
                Connect connect = new Connect(jdbc);
                
                Connection con = jdbc.getConnection();
                if (con != null) {
                    lbStatus.setText("BD \"" + jdbc.getDataBaseName() + "\" conectado com sucesso!");
                    btnConnectionTest.setVisible(false);
                    btnCancel.setVisible(true);
                    btnGenerate.setVisible(true);
                    disabled();
                } else {
                    lbStatus.setText("Dados inseridos de maneira inválida!");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConnectionTest.setVisible(true);
                btnCancel.setVisible(false);
                btnGenerate.setVisible(false);
                jdbc.closeConnection();
                enable();
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Generate generate = new Generate(jdbc);
            }
        });

        setTitle("Gerador de CRUD - BD");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
