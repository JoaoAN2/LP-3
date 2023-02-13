package GUIs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


 /**
 * @author JoaoAN2 13/02/2023 - 19:10:28
 */

public class GUIMenu extends JFrame {
    private Container cp;
    DefaultComboBoxModel cbMenuModel = new DefaultComboBoxModel();
    JComboBox cbMenu = new JComboBox(cbMenuModel);
    JLabel lbComboBox = new JLabel("Selecione o CRUD: ");
    JPanel pnLbComboBox = new JPanel();
    JPanel pnComboBox = new JPanel();

    JButton btnChoose = new JButton("Selecionar o CRUD");

    public GUIMenu() {
        cp = getContentPane();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Menu - Chess");
        cp.setLayout(new BorderLayout());

        JLabel lbTitle = new JLabel("Menu");

        JPanel pnNorth = new JPanel();
        cp.add(BorderLayout.NORTH, pnNorth);
        pnNorth.add(lbTitle);

        JPanel pnWest = new JPanel(new GridLayout(1, 1));
        cp.add(BorderLayout.WEST, pnWest);

        JPanel pnCenter = new JPanel(new GridLayout(1, 1));
        cp.add(BorderLayout.CENTER, pnCenter);

        JPanel pnSouth = new JPanel();
        cp.add(BorderLayout.SOUTH, pnSouth);
        pnSouth.add(btnChoose);

        pnLbComboBox.add(lbComboBox);
        pnComboBox.add(cbMenu);

        pnWest.add(pnLbComboBox);
        pnCenter.add(pnComboBox);
        
        cbMenu.setFont(new Font("Arial", 1, 20));
        lbComboBox.setFont(new Font("Arial", 1, 24));

        cbMenuModel.addElement("Arbitration");
        cbMenuModel.addElement("CatReferee");
        cbMenuModel.addElement("City");
        cbMenuModel.addElement("Federation");
        cbMenuModel.addElement("Gender");
        cbMenuModel.addElement("Login");
        cbMenuModel.addElement("Player");
        cbMenuModel.addElement("Referee");
        cbMenuModel.addElement("State");
        cbMenuModel.addElement("Title");
        cbMenuModel.addElement("Tournament");
        cbMenuModel.addElement("Tournaments");
        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch ((String) cbMenu.getSelectedItem()) {

                    case "Arbitration":
                        ArbitrationGUI arbitration = new ArbitrationGUI();
                        break;
                    case "CatReferee":
                        CatRefereeGUI catReferee = new CatRefereeGUI();
                        break;
                    case "City":
                        CityGUI city = new CityGUI();
                        break;
                    case "Federation":
                        FederationGUI federation = new FederationGUI();
                        break;
                    case "Gender":
                        GenderGUI gender = new GenderGUI();
                        break;
                    case "Login":
                        LoginGUI login = new LoginGUI();
                        break;
                    case "Player":
                        PlayerGUI player = new PlayerGUI();
                        break;
                    case "Referee":
                        RefereeGUI referee = new RefereeGUI();
                        break;
                    case "State":
                        StateGUI state = new StateGUI();
                        break;
                    case "Title":
                        TitleGUI title = new TitleGUI();
                        break;
                    case "Tournament":
                        TournamentGUI tournament = new TournamentGUI();
                        break;
                    case "Tournaments":
                        TournamentsGUI tournaments = new TournamentsGUI();
                        break;

                }
            }
        });

        setLocationRelativeTo(null);
        setSize(600, 150);
        setVisible(true);
    }
}
