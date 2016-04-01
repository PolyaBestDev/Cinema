package model;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;


public class Group
{
    public Group()
    {
        seats=new ArrayList<Seat>();
    }

    /**
       Добавление нового места в группу.
       @param seat Объект класса Seat.
     */
    public void add(Seat seat)
    {
        seats.add(seat);
    }

    /**
       Получение места по индексу в группе.
       @param index Индекс места в группе.
       @return Объект класса Seat.
     */
    public Seat get(int index)
    {
        return seats.get(index);
    }

    /**
       Очистка группы.
     */
    public void clear()
    {
        seats=new ArrayList<Seat>();
    }

    /**
       Получение размера группы.
       @return Размер группы.
     */
    public int size()
    {
        return seats.size();
    }

    /**
       Создание видимой области, которая
       покрывает места, входящие в группу.
     */
    public void createPath()
    {
        path=new Path2D.Double();
        path.moveTo(seats.get(0).getPositionOnPanel().x-1,
                seats.get(0).getPositionOnPanel().y-1);
        path.lineTo(seats.get(0).getPositionOnPanel().x+seats.get(0).getWidth()+2,
                seats.get(0).getPositionOnPanel().y-1);
        for(int i=1; i<seats.size(); i++)
        {
            path.lineTo(seats.get(i).getPositionOnPanel().x-1,
                    seats.get(i).getPositionOnPanel().y-1);
            path.lineTo(seats.get(i).getPositionOnPanel().x+seats.get(i).getWidth()+2,
                    seats.get(i).getPositionOnPanel().y-1);
        }
        for(int i=seats.size()-1; i>=0; i--)
        {
            path.lineTo(seats.get(i).getPositionOnPanel().x+seats.get(i).getWidth()+2,
                    seats.get(i).getPositionOnPanel().y+seats.get(i).getHeight());
            path.lineTo(seats.get(i).getPositionOnPanel().x-1,
                    seats.get(i).getPositionOnPanel().y+seats.get(i).getHeight());
        }
        path.closePath();
    }

    /**
       Получение видимой области.
       @return Объект класса Path2D.
     */
    public Path2D gethPath()
    {
        return path;
    }

    public boolean contains(Point p)
    {
        if(path.contains(p))
            return true;
        else
            return false;
    }

    private ArrayList<Seat> seats;
    private Path2D path;
}