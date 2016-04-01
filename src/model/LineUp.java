package model;

import java.awt.Image;
import java.awt.Point;


public class LineUp
{
    /**
       Задание изображения номера ряда (колонны).
       @param image Изображение номера.
     */
    public void setImage(Image image)
    {
        this.image=image;

        width=image.getWidth(null);
        height=image.getHeight(null);
    }

    /**
       Получение изображения номера ряда (колонны).
       @return Изображение номера.
     */
    public Image getImage()
    {
        return image;
    }

    /**
       Задание позиции номера ряда (колонны) на панели (в пикселях).
       @param position Позиция номера ряда (колонны) на панели.
     */
    public void setPosition(Point position)
    {
        this.position=position;
    }

    /**
       Получение позиции числа ряда (колонны) на панели (в пикселях).
       @return Позиция номера ряда (колонны) на панели.
     */
    public Point getPosition()
    {
        return position;
    }

    /**
       Задание порядкового номера ряда (колонны).
       @param index Порядковый номер.
     */
    public void setIndex(int index)
    {
        this.index=index;
    }

    /**
       Получение порядкового номера ряда (колонны).
       @return Порядковый номер.
     */
    public int getIndex()
    {
        return index;
    }

    /**
       Задает значение "свободности" ряда или колонны.
       @param free True - свободен, false - занят.
     */
    public void setFreeness(boolean free)
    {
        this.free=free;
    }

    /**
       Ряд (колонна) свободен или нет?
       @return Значение free.
     */
    public boolean isFree()
    {
        return free;
    }

    public boolean contains(Point p)
    {
        if(p.getX()>=position.x&&p.getX()<=position.x+width&&
                p.getY()>=position.y&&p.getY()<=position.y+height)
            return true;
        else
            return false;
    }

    private Image image;
    private Point position;
    private int index;
    private int width;
    private int height;
    private boolean free=false;
}