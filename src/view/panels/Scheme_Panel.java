package view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import controller.Controller;

/**
   Панель, на которой отображается схема мест кинотеатра.
 */
public class Scheme_Panel extends JPanel implements Observer
{
    public Scheme_Panel(Controller cinema)
    {
        this.c=cinema;
        cinema.addObserver(Scheme_Panel.this);

        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(cinema.getCinemaSize());
        addMouseListener(new Action());
        addMouseMotionListener(new Action());

        Control_Panel control_panel=new Control_Panel(cinema);
        Info_Panel info_panel=new Info_Panel(cinema);

        add(control_panel, BorderLayout.NORTH);
        add(info_panel, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(27, 43, 65));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        c.drawSeats(g2);
        c.drawSeatsDesignation(g2);
        c.drawRowsAndColumns(g2);
        c.drawFreeAndTaken(g2);
        c.drawGroups(g2);
    }

    private class Action extends MouseAdapter implements MouseMotionListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point p=e.getPoint();
            int b=e.getButton();

            if(c.seat(p)!=null&&c.getSelection()&&!c.getSelectionGroup())
            {
                if(b==1&&c.seat(p).isFree())
                    c.setSeatUnfree(c.seat(p));
                else if(b==3&&!c.seat(p).isFree())
                    c.setSeatFree(c.seat(p));
            }

            if(c.row(p)!=null&&c.getSelection()&&!c.getSelectionGroup())
            {
                if(b==1&&!c.row(p).isFree())
                    c.setRowUnfree(c.row(p));
                else if(b==3)
                    c.setRowFree(c.row(p));
            }

            if(c.column(p)!=null&&c.getSelection()&&!c.getSelectionGroup())
            {
                if(b==1&&!c.column(p).isFree())
                    c.setColumnUnfree(c.column(p));
                else if(b==3)
                    c.setColumnFree(c.column(p));
            }

            if(c.free(p)!=null&&c.getSelection()&&b==1&&c.getFreeSeats()!=200&&!c.getSelectionGroup())
                c.setAllFree();

            if(c.taken(p)!=null&&c.getSelection()&&b==1&&c.getTakenSeats()!=200&&!c.getSelectionGroup())
                c.setAllUnfree();

            if(c.group(p)!=null&&b==1)
                c.setGroup(c.group(p));
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            Point p=e.getPoint();

            if((c.seat(p)!=null||c.row(p)!=null
                    ||c.column(p)!=null||c.free(p)!=null
                    ||c.taken(p)!=null)&&c.getSelection()&&!c.getSelectionGroup()||c.group(p)!=null)
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseDragged(MouseEvent e) {}
    }

    public void update(Observable observer, Object arg)
    {
        c.createFreeAndTaken();
        repaint();
    }

    private Controller c;
}