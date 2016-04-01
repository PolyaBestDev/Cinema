package view.cinema;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import controller.Controller;
import view.panels.Scheme_Panel;
import view.panels.Title_Panel;

public class Cinema_Frame extends JFrame
{
    public Cinema_Frame()
    {
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screen=kit.getScreenSize();
        int x=screen.width/2-785/2;
        int y=screen.height/2-602/2;

        Controller cinema=new Controller();

        setNimbus();
        setIconImage(cinema.getImageSimple());
        setLocation(x, y);
        setUndecorated(true);
        setTitle("Кинотеатр \"Сентябрь\"");
        setLayout(new GridBagLayout());
        this.getRootPane().setBorder(new RoundBorder(3, Color.lightGray, 25));

        Title_Panel title_panel=new Title_Panel(this);
        Scheme_Panel scheme_panel=new Scheme_Panel(cinema);

        add(title_panel, new GBC(0, 0).setFill(GBC.BOTH));
        add(scheme_panel, new GBC(0, 1).setFill(GBC.BOTH));
        pack();
    }

    private void setNimbus()
    {
        UIManager.put("info", new Color(230, 232, 236));
        UIManager.LookAndFeelInfo[] info=UIManager.getInstalledLookAndFeels();
        try
        {
            UIManager.setLookAndFeel(info[1].getClassName());
            SwingUtilities.updateComponentTreeUI(Cinema_Frame.this);
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
}