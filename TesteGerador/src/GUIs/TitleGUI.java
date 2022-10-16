package GUIs;

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

 /**
 * @author JoaoAN2 16/10/2022 - 10:50:58
 */

public class TitleGUI extends JDialog {
    String action;

    Title title = new Title();
    DAOTitle daoTitle = new DAOTitle();

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"Sigla Title", "Name Title"};
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

    JLabel lbSiglaTitle = new JLabel("Sigla Title");
    JTextField tfSiglaTitle = new JTextField(3);

    JLabel lbNameTitle = new JLabel("Name Title");
    JTextField tfNameTitle = new JTextField(45);

    private List<Title> list = new ArrayList<>();

    public void clear() {
        tfNameTitle.setText("");
    }

    public void enabled() {
        tfNameTitle.setEditable(true);
    }

    public void disabled() {
        tfNameTitle.setEditable(false);
    }


    public TitleGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - Title");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));


        pnNorth.add(lbSiglaTitle);
        pnNorth.add(tfSiglaTitle);

        pnNorth.add(lbNameTitle);
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

        pnCenter.add(lbNameTitle);
        pnCenter.add(tfNameTitle);

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

                title = daoTitle.obter(tfSiglaTitle.getText());

                if (title != null) {

                    btnCreate.setVisible(false);
                    btnDelete.setVisible(true);
                    btnUpdate.setVisible(true);
                    tfNameTitle.setText(title.getNameTitle());
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

                tfNameTitle.requestFocus();

                tfSiglaTitle.setEnabled(false);
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

                tfSiglaTitle.setEnabled(true);
                tfSiglaTitle.setEditable(true);
                tfSiglaTitle.setText("");

                btnDelete.setVisible(false);
                btnSearch.setVisible(true);

                clear();
                disabled();
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {

                    daoTitle.remover(title);

                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<Title> titleList = daoTitle.list();
                String[] col = {"Sigla Title", "Name Title"};
                Object[][] data = new Object[titleList.size()][col.length];

                String aux[];

                for (int i = 0; i < titleList.size(); i++) {
                    aux = titleList.get(i).toString().split(";");
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
                    title =  new Title();
                }

                title.setSiglaTitle(tfSiglaTitle.getText());
                title.setNameTitle(tfNameTitle.getText());

                if("create".equals(action)){
                    daoTitle.inserir(title);
                }

                if("update".equals(action)){
                    daoTitle.atualizar(title);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfSiglaTitle.setEnabled(true);
                tfSiglaTitle.setEditable(true);

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

                tfSiglaTitle.setEnabled(false);
                tfSiglaTitle.setEditable(false);

                enabled();

                action = "update";
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                tfSiglaTitle.setEnabled(true);
                tfSiglaTitle.setEditable(true);
                tfSiglaTitle.setText("");

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
