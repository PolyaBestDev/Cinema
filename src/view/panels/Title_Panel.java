package view.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
   Панель, содержащая название программы и 2 кнопки:
   закрытие и сворачивание окна программы.
 */
public class Title_Panel extends JPanel
{
    public Title_Panel(JFrame frame)
    {
        this.frame=frame;

        setOpaque(false);
        setPreferredSize(new Dimension(785, 25));
        addMouseListener(new Action());
        addMouseMotionListener(new Action());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;

        g2.setFont(new Font("Arial", Font.BOLD, 15));
        String s="Кинотеатр \"Сентябрь\"";
        int w=getWidth();
        int h=getHeight();
        int tw=g2.getFontMetrics().stringWidth(s);
        title=new RoundRectangle2D.Double(w/2-tw/2-5, 0, tw+10, h-5, 7, 7);
        minimize=new RoundRectangle2D.Double(w/2-tw/2-28, 3, 15, 15, 7, 7);
        close=new RoundRectangle2D.Double(w/2+tw/2+13, 3, 15, 15, 7, 7);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 232, 236));
        g2.fill(title);
        g2.fill(minimize);
        g2.fill(close);
        g2.setColor(Color.lightGray);
        g2.drawRoundRect(w/2-tw/2-5, 0, tw+10, h-5, 7, 7);
        g2.drawRoundRect(w/2-tw/2-4, 1, tw+8, h-7, 5, 5);
        g2.drawRoundRect(w/2-tw/2-28, 3, 15, 15, 7, 7);
        g2.drawRoundRect(w/2-tw/2-27, 4, 13, 13, 5, 5);
        g2.drawRoundRect(w/2+tw/2+13, 3, 15, 15, 7, 7);
        g2.drawRoundRect(w/2+tw/2+14, 4, 13, 13, 5, 5);
        Stroke stroke=new BasicStroke(2.0f);
        g2.setStroke(stroke);
        g2.setColor(new Color(27, 43, 65));
        g2.drawLine(w/2-tw/2-24, 13, w/2-tw/2-19, 13);

        g2.drawLine(w/2+tw/2+17, 7, w/2+tw/2+24, 14);
        g2.drawLine(w/2+tw/2+17, 14, w/2+tw/2+24, 7);

        Stroke stroke2=new BasicStroke(1.0f);
        g2.setStroke(stroke2);
        g2.drawString(s, w/2-tw/2, h-10);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    private class Action extends MouseAdapter implements MouseMotionListener
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            lastPosition=e.getLocationOnScreen();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(minimize.contains(e.getPoint()))
                frame.setState(JFrame.ICONIFIED);
            if(close.contains(e.getPoint()))
                System.exit(0);
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(title.contains(e.getPoint()))
            {
                Point position=e.getLocationOnScreen();
                int dX=position.x-lastPosition.x;
                int dY=position.y-lastPosition.y;
                if(dX!=0||dY!=0)
                {
                    int x=frame.getLocation().x+dX;
                    int y=frame.getLocation().y+dY;
                    frame.setLocation(x, y);
                    lastPosition=position;
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(minimize.contains(e.getPoint())||title.contains(e.getPoint())||close.contains(e.getPoint()))
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        }
    }

    private JFrame frame;
    private RoundRectangle2D title;
    private RoundRectangle2D minimize;
    private RoundRectangle2D close;
    private Point lastPosition;
}