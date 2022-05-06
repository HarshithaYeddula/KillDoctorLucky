package gameworld.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * Panel for the Pre Game Screen.
 *
 */
public class PreScreenPanel extends JPanel {

  private static final long serialVersionUID = -2179965453492637485L;

  private Image image;

  /**
   * Constructs an object of type PreScreenPanel.
   * 
   * @throws IllegalArgumentException if the input world image couldn't be
   *                                  processed.
   */
  public PreScreenPanel(Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!!!");
    }
    this.image = image;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
  }
}