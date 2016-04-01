package model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;


public class Seat
{
    public Seat() {}

    public Seat(String name)
    {
        this.name=name;
    }

    public Seat(String name, int type)
    {
        this.name=name;
        this.type=type;
    }

    /**
       Задание названия места.
       @param name Название места.
     */
    public void setName(String name)
    {
        this.name=name;
    }

    /**
       Получение названия места.
       @return Название места.
     */
    public String getName()
    {
        return name;
    }

    /**
       Задание типа места.
       @param type Тип места.
     */
    public void setType(int type)
    {
        this.type=type;
    }

    /**
       Получение типа места.
       @return Тип места.
     */
    public int getType()
    {
        return type;
    }

    /**
       Задание номера ряда, в котором находится место.
       @param row Номер ряда.
     */
    public void setRow(int row)
    {
        this.row=row;
    }

    /**
       Получение номера ряда.
       @return Номер ряда.
     */
    public int getRow()
    {
        return row;
    }

    /**
       Задание номера колонны, в которой находится место.
       @param column Номер колонны.
     */
    public void setColumn(int column)
    {
        this.column=column;
    }

    /**
       Полученние номера колонны.
       @return Номер колонны.
     */
    public int getColumn()
    {
        return column;
    }

    /**
       Получение позиции места в схеме мест (ряд и колонна).
       @return Позиция места.
     */
    public Point getPosition()
    {
        return new Point(row, column);
    }

    /**
       Задание изображения места на схеме мест.
       @param image Изображение места.
     */
    public void setImage(Image image)
    {
        this.image=image;

        width=image.getWidth(null);
        height=image.getHeight(null);
    }

    /**
       Получение изображения места.
       @return Изображение места.
     */
    public Image getImage()
    {
        return image;
    }

    /**
       Задание позиции места на панели (в пикселях).
       @param position Позиция места на панели.
     */
    public void setPositionOnPanel(Point position)
    {
        this.position=position;
    }

    /**
       Получение позиции места на панели (в пикселях).
       @return Позиция места на панели.
     */
    public Point getPositionOnPanel()
    {
        return position;
    }

    /**
       Получение длины изображения.
       @return Длина изображения.
     */
    public int getWidth()
    {
        return width;
    }

    /**
       Получение высоты изображения.
       @return Высота изображения.
     */
    public int getHeight()
    {
        return height;
    }

    /**
       Задает значение "свободности" места.
       @param free True - свободно, false - занято.
     */
    public void setFreeness(boolean free)
    {
        this.free=free;
    }

    /**
       Свободно место или нет?
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

    private String name;
    private int type;
    private int row;
    private int column;
    private Image image;
    private Point position;
    private int width;
    private int height;
    private boolean free=true;
}