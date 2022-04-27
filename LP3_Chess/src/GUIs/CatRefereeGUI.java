package GUIs;

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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

 /**
 * @author JoaoAN2 27/04/2022 - 09:56:21
 */

public class CatRefereeGUI extends JDialog {

    CatReferee catReferee = new CatReferee();
    DAOCatReferee DAOCatReferee = new DAOCatReferee();
    String action;

    Container cp;
    JPanel pnNorth = new JPanel();
    JPanel pnSouth = new JPanel();
    JPanel pnCenter = new JPanel();
    JPanel pnList = new JPanel(new GridLayout(1,1));

    String[] col = new String[]{"SiglaCatReferee", "NameCatReferee"};
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

    JLabel lbSiglaCatReferee = new JLabel("SiglaCatReferee");
    JTextField tfSiglaCatReferee = new JTextField(11);

    JLabel lbNameCatReferee = new JLabel("NameCatReferee");
    JTextField tfNameCatReferee = new JTextField(45);

    private List<CatReferee> list = new ArrayList<>();

    public void clear() {
        tfNameCatReferee.setText("");
    }

    public void enabled() {
        tfNameCatReferee.setEditable(true);
    }

    public void disabled() {
        tfNameCatReferee.setEditable(false);
    }


    public CatRefereeGUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        setTitle("CRUD - CatReferee");

        pnCenter.setLayout(new GridLayout(1, col.length - 1));
        pnNorth.setLayout(new FlowLayout(FlowLayout.LEFT));

        cp.add(pnNorth, BorderLayout.NORTH);
        cp.add(pnSouth, BorderLayout.SOUTH);
        cp.add(pnCenter, BorderLayout.CENTER);

        pnNorth.setBackground(Color.cyan);
        pnCenter.setBorder(BorderFactory.createLineBorder(Color.black));

        pnNorth.add(lbSiglaCatReferee);
        pnNorth.add(tfSiglaCatReferee);
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

        pnCenter.add(lbNameCatReferee);
        pnCenter.add(tfNameCatReferee);

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
                catReferee = DAOCatReferee.obter(tfSiglaCatReferee.getText());
                if (catReferee != null) {
                    btnCreate.setVisible(false);
                    btnUpdate.setVisible(true);
                    btnDelete.setVisible(true);

                    tfNameCatReferee.setText(catReferee.getNameCatReferee());
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
                tfNameCatReferee.requestFocus();
                tfSiglaCatReferee.setEnabled(false);
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
                CatReferee oldCatReferee = catReferee;

                if("create".equals(action)) {
                    catReferee =  new CatReferee();
                }

                catReferee.setSiglaCatReferee(tfSiglaCatReferee.getText());
                catReferee.setNameCatReferee(tfNameCatReferee.getText());

                if("create".equals(action)){
                    DAOCatReferee.inserir(catReferee);
                }

                if("update".equals(action)){
                    DAOCatReferee.atualizar(catReferee);
                }

                btnSearch.setVisible(true);
                btnList.setVisible(true);
                btnSave.setVisible(false);
                btnCancel.setVisible(false);
                btnDelete.setVisible(false);

                tfSiglaCatReferee.setEnabled(true);
                tfSiglaCatReferee.setEditable(true);
                tfSiglaCatReferee.requestFocus();
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
                tfNameCatReferee.requestFocus();
                tfSiglaCatReferee.setEditable(false);
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
                tfSiglaCatReferee.setEnabled(true);
                tfSiglaCatReferee.setEditable(true);
                tfSiglaCatReferee.requestFocus();
                tfSiglaCatReferee.setText("");
                clear();
                disabled();
                btnDelete.setVisible(false);
                btnUpdate.setVisible(false);
                btnCancel.setVisible(false);

                if(response == JOptionPane.YES_OPTION) {
                    DAOCatReferee.remover(catReferee);
                }

            }
        });

       btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                List<CatReferee> catRefereeList = DAOCatReferee.list();
                String[] col = {"SiglaCatReferee", "NameCatReferee"};
                Object[][] data = new Object[catRefereeList.size()][col.length];
                String aux[];

                for (int i = 0; i < catRefereeList.size(); i++) {
                    aux = catRefereeList.get(i).toString().split(";");
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
                tfSiglaCatReferee.setText("");
                tfSiglaCatReferee.requestFocus();
                tfSiglaCatReferee.setEnabled(true);
                tfSiglaCatReferee.setEditable(true);

                clear();
                disabled();

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
