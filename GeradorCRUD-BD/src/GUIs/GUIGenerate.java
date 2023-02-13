package GUIs;

import ActionButtons.Generate;
import Entidades.Config;
import Entidades.JDBC;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author joaoan2
 */
public class GUIGenerate extends JFrame {

    Container cp = new Container();

    JLabel lbChoose = new JLabel("Pasta do projeto");
    JLabel lbAuthor = new JLabel("Autor");
    JLabel lbColorChoose = new JLabel("Cor");

    JButton btnChoose = new JButton("Escolher pasta do projeto");
    JButton btnColorChoose = new JButton("Escolher a cor");
    JButton btnBack = new JButton("Voltar");
    JButton btnGenerate = new JButton("Gerar Sistema");

    JTextField tfChosenDirectory = new JTextField(30);
    JTextField tfAuthor = new JTextField(30);

    JFileChooser fcDirectory = new JFileChooser();
    JColorChooser colorChooser = new JColorChooser();

    Config config = new Config();

    JPanel pnLbAuthor = new JPanel();

    JPanel pnTfAuthor = new JPanel();

    public GUIGenerate(GUI gui, JDBC jdbc) {
        cp = getContentPane();

        cp.setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel pnNorth = new JPanel();
        cp.add(BorderLayout.NORTH, pnNorth);
        pnNorth.add(lbChoose);
        pnNorth.add(tfChosenDirectory);
        pnNorth.add(btnChoose);

        JPanel pnWest = new JPanel(new GridLayout(4, 1));
        cp.add(BorderLayout.WEST, pnWest);

        JPanel pnCenter = new JPanel(new GridLayout(2, 2));
        cp.add(BorderLayout.CENTER, pnCenter);
        pnCenter.add(lbAuthor);
        pnCenter.add(tfAuthor);
        pnCenter.add(lbColorChoose);
        pnCenter.add(btnColorChoose);

        JPanel pnSouth = new JPanel();
        cp.add(BorderLayout.SOUTH, pnSouth);

        pnSouth.add(btnBack);
        pnSouth.add(btnGenerate);

        btnGenerate.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.setVisible(true);
                dispose();
            }
        });

        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fcDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fcDirectory.showOpenDialog(GUIGenerate.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    config.setPath(fcDirectory.getSelectedFile().getAbsolutePath());
                    tfChosenDirectory.setText(config.getPath());
                }
            }
        });

        btnColorChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(GUIGenerate.this, "Selecione uma cor", Color.BLACK);
                if (selectedColor != null) {
                    config.setColor(selectedColor);
                    System.out.println(config.getColor());
                    lbColorChoose.setText("Cor: R=" + config.getColor().getRed() + " G=" + config.getColor().getGreen() + " B=" + config.getColor().getBlue());
                }
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (config.getColor() != null
                        && config.getPath().trim() != null
                        && !tfAuthor.getText().trim().equals("")) {
                    config.setAuthor(tfAuthor.getText().trim());
                    try {
                        Generate generate = new Generate(jdbc, config);
                    } catch (IOException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(cp, "Preencha Todos os dados!", "Deu ruim patrão...", JOptionPane.PLAIN_MESSAGE);
                }

            }
        });

        setTitle("Gerador de CRUD - BD");
        setSize(800, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
