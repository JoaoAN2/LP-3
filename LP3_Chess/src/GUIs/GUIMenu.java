package GUIs;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author JoaoAN2
 */
public class GUIMenu extends JFrame {
    
    private Container cp;
    
    JButton btnCatReferee = new JButton("Categorias de Árbitros");
    JButton btnFederation = new JButton("Federações");    
    JButton btnGender = new JButton("Gêneros");
    JButton btnTitle = new JButton("Títulos");
    
    public GUIMenu() {
        cp = getContentPane();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Menu - Chess");
        
        cp.setLayout(new GridLayout(2,2));
        cp.add(btnCatReferee);
        cp.add(btnFederation);        
        cp.add(btnGender);
        cp.add(btnTitle);
        
        btnFederation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FederationGUI federationGUI = new FederationGUI();
            }
        });
        
        btnCatReferee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CatRefereeGUI catRefereeGUI = new CatRefereeGUI();
            }
        });
        
        btnGender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GenderGUI genderGUI = new GenderGUI();
            }
        });
        
        btnTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                TitleGUI titleGUI = new TitleGUI();
            }
        });
        
        setLocationRelativeTo(null);
        setSize(800,200);
        setVisible(true);
    }
    
}
