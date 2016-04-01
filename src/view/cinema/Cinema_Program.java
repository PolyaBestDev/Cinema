package view.cinema;

import com.sun.awt.AWTUtilities;

public class Cinema_Program
{
    public static void main(String[] args)
    {
        Cinema_Frame frame=new Cinema_Frame();

        frame.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(frame, false);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}