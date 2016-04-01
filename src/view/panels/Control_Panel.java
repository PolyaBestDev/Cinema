package view.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.Controller;
import view.cinema.GBC;
import view.dialogs.About_Dialog;
import view.dialogs.Group_Dialog;
import view.dialogs.Statistic_Dialog;

/**
   Панель, на которой расположенны 4 кнопки управления.
 */
public class Control_Panel extends JPanel implements Runnable
{
    public Control_Panel(Controller cinema)
    {
        this.cinema=cinema;

        setOpaque(false);
        setLayout(new GridBagLayout());

        Button_Panel button_panel=new Button_Panel();
        Main_Panel info_panel=new Main_Panel();

        add(info_panel, new GBC(0, 0).setFill(GBC.BOTH));
        add(button_panel, new GBC(0, 1).setFill(GBC.BOTH));
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
            y=3;
            if(getLocation().y>=0)
            {
                run=false;
                y=0;
            }
        }
        else
        {
            y=-3;
            if(getLocation().y<=-48)
            {
                run=false;
                y=0;
            }
        }
        this.setLocation(0, getLocation().y+y);
    }

    private class Main_Panel extends JPanel
    {
        public Main_Panel()
        {
            setOpaque(false);
            setLayout(new GridBagLayout());

     //       group_Dialog=new Group_Dialog(cinema);
            statistic_Dialog=new Statistic_Dialog(cinema);
            about_Dialog=new About_Dialog();

      /*      JButton group=new JButton("Группа");
            group.setFocusable(false);
            group.setToolTipText("Поиск свободных мест для размещения группы");
            group.setFont(new Font("SansSerif", Font.BOLD, 13));
            group.setForeground(new Color(27, 43, 65));
            group.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    group_Dialog.setVisible(true);
                }
            });*/

            Redaction_Button redaction=new Redaction_Button();

            JButton statistic=new JButton("Статистика");
            statistic.setFocusable(false);
            statistic.setToolTipText("Информация о выручке и проданых билетах");
            statistic.setFont(new Font("SansSerif", Font.BOLD, 13));
            statistic.setForeground(new Color(27, 43, 65));
            statistic.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    statistic_Dialog.setVisible(true);
                }
            });

            JButton about=new JButton("О программе");
            about.setFocusable(false);
            about.setToolTipText("Информация о программе");
            about.setFont(new Font("SansSerif", Font.BOLD, 13));
            about.setForeground(new Color(27, 43, 65));
            about.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    about_Dialog.setVisible(true);
                }
            });

      //      add(group, new GBC(0, 0).setInsets(10, 60, 10, 0));
            add(redaction, new GBC(1, 0).setInsets(10, 60, 10, 0));
            add(statistic, new GBC(2, 0).setInsets(10, 60, 10, 0));
            add(about, new GBC(3, 0).setInsets(10, 60, 10, 60));
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            int w=getWidth();
            int h=getHeight();

            g2.setColor(new Color(27, 43, 65));
            g2.fillRect(5, 0, w-10, h);
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.fillRoundRect(5, 0, w-10, h, 16, 16);
//            Paint gradient=new GradientPaint(w, -20, new Color(58, 92, 139), w, 30, new Color(27, 43, 65), false);
//            g2.setPaint(gradient);
//            g2.fillRoundRect(5, 0, w-10, h/2, 16, 16);
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//            g2.fillRect(5, h-5, w-10, 3);
            g2.setColor(Color.lightGray);
            g2.fillRect(0, h-3, w, h);
        }
    }

    private class Redaction_Button extends JButton implements Observer
    {
        public Redaction_Button()
        {
            cinema.addObserver(Redaction_Button.this);

            setFocusable(false);
            setText("Редактирование");
            setToolTipText("Отметить занятые места");
            setFont(new Font("SansSerif", Font.BOLD, 13));
            setForeground(new Color(27, 43, 65));
            addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    cinema.setSelection(selection);
                    selection=!selection;
                    if(selection) {
                        setToolTipText("Отметить занятые места");
                    }
                    else {
                        setToolTipText("Режим редактирования включен");
                    }
                }
            });
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;

            if(!selection)
            {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.setColor(new Color(244, 0, 0));
                g2.drawString("R", getWidth()-g2.getFontMetrics().stringWidth("R")-4, 15);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            }
        }

        public void update(Observable observer, Object arg)
        {
            repaint();
        }
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
            g2.fillRect(w/2-35, 0, 70, 4);
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
                    Thread counterThread=new Thread(Control_Panel.this, "Location Control");
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

    private Controller cinema;
    private boolean selection=true;;
 //   private Group_Dialog group_Dialog;
    private Statistic_Dialog statistic_Dialog;
    private About_Dialog about_Dialog;
    private int y=0;
    private boolean down=true;
    private boolean run;
}