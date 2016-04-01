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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.Controller;
import view.cinema.GBC;
import view.cinema.RoundBorder;

/**
   Диалоговое окно, позволяющее искать свободные места для группы.
 */
public class Group_Dialog extends JDialog
{
    public Group_Dialog(Controller cinema)
    {
        this.cinema=cinema;

        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        int width=280;
        int height=150;
        int x=screen.width/2-width/2;
        int y=screen.height/2-height/2;

        setUndecorated(true);
        setSize(width, height);
        setLocation(x, y);
        setModal(true);
        setResizable(false);
        AWTUtilities.setWindowOpaque(Group_Dialog.this, false);
        this.getRootPane().setBorder(new RoundBorder(2, Color.lightGray, 0));
        addMouseListener(new Action());
        addMouseMotionListener(new Action());

        Group_Panel panel=new Group_Panel();

        add(panel);
    }

    private class Group_Panel extends JPanel
    {
        public Group_Panel()
        {
            setOpaque(false);
            setLayout(new GridBagLayout());

            JLabel label=new JLabel("Размер группы: ");
            label.setForeground(new Color(27, 43, 65));
            label.setFont(new Font("Arial", Font.BOLD, 14));

            input=new JTextField("", 5);
            input.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
            input.addKeyListener(new InputKeys());

            JButton find=new JButton("Поиск");
            find.setFocusable(false);
            find.setFont(new Font("SansSerif", Font.BOLD, 13));
            find.setForeground(new Color(27, 43, 65));
            find.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    if(input.getText().isEmpty())
                    {
                        Message_Dialog message=new Message_Dialog("Введите число", "Ошибка!");
                        message.setVisible(true);
                    }
                    else
                    {
                        cinema.findFreeSeatsForGroup(Integer.valueOf(input.getText()));
                        Group_Dialog.this.setVisible(false);
                    }
                    if(cinema.showMessage())
                    {
                        Message_Dialog message=new Message_Dialog(cinema.getMessage(), "Внимание!");
                        message.setVisible(true);
                    }
                }
            });
            
            JButton cancel=new JButton("Отмена");
            cancel.setFocusable(false);
            cancel.setFont(new Font("SansSerif", Font.BOLD, 13));
            cancel.setForeground(new Color(27, 43, 65));
            cancel.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    Group_Dialog.this.setVisible(false);
                }
            });

            add(label, new GBC(0, 0).setInsets(40, 30, 0, 0));
            add(input, new GBC(1, 0).setInsets(40, 0, 0, 20));
            add(find, new GBC(0, 1).setInsets(20, 20, 0, 10));
            add(cancel, new GBC(1, 1).setInsets(20, 0, 0, 40));
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
            g2.setColor(new Color(216, 220, 182));
            g2.fillRoundRect(0, 0, w, 25, 16, 16);

            g2.setFont(new Font("Arial", Font.BOLD, 14));
            String text="Поиск свободных мест для группы";
            g2.setColor(new Color(27, 43, 65));
            g2.drawString(text, w/2-g2.getFontMetrics().stringWidth(text)/2, 15);

            g2.setColor(Color.lightGray);
            g2.fillRect(0, 25-3, w, 2);
        }
    }

    private class InputKeys extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent e)
        {
            if(!input.getText().equals("2")&&!input.getText().equals("3")
                    &&!input.getText().equals("4")&&!input.getText().equals("5")
                    &&!input.getText().equals("6"))
            {
                String text="Вводите только натуральные числа. не меньше 2 и не больше 6";
                Message_Dialog message=new Message_Dialog(text, "Ошибка!");
                message.setVisible(true);
                input.setText("");
            }
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
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            if(dialog.contains(e.getPoint()))
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        }
    }

    private Controller cinema;
    private JTextField input;
    private RoundRectangle2D dialog;
    private Point lastPosition;
}