package gameworld.view;

import gameworld.controller.Features;
import gameworld.view.listeners.ButtonListener;
import gameworld.view.panels.PreScreenPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class Implements {@link PreReqsInfoView} to accept only max turns count from
 * user (default input world file is used) with the help of JTextFields of
 * JFrame.
 * 
 */
public class DefaultMapViewImpl extends JFrame implements PreReqsInfoView {

  private static final long serialVersionUID = -2179965453492637485L;
  private GridBagConstraints gbc;
  private JButton saveBtn;
  private JLabel turnsJlabel;
  private JTextField turnsCountValue;
  private JButton quitBtn;
  private Font buttonsFont;
  private ValidateParameterHelper validateParameterHelper;

  /**
   * Constructor to create an object of type {@link DefaultMapViewImpl}.
   */
  public DefaultMapViewImpl() {
    super("DEFAULT MAP SCREEN");
    validateParameterHelper = new ValidateParameterHelper();
  }

  private void initializeComponents() {
    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.insets = new Insets(5, 0, 5, 0);

    buttonsFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    URL bgImgPath = this.getClass().getResource("/img/WelcomeImage.jpg");
    try {
      setContentPane(
              new PreScreenPanel(
                      Toolkit.getDefaultToolkit().getImage(bgImgPath.toURI().toString())));
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("File is missing in resource folder!!!");
    }
    setLayout(new GridBagLayout());

    turnsJlabel = new JLabel("ENTER MAXIMUM TURNS COUNT");
    turnsJlabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    turnsJlabel.setForeground(Color.BLACK);
    turnsCountValue = new JTextField(5);

    try {
      saveBtn = new JButton("SAVE");
      saveBtn.setActionCommand("SAVE BUTTON FOR DEFAULT MAP");
      saveBtn.setFont(buttonsFont);
      saveBtn.setForeground(Color.BLACK);
      saveBtn.setBackground(Color.GREEN);
      saveBtn.setFocusable(false);
      URL savePath = this.getClass().getResource("/img/save.jpg");
      saveBtn.setIcon(new ImageIcon(ImageIO.read(savePath).getScaledInstance(15,
          15, Image.SCALE_DEFAULT)));

      quitBtn = new JButton("QUIT");
      quitBtn.setActionCommand("QUIT FOR DEFAULT MAP VIEW");
      quitBtn.setForeground(Color.black);
      quitBtn.setBackground(Color.red);
      quitBtn.setFocusable(false);
      quitBtn.setFont(buttonsFont);
      URL quitPath = this.getClass().getResource("/img/quit.jpg");
      quitBtn.setIcon(new ImageIcon(ImageIO.read(quitPath).getScaledInstance(15,
          15, Image.SCALE_DEFAULT)));
      setView();
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't read Icons!!!");
    }

  }

  @Override
  public void setupView() {
    initializeComponents();
  }

  @Override
  public void setFeatures(Features features) {
    validateParameterHelper.validateFeatures(features);
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("SAVE BUTTON FOR DEFAULT MAP", () -> {
      features.promptToGetPreReqInfo(true);
    });

    buttonClickedMap.put("QUIT FOR DEFAULT MAP VIEW", () -> {
      features.promptToQuitGame();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);

    saveBtn.addActionListener(buttonListener);
    quitBtn.addActionListener(buttonListener);
  }

  private void setView() {
    add(turnsJlabel, gbc);
    add(turnsCountValue, gbc);
    add(saveBtn, gbc);
    add(quitBtn, gbc);
    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();
  }

  @Override
  public List<String> promptToGetInputInformation() {
    List<String> params = new ArrayList<String>();
    params.add(null);
    params.add(turnsCountValue.getText());
    clearText();
    return params;
  }

  private void clearText() {
    turnsCountValue.setText("");

  }

  @Override
  public void makeVisible() {
    setVisible(true);

  }

  @Override
  public void refresh() {
    repaint();

  }

  @Override
  public void makeInvisible() {
    setVisible(false);

  }
}
