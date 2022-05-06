package gameworld.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Panel for the Turn Screen.
 */
public class TurnScreenPanel extends JPanel implements Scrollable {

  private static final long serialVersionUID = -2179965453492637485L;
  private final BufferedImage bufferedImage;
  private final int vpWidth;
  private final int vpHeight;

  /**
   * Constructs an object of type TurnScreenPanel.
   * 
   * @throws IllegalArgumentException if the input world image couldn't be
   *                                  processed.
   */
  public TurnScreenPanel() {
    try {
      bufferedImage = ImageIO.read(new File("image.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException("Error while processing the image of the world!!!");
    }

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    this.vpWidth = (int) d.getWidth() - 500;
    this.vpHeight = (int) d.getHeight() - 150;
    this.setPreferredSize(new Dimension(vpWidth, vpHeight));
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (bufferedImage != null) {
      g.drawImage(bufferedImage, 0, 0, this);

    }

  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension(vpWidth, vpHeight);
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 0;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 0;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }
}