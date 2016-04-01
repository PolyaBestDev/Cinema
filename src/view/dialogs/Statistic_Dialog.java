package view.dialogs;

import com.sun.awt.AWTUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controller.Controller;
import view.cinema.GBC;
import view.cinema.RoundBorder;

/**
   Диалоговое окно, которое содержит 2 вкладки
   с информацией о занятых местах и выручке.
 */
public class Statistic_Dialog extends JDialog
{
    public Statistic_Dialog(Controller cinema)
    {
        this.cinema=cinema;
        
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        int width=410;
        int height=250;
        int x=screen.width/2-width/2;
        int y=screen.height/2-height/2;

        setUndecorated(true);
        setSize(width, height);
        setLocation(x, y);
        setModal(true);
        setResizable(false);
        AWTUtilities.setWindowOpaque(Statistic_Dialog.this, false);
        this.getRootPane().setBorder(new RoundBorder(2, Color.lightGray, 0));
        addMouseListener(new Action());
        addMouseMotionListener(new Action());

        Statistic_Panel panel=new Statistic_Panel();

        add(panel);
    }

    private class Statistic_Panel extends JPanel
    {
        public Statistic_Panel()
        {
            setOpaque(false);
            setLayout(new GridBagLayout());

            TabbedPane pane=new TabbedPane();

            add(pane, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(25, 0, 0, 0));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            title=new RoundRectangle2D.Double(0, 0, w, 26, 16, 16);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 232, 236));
            g2.fillRoundRect(0, 0, w, 70, 16, 16);
            g2.setColor(new Color(221, 224, 190));
            g2.fill(title);

            g2.setColor(new Color(27, 43, 65));
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            String text="Статистика кинотеатра";
            g2.drawString(text, w/2-g2.getFontMetrics().stringWidth(text)/2, 15);

            close=new RoundRectangle2D.Double(w-20, 3, 15, 15, 7, 7);
            g2.setColor(new Color(230, 232, 236));
            g2.fill(close);
            g2.setColor(Color.lightGray);
            g2.drawRoundRect(w-20, 3, 15, 15, 7, 7);
            Stroke stroke=new BasicStroke(2.0f);
            g2.setStroke(stroke);
            g2.setColor(new Color(27, 43, 65));
            g2.drawLine(w-16, 7, w-9, 14);
            g2.drawLine(w-16, 14, w-9, 7);
            Stroke stroke2=new BasicStroke(1.0f);
            g2.setStroke(stroke2);

            g2.setColor(Color.lightGray);
            g2.fillRect(0, 25-2, w, 2);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }
    
    private class TabbedPane extends JTabbedPane
    {
        public TabbedPane()
        {
            setOpaque(false);
            setFocusable(false);

            Seats_Panel seats=new Seats_Panel();
            Revenue_Panel revenue=new Revenue_Panel();

            addTab("Места", seats);
            addTab("Выручка", revenue);
        }
    }

    private class Seats_Panel extends JPanel implements Observer
    {
        public Seats_Panel()
        {
            cinema.addObserver(Seats_Panel.this);

            setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            int h=getHeight();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 232, 236));
            g2.fillRoundRect(0, 0, w, h, 15, 15);

            drawLegend(g2);
            drawChart(g2);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        private void drawLegend(Graphics2D g2)
        {
            int x=10;
            int y=10;

            g2.setColor(side_color);
            g2.fillRect(x, y+30, 15, 15);

            g2.setColor(simple_color);
            g2.fillRect(x, y+61, 15, 15);

            g2.setColor(central_color);
            g2.fillRect(x, y+92, 15, 15);

            g2.setColor(vip_color);
            g2.fillRect(x, y+123, 15, 15);

            g2.setColor(Color.lightGray);
            g2.fillRect(x, y+154, 15, 15);

            g2.setColor(Color.black);
            g2.setFont(new Font("Arial", Font.BOLD, 12));

            String all_text="Занято:  "+cinema.getTakenSeats()+", "
                    +cinema.getTakenSeatsPerc()+" %";
            g2.drawString(all_text, x, y+11);

            String side_text="- боковые ("+cinema.getTakenSides()+", "
                    +cinema.getTakenSidesPerc()+" %, "
                    +cinema.getTakenSidesToAllPerc()+" %)";
            g2.drawString(side_text, x+20, y+41);

            String simple_text="- обычные ("+cinema.getTakenSimples()+", "
                    +cinema.getTakenSimplesPerc()+" %, "
                    +cinema.getTakenSimplesToAllPerc()+" %)";
            g2.drawString(simple_text, x+20, y+72);

            String central_text="- центральные ("+cinema.getTakenCentrals()+", "
                    +cinema.getTakenCentralsPerc()+" %, "
                    +cinema.getTakenCentralsToAllPerc()+" %)";
            g2.drawString(central_text, x+20, y+103);

            String vip_text="- VIP-места ("+cinema.getTakenVIPs()+", "
                    +cinema.getTakenVIPsPerc()+" %, "
                    +cinema.getTakenVIPsToAllPerc()+" %)";
            g2.drawString(vip_text, x+20, y+135);

            String free_text="- свободно ("+cinema.getFreeSeats()+", "
                    +cinema.getFreeSeatsPerc()+" %)";
            g2.drawString(free_text, x+20, y+166);
        }

        private void drawChart(Graphics2D g2)
        {
            int x=250;
            int y=20;
            int side_angle=(int)Math.round(cinema.getTakenSidesToAllPerc()*3.6);
            int simple_angle=(int)Math.round(cinema.getTakenSimplesToAllPerc()*3.6);
            int central_angle=(int)Math.round(cinema.getTakenCentralsToAllPerc()*3.6);
            int vip_angle=(int)Math.round(cinema.getTakenVIPsToAllPerc()*3.6);
            int free_angle=(int)Math.round(cinema.getFreeSeatsPerc()*3.6);

            g2.setColor(new Color(216, 235, 249));
            g2.fillOval(x, y, 145, 145);

            g2.setColor(side_color);
            g2.fillArc(x+5, y+5, 135, 135, 90, side_angle);

            g2.setColor(simple_color);
            g2.fillArc(x+5, y+5, 135, 135, 90+side_angle, simple_angle);

            g2.setColor(central_color);
            g2.fillArc(x+5, y+5, 135, 135, 90+side_angle+simple_angle, central_angle);

            g2.setColor(vip_color);
            g2.fillArc(x+5, y+5, 135, 135, 90+side_angle+simple_angle+central_angle, vip_angle);

            g2.setColor(Color.lightGray);
            g2.fillArc(x+5, y+5, 135, 135, 90-free_angle, free_angle);
        }

        public void update(Observable o, Object arg)
        {
            repaint();
        }
    }

    private class Revenue_Panel extends JPanel implements Observer
    {
        public Revenue_Panel()
        {
            cinema.addObserver(Revenue_Panel.this);

            setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            int h=getHeight();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(230, 232, 236));
            g2.fillRoundRect(0, 0, w, h, 15, 15);

            drawLegend(g2);
            drawChart(g2);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        private void drawLegend(Graphics2D g2)
        {
            int x=10;
            int y=10;

            g2.setColor(side_color);
            g2.fillRect(x, y+35, 15, 15);

            g2.setColor(simple_color);
            g2.fillRect(x, y+70, 15, 15);

            g2.setColor(central_color);
            g2.fillRect(x, y+105, 15, 15);

            g2.setColor(vip_color);
            g2.fillRect(x, y+140, 15, 15);

            g2.setColor(Color.black);
            g2.setFont(new Font("Arial", Font.BOLD, 12));

            String all_text="Всего:  "+cinema.getRevenueAll()+" грн.";
            g2.drawString(all_text, x, y+11);

            String side_text="- боковые ("+cinema.getRevenueSide()+" грн., "
                    +cinema.getRevenueSidePerc()+" %)";
            g2.drawString(side_text, x+20, y+46);

            String simple_text="- обычные ("+cinema.getRevenueSimple()+" грн., "
                    +cinema.getRevenueSimplePerc()+" %)";
            g2.drawString(simple_text, x+20, y+81);

            String central_text="- центральные ("+cinema.getRevenueCentral()+" грн., "
                    +cinema.getRevenueCentralPerc()+" %)";
            g2.drawString(central_text, x+20, y+116);

            String vip_text="- VIP-места ("+cinema.getRevenueVIP()+" грн., "
                    +cinema.getRevenueVIPPerc()+" %)";
            g2.drawString(vip_text, x+20, y+152);
        }

        private void drawChart(Graphics2D g2)
        {
            int x=250;
            int y=20;
            int side_angle=(int)Math.round(cinema.getRevenueSidePerc()*3.6);
            int simple_angle=(int)Math.round(cinema.getRevenueSimplePerc()*3.6);
            int central_angle=(int)Math.round(cinema.getRevenueCentralPerc()*3.6);
            int vip_angle=(int)Math.round(cinema.getRevenueVIPPerc()*3.6);

            g2.setColor(new Color(216, 235, 249));
            g2.fillOval(x, y, 145, 145);

            g2.setColor(side_color);
            g2.fillArc(x+5, y+5, 135, 135, 90, side_angle);

            g2.setColor(simple_color);
            g2.fillArc(x+5, y+5, 135, 135, 90+side_angle, simple_angle);

            g2.setColor(central_color);
            g2.fillArc(x+5, y+5, 135, 135, 90+side_angle+simple_angle, central_angle);

            g2.setColor(vip_color);
            g2.fillArc(x+5, y+5, 135, 135, 90-vip_angle, vip_angle);
        }

        public void update(Observable o, Object arg)
        {
            repaint();
        }
    }

    private class Action extends MouseAdapter implements MouseMotionListener
    {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(close.contains(e.getPoint())) {
                Statistic_Dialog.this.setVisible(false);
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            lastPosition=e.getLocationOnScreen();
        }

      /*  @Override
        public void mouseDragged(MouseEvent e)
        {
            if(title.contains(e.getPoint()))
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
*/
        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(title.contains(e.getPoint())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private Controller cinema;
    private Color side_color=new Color(232, 232, 0);
    private Color simple_color=new Color(0, 191, 191);
    private Color central_color=new Color(45, 150, 255);
    private Color vip_color=new Color(47, 255, 255);

    private RoundRectangle2D title;
    private RoundRectangle2D close;
    private Point lastPosition;
}