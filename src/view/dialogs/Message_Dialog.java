package view.dialogs;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import view.cinema.GBC;
import view.cinema.RoundBorder;

/**
   Диалоговое окно, сообщающее об ошибке.
 */
public class Message_Dialog extends JDialog
{
    public Message_Dialog() {}

    public Message_Dialog(String text, String message)
    {
        this.text=text;
        this.message=message;

        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        int width=200;
        int height=100;
        int x=screen.width/2-width/2;
        int y=screen.height/2-height/2;

        setSize(width, height);
        setLocation(x, y);
        setUndecorated(true);
        setLayout(new GridBagLayout());
        setModal(true);
        AWTUtilities.setWindowOpaque(Message_Dialog.this, false);
        this.getRootPane().setBorder(new RoundBorder(2, Color.lightGray, 0));
        addMouseListener(new Action());
        addMouseMotionListener(new Action());

        InnerPanel op=new InnerPanel(this);

        add(op, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
    }

    private class InnerPanel extends JPanel
    {
        public InnerPanel(final Message_Dialog dialog)
        {
            setOpaque(false);
            setLayout(new GridBagLayout());

            JButton ok=new JButton("OK");
            ok.setFocusable(false);
            ok.setFont(new Font("SansSerif", Font.BOLD, 11));
            ok.setForeground(new Color(27, 43, 65));
            ok.putClientProperty("JComponent.sizeVariant", "small");
            ok.updateUI();
            ok.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    dialog.setVisible(false);
                }
            });

            add(ok, new GBC(0, 0).setAnchor(GBC.CENTER).setWeight(1, 1).setInsets(60, 0, 0, 0));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            int h=getHeight();
            rect=new RoundRectangle2D.Double(0, 0, w, h, 15, 15);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 232, 236));
            g2.fill(rect);

            g2.setColor(new Color(255, 191, 191));
            g2.fillRoundRect(0, 0, w, 22, 15, 15);
            g2.fillRect(0, 17, getWidth(), 3);

            g2.setColor(Color.lightGray);
            g2.fillRect(0, 20, w, 2);

            g2.setColor(new Color(27, 43, 65));
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString(message, (w-g2.getFontMetrics().stringWidth(message))/2, 15);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            if(text.length()>26)
            {
                String part_1=text.substring(0, text.indexOf("."));
                String part_2=text.substring(text.indexOf(".")+1, text.length());
                g2.drawString(part_1, (w-g2.getFontMetrics().stringWidth(part_1))/2, 40);
                g2.drawString(part_2, (w-g2.getFontMetrics().stringWidth(part_2))/2, 55);
            }
            else
                g2.drawString(text, (w-g2.getFontMetrics().stringWidth(text))/2, 50);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    private class Action extends MouseAdapter implements MouseMotionListener
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            lastPosition=e.getLocationOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(rect.contains(e.getPoint()))
            {
                Point position=e.getLocationOnScreen();
                int dX=position.x-lastPosition.x;
                int dY=position.y-lastPosition.y;
                if(dX!=0||dY!=0)
                {
                    int x=getLocation().x+dX;
                    int y=getLocation().y+dY;
                    setLocation(x, y);
                    lastPosition=position;
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(rect.contains(e.getPoint()))
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        }
    }

    private String text;
    private String message;
    private RoundRectangle2D rect;
    private Point lastPosition;
}