package GUIs;

import Entidades.Atribute;
import Entidades.Table;
import Entidades.JDBC;
import Geradores.GeradorDeDAO;
import Geradores.GeradorDeGUI;
import Geradores.GeradorDeMenu;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoaoAN
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

                boolean test = false;
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
                Connection con = jdbc.getConnection();
                try {
                    List<Table> tables = new ArrayList();
                    ResultSet rsTables = con.createStatement().executeQuery("SHOW TABLES;");

                    while (rsTables.next()) {
                        Table table = new Table();
                        table.setTableNameBD(rsTables.getString(1));
                        table.setTableNameJava(st.bdToJava(rsTables.getString(1)));
                        tables.add(table);
                        jdbc.setTables(tables);
                    }

                    for (int i = 0; i < tables.size(); i++) {
                        List<Atribute> atributes = new ArrayList();
                        ResultSet rsDesc = con.createStatement().executeQuery("DESC " + tables.get(i).getTableNameBD());
                        while (rsDesc.next()) {
                            Atribute atribute = new Atribute();
                            atribute.setNameJava(st.bdToJava(rsDesc.getString(1)));
                            atribute.setTypeJava(st.convertTypeBDToJava(rsDesc.getString(2)));
                            atribute.setNameBD(rsDesc.getString(1));
                            atribute.setSize(st.sizeAtributes(rsDesc.getString(2)));
                            atribute.setTypeBD(rsDesc.getString(2));
                            atribute.setIsNull(rsDesc.getString(3).equals("YES"));
                            atribute.setKey(rsDesc.getString(4));
                            atribute.setLabelName(st.labelJava(rsDesc.getString(1)));
                            atributes.add(atribute);
                        }
                        tables.get(i).setAtributes(atributes);

                    }

                    for (int i = 0; i < tables.size(); i++) {
                        ResultSet rsFK = con.createStatement().executeQuery("SELECT\n"
                                + "   constraint_name,\n"
                                + "   column_name,\n"
                                + "   table_name,\n"
                                + "   referenced_table_name, \n"
                                + "   referenced_column_name\n"
                                + "\n"
                                + "FROM information_schema.KEY_COLUMN_USAGE\n"
                                + "WHERE REFERENCED_TABLE_NAME = '" + tables.get(i).getTableNameBD() + "'");
                        while (rsFK.next()) {
                            Table tableFK = jdbc.getTableByName(rsFK.getString("table_name"));
                            Atribute atributeFK = tableFK.getAtributeByName(rsFK.getString("column_name"));
                            atributeFK.setOriginTableFK(rsFK.getString("referenced_table_name"));
                        }
                    }
                    for (int i = 0; i < tables.size(); i++) {
                        GeradorDeGUI geradorDeGUI = new GeradorDeGUI(st.firstLetterToUpperCase(tables.get(i).getTableNameJava()), tables.get(i).getAtributes());
                        GeradorDeDAO geradorDeDAO = new GeradorDeDAO(st.firstLetterToUpperCase(tables.get(i).getTableNameJava()), tables.get(i).getAtributes(), tables.get(i).getTableNameBD());
                    }
                    GeradorDeMenu geradorDeMenu = new GeradorDeMenu(tables, jdbc.getDataBaseName());
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        setTitle("Gerador de CRUD - BD");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
