package view.cinema;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

public class RoundBorder implements Border
{
    /**
       Конструктор пользовательской рамки.
       @param thickness Толщина рамки.
       @param colorL Первый цвет рамки (более светлый, находящийся слева и справа, либо только слева).
       @param colorR Второй цвет рамки (более темный, находящийся в центре, либо только справа).
       @param type Тип заливки.
     */
    public RoundBorder(int thickness, Color color, int dh)
    {
        this.thick=thickness;
        this.color=color;
        this.dh=dh;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2=(Graphics2D)g;

        //Задание новой рамки с закругленными углами.
        Area roundBorder=new Area(new RoundRectangle2D.Double(x, y+dh, width, height-dh, 20, 20));
        roundBorder.subtract(new Area(new RoundRectangle2D.Double(x+thick, y+dh+thick, width-2*thick, height-2*thick-dh, 16, 16)));

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fill(roundBorder);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return new Insets(thick, thick, thick, thick);
    }

    @Override
    public boolean isBorderOpaque()
    {
        return false;
    }

    private int thick;
    private Color color;
    private int dh;
}