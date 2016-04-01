package view.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import controller.Controller;
import view.cinema.GBC;

/**
   Панель, на которой находятся названия мест и их соответствующие изображения,
   а также стоимость этих мест.
 */
public class Info_Panel extends JPanel implements Runnable
{
    public Info_Panel(Controller cinema)
    {
        this.cinema=cinema;

        setOpaque(false);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(785, 50));

        Button_Panel button_panel=new Button_Panel();
        Main_Panel info_panel=new Main_Panel();

        add(button_panel, new GBC(0, 0).setFill(GBC.BOTH));
        add(info_panel, new GBC(0, 1).setFill(GBC.BOTH));
    }

    public void run()
    {
        runChangeLocation();
    }

    @SuppressWarnings("SleepWhileHoldingLock")
    public void runChangeLocation()
    {
        run=true;
        long time=20;

        while(run)
        {
            try
            {
                Thread.sleep(time);
            }
            catch (InterruptedException x) {}

            changeLocation();
        }
    }

    public void changeLocation()
    {
        if(down)
        {
            y=2;
            if(getLocation().y>=522)
            {
                run=false;
                y=0;
            }
        }
        else
        {
            y=-2;
            if(getLocation().y<=487)
            {
                run=false;
                y=0;
            }
        }
        this.setLocation(0, getLocation().y+y);
    }

    private class Button_Panel extends JPanel
    {
        public Button_Panel()
        {
            setOpaque(false);
            setPreferredSize(new Dimension(785, 8));
            addMouseListener(new ButtonAction());
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            button=new RoundRectangle2D.Double(w/2-35, 0, 70, 8, 9, 9);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fill(button);
            g2.fillRect(w/2-35, 4, 70, 8);
            g2.setColor(Color.black);
            Path2D.Double path=new Path2D.Double();
            if(!down)
            {
                path.moveTo(w/2-15, 2);
                path.lineTo(w/2+15, 2);
                path.lineTo(w/2, 6);
                path.closePath();
            }
            else
            {
                path.moveTo(w/2-15, 6);
                path.lineTo(w/2+15, 6);
                path.lineTo(w/2, 2);
                path.closePath();
            }
            g2.fill(path);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        private class ButtonAction implements MouseListener
        {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e)
            {
                if(button.contains(e.getPoint()))
                {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    background=Color.gray;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(button.contains(e.getPoint()))
                {
                    Thread counterThread = new Thread(Info_Panel.this, "Info_Panel");
                    counterThread.start();
                    down=!down;
                }

                setCursor(Cursor.getDefaultCursor());
                background=Color.lightGray;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(button.contains(e.getPoint()))
                {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    background=new Color(214, 217, 223);
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                setCursor(Cursor.getDefaultCursor());
                background=Color.lightGray;
                repaint();
            }
        }

        private RoundRectangle2D button;
        private Color background=Color.lightGray;
    }

    private class Main_Panel extends JPanel
    {
        public Main_Panel()
        {
            setOpaque(false);
            setPreferredSize(new Dimension(785, 40));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            g2.setColor(new Color(27, 43, 65));
            g2.fillRect(8, 0, getWidth()-16, getHeight());

            g2.setColor(Color.lightGray);
            g2.fillRect(0, 0, getWidth(), 3);
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));

            String side="\u2212 "+cinema.getSeat(0, 0).getName()+" ("+cinema.getPriceSide()+" грн.)";
            g2.drawImage(cinema.getImageSide(), 20, getHeight()/2-12, this);
            g2.drawString(side, 52, getHeight()/2+4);

            String simple="\u2212 "+cinema.getSeat(2, 2).getName()+" ("+cinema.getPriceSimple()+" грн.)";
            g2.drawImage(cinema.getImageSimple(), 170, getHeight()/2-12, this);
            g2.drawString(simple, 202, getHeight()/2+4);

            String central="\u2212 "+cinema.getSeat(2, 9).getName()+" ("+cinema.getPriceCentral()+" грн.)";
            g2.drawImage(cinema.getImageCentral(), 323, getHeight()/2-12, this);
            g2.drawString(central, 355, getHeight()/2+4);

            String vip="\u2212 "+cinema.getSeat(0, 2).getName()+" ("+cinema.getPriceVIP()+" грн.)";
            g2.drawImage(cinema.getImageVIP(), 503, getHeight()/2-12, this);
            g2.drawString(vip, 535, getHeight()/2+4);

            String taken="\u2212 занятое";
            g2.drawImage(cinema.getImageTaken(), 661, getHeight()/2-12, this);
            g2.drawString(taken, 693, getHeight()/2+4);
        }
    }

    private Controller cinema;
    private int y=0;
    private boolean down=false;
    private boolean run;
}