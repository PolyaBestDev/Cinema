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
   Диалоговое окно, содержащее как бы информацию о программе.
 */
public class About_Dialog extends JDialog
{
    public About_Dialog()
    {
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        int width=320;
        int height=170;
        int x=screen.width/2-width/2;
        int y=screen.height/2-height/2;

        setUndecorated(true);
        setSize(width, height);
        setLocation(x, y);
        setModal(true);
        setResizable(false);
        AWTUtilities.setWindowOpaque(About_Dialog.this, false);
        this.getRootPane().setBorder(new RoundBorder(2, Color.lightGray, 0));
        addMouseListener(new Action());
        addMouseMotionListener(new Action());

        About_Panel panel=new About_Panel();

        add(panel);
    }

    private class About_Panel extends JPanel
    {
        public About_Panel()
        {
            setOpaque(false);
            setLayout(new GridBagLayout());

            JButton close=new JButton("Закрыть");
            close.setFocusable(false);
            close.setFont(new Font("SansSerif", Font.BOLD, 13));
            close.setForeground(new Color(27, 43, 65));
            close.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    About_Dialog.this.setVisible(false);
                }
            });

            add(close, new GBC(0, 0).setInsets(120, 0, 0, 0));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            int h=getHeight();
            dialog=new RoundRectangle2D.Double(0, 0, w, h, 16, 16);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 232, 236));
            g2.fill(dialog);
            g2.setColor(new Color(221, 224, 190));
            g2.fillRoundRect(0, 0, w, 25, 16, 16);
            g2.setColor(Color.lightGray);
            g2.fillRect(0, 25-3, w, 2);
            g2.drawLine(10, 60, w-10, 60);
            g2.drawLine(10, h-40, w-10, h-40);

            g2.setColor(new Color(27, 43, 65));
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            String text="О программе";
            g2.drawString(text, w/2-g2.getFontMetrics().stringWidth(text)/2, 15);

            g2.setFont(new Font("Arial", Font.BOLD, 20));
            String name="Кинотеатр \"Сентябрь\"";
            g2.drawString(name, w/2-g2.getFontMetrics().stringWidth(name)/2, 50);

            g2.setFont(new Font("Arial", Font.BOLD, 13));
            String who="Разработал:";
            g2.drawString(who, 8, 90);
            g2.setFont(new Font("Arial", Font.ITALIC, 13));
            String author="Буренко Владимир Александрович";
            g2.drawString(author, 18+g2.getFontMetrics().stringWidth(who), 90);


/*
            g2.setFont(new Font("Arial", Font.ITALIC, 12));
            String s_part_1="Умом Россию не понять,";
            g2.drawString(s_part_1, w/2-g2.getFontMetrics().stringWidth(s_part_1)/2, 160);
            String s_part_2="Аршином общим не измерить:";
            g2.drawString(s_part_2, w/2-g2.getFontMetrics().stringWidth(s_part_2)/2, 180);
            String s_part_3="У ней особенная стать —";
            g2.drawString(s_part_3, w/2-g2.getFontMetrics().stringWidth(s_part_3)/2, 200);
            String s_part_4="В Россию можно только верить.";
            g2.drawString(s_part_4, w/2-g2.getFontMetrics().stringWidth(s_part_4)/2, 220);

            g2.drawRoundRect(w/2-105, 145, 210, 85, 12, 12);
            g2.drawRoundRect(w/2-104, 146, 208, 83, 10, 10);
*/
            g2.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 10));
            String data="Николаев, Март 2013 г.";
            g2.drawString(data, 10, h-45);

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

     /*   @Override
        public void mouseDragged(MouseEvent e)
        {
            if(dialog.contains(e.getPoint()))
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
        }*/

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(dialog.contains(e.getPoint()))
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        }
    }

    private RoundRectangle2D dialog;
    private Point lastPosition;
}