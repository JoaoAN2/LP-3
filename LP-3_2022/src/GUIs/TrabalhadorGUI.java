package GUIs;

import Entidades.Trabalhador;
import DAOs.DAOTrabalhador;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

 /**
 * @author JoaoAN2 15/05/2022 - 19:27:26
 */

public class TrabalhadorGUI extends JDialog {
    Trabalhador trabalhador = new Trabalhador();
    DAOTrabalhador daoTrabalhador = new DAOTrabalhador();
    String action;

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Cpf", "Aposentado", "Nome", "Salario"};
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

    JLabel lbCpf = new JLabel("Cpf");
    JTextField tfCpf = new JTextField(11);

    JLabel lbAposentado = new JLabel("Aposentado");
    JTextField tfAposentado = new JTextField(11);

    JLabel lbNome = new JLabel("Nome");
    JTextField tfNome = new JTextField(255);

    JLabel lbSalario = new JLabel("Salario");
    JTextField tfSalario = new JTextField(11);

    private List<Trabalhador> list = new ArrayList<>();

    public void clear() {
        tfAposentado.setText("");
        tfNome.setText("");
        tfSalario.setText("");
    }

    public void enabled() {
        tfAposentado.setEditable(true);
        tfNome.setEditable(true);
        tfSalario.setEditable(true);
    }

    public void disabled() {
        tfAposentado.setEditable(false);
        tfNome.setEditable(false);
        tfSalario.setEditable(false);
    }


    public TrabalhadorGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Trabalhador");

        pnCenter.setLayout(new GridLayout(3, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorth.add(lbCpf);
        pnNorth.add(tfCpf);
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

        pnCenter.add(lbAposentado);
        pnCenter.add(tfAposentado);

        pnCenter.add(lbNome);
        pnCenter.add(tfNome);

        pnCenter.add(lbSalario);
        pnCenter.add(tfSalario);

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
                trabalhador = daoTrabalhador.obter(tfCpf.getText());
                if (trabalhador != null) {
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    btnDelete.setVisible(true);

                    tfAposentado.setText(String.valueOf(trabalhador.getAposentado()));
                    tfNome.setText(trabalhador.getNome());
                    tfSalario.setText(String.valueOf(trabalhador.getSalario()));
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
                tfAposentado.requestFocus();
                tfCpf.setEnabled(false);
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
                    trabalhador =  new Trabalhador();
                }

                trabalhador.setCpf(tfCpf.getText());
                trabalhador.setAposentado(Short.valueOf(tfAposentado.getText()));
                trabalhador.setNome(tfNome.getText());
                trabalhador.setSalario(Double.valueOf(tfSalario.getText()));

                if("create".equals(action)){
                    daoTrabalhador.inserir(trabalhador);
                }

                if("update".equals(action)){
                    daoTrabalhador.atualizar(trabalhador);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfCpf.setEnabled(true);
                tfCpf.setEditable(true);
                tfCpf.requestFocus();
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
                tfAposentado.requestFocus();
                tfCpf.setEditable(false);
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
                tfCpf.setEnabled(true);
                tfCpf.setEditable(true);
                tfCpf.requestFocus();
                tfCpf.setText("");
                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {
                    daoTrabalhador.remover(trabalhador);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Trabalhador> trabalhadorList = daoTrabalhador.list();
                String[] col = {"Cpf", "Aposentado", "Nome", "Salario"};
                Object[][] data = new Object[trabalhadorList.size()][col.length];
                String aux[];

                for (int i = 0; i < trabalhadorList.size(); i++) {
                    aux = trabalhadorList.get(i).toString().split(";");
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
                tfCpf.setText("");
                tfCpf.requestFocus();
                tfCpf.setEnabled(true);
                tfCpf.setEditable(true);

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
