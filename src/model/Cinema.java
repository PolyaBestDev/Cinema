package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Observable;


public class Cinema extends Observable
{
    public Cinema()
    {
        createImages();
        createSeats();
        createRowsAndColumns();
        createFreeAndTaken();

        width=seats.get(seats.size()-1).get(seats.get(seats.size()-1).size()-1).getPositionOnPanel().x+78;
        height=seats.get(seats.size()-1).get(seats.get(2).size()-1).getPositionOnPanel().y+78+50;
    }

    // Создание изображения одного места.
    private Image seat_image(Color[] colors, int width, int height)
    {
        BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=(Graphics2D)image.getGraphics();

        g2.setColor(colors[0]);
        g2.fillRoundRect(0, 0, width-1, height-1, 11, 11);
        g2.setColor(colors[1]);
        g2.drawRoundRect(0, 0, width-1, height-1, 11, 11);
        g2.setColor(colors[2]);
        g2.drawRoundRect(1, 1, width-3, height-3, 5, 5);
        g2.setColor(colors[3]);
        g2.drawRoundRect(2, 2, width-5, height-5, 5, 5);
        g2.setColor(colors[3]);
        g2.drawRoundRect(2, 2, width-5, height-5, 3, 3);

        return image;
    }

    // Создание изображения с номером ряда или колонны.
    private Image lineup_image(Color[] colors, Object text, int width, int height)
    {
        BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=(Graphics2D)image.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colors[0]);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString(""+text, width/2-g2.getFontMetrics().stringWidth(""+text)/2, height-4);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.setColor(colors[1]);
        g2.drawRoundRect(0, 0, width-1, height-1, 9, 9);

        return image;
    }

    // Создание всех изображений, выводимых на схеме.
    private void createImages()
    {
        row_images=new Image[ROWS];
        column_images=new Image[COLUMNS];

        image_side=seat_image(side_colors, 28, 24);
        image_simple=seat_image(simple_colors, 28, 24);
        image_central=seat_image(central_colors, 28, 24);
        image_vip=seat_image(vip_colors, 28, 24);
        image_taken=seat_image(taken_colors, 28, 24);
        for(int i=0; i<row_images.length; i++)
            row_images[i]=lineup_image(row_colors, i+1, 22, 20);
        for(int i=0; i<column_images.length; i++)
            column_images[i]=lineup_image(column_colors, i+1, 22, 20);
    }

    // Создание двухмерного массива размером 10х20, и заполнение его
    // объектами класса Seat.
    private void createSeats()
    {
        seats=new ArrayList<ArrayList<Seat>>();

        for(int i=0; i<ROWS; i++)
        {
            ArrayList<Seat> seat_rows=new ArrayList<Seat>();
            int distance=0;
            int interval=0;

            for(int j=0; j<COLUMNS; j++)
            {
                int level=0;

                if(i>=2&&i<=8)
                    distance=16;
                else if(i==9)
                    distance=32;

                if(j==0||j==19)
                    level=-12;
                else if(j==1||j==18)
                    level=-6;
                if(j>=10)
                    interval=68;

                Seat seat=new Seat("обычное", SIMPLE_SEAT);
                seat.setImage(image_simple);

                if(j==0||j==1||j==18||j==19)
                {
                    seat=new Seat("боковое", SIDE_SEAT);
                    seat.setImage(image_side);
                }
                else if(j>=2&&j<=5||j>=14&&j<=17)
                {
                    if(i<=1||i==9)
                    {
                        seat=new Seat("VIP-место", VIP_SEAT);
                        seat.setImage(image_vip);
                    }
                }
                else
                {
                    if(i<=1||i==9)
                    {
                        seat=new Seat("VIP-место", VIP_SEAT);
                        seat.setImage(image_vip);
                    }
                    else if(i>=2&&i<=6)
                    {
                        seat=new Seat("центральное", CENTRAL_SEAT);
                        seat.setImage(image_central);
                    }
                }
                Point position=new Point(50+j*(seat.getWidth()+3)+interval,
                        55+70+level+i*(seat.getHeight()+5)+distance);
                seat.setRow(i);
                seat.setColumn(j);
                seat.setPositionOnPanel(position);
                seat_rows.add(seat);
            }
            seats.add(seat_rows);
        }
    }

    // Создание двух массивов размерами соответственно 10 и 20, и их заполнение
    // объектами класса LineUp.
    private void createRowsAndColumns()
    {
        for(int i=0; i<seats.size(); i++)
        {
            int x=seats.get(i).get(seats.get(0).size()-1).getPositionOnPanel().x+38;
            int y=seats.get(i).get(0).getPositionOnPanel().y+1;

            LineUp left=new LineUp();
            left.setPosition(new Point(19, y));
            left.setIndex(i);
            left.setImage(row_images[i]);
            LineUp right=new LineUp();
            right.setPosition(new Point(x-1, y));
            right.setIndex(i);
            right.setImage(row_images[i]);
            rows.add(left);
            rows.add(right);
        }

        for(int i=0; i<seats.get(seats.size()-1).size(); i++)
        {
            int x=seats.get(seats.size()-1).get(i).getPositionOnPanel().x+3;
            int y=seats.get(seats.size()-1).get(i).getPositionOnPanel().y+34;

            LineUp down=new LineUp();
            down.setPosition(new Point(x, y));
            down.setIndex(i);
            down.setImage(column_images[i]);
            columns.add(down);
        }
    }

    /**
       Создание кнопок всех свободных и занятых мест.
     */
    public final void createFreeAndTaken()
    {
        free.setPosition(new Point(20, 70));
        free.setImage(lineup_image(number_free_colors, getFreeSeats(), 32, 20));

        taken.setPosition(new Point(57, 70));
        taken.setImage(lineup_image(number_taken_colors, getTakenSeats(), 32, 20));
    }

    /**
       Получение размера схемы кинотеатра (в пикселях).
       @return Размер схемы.
     */
    public Dimension getCinemaSize()
    {
        return new Dimension(width, height);
    }

    /**
       Получение длины схемы кинотеатра в пикселях (в пикселях).
       @return Длина схемы.
     */
    public int getWidth()
    {
        return width;
    }

    /**
       Получение высоты схемы кинотеатра в пикселях (в пикселях).
       @return Высота схемы.
     */
    public int getHeight()
    {
        return height;
    }

    /**
       Задание режима редактирования, т.е. отмечание занятых мест.
       @param selection Включить режим редактирования: true - да, false - нет.
     */
    public void setSelection(boolean selection)
    {
        this.selection=selection;
        fireCinemaChanged();
    }

    /**
       Получение значения режима редактирования.
       @return Значение логического параметра..
     */
    public boolean getSelection()
    {
        return selection;
    }

    /**
       Получение объекта класса Seat по заданному ряду и колонне.
       @param row Ряд.
       @param column Колонна.
       @return Объект класса Seat.
     */
    public Seat getSeat(int row, int column)
    {
        return seats.get(row).get(column);
    }

    /**
       Получение объекта класса Seat, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой местом.
       @return Объект класса Seat.
     */
    public Seat seat(Point p)
    {
        for(ArrayList<Seat> row: seats)
            for(Seat seat: row)
                if(seat.contains(p))
                    return seat;

        return null;
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой номером ряда.
       @return Объект класса LineUp.
     */
    public LineUp row(Point p)
    {
        for(LineUp line: rows)
            if(line.contains(p))
                return line;

        return null;
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой номером колонны.
       @return Объект класса LineUp.
     */
    public LineUp column(Point p)
    {
        for(LineUp line: columns)
            if(line.contains(p))
                return line;

        return null;
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области,
       занимаемой кнопкой свободных мест.
       @return Объект класса LineUp.
     */
    public LineUp free(Point p)
    {
        if(free.contains(p))
            return free;

        return null;
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области,
       занимаемой кнопкой занятых мест.
       @return Объект класса LineUp.
     */
    public LineUp taken(Point p)
    {
        if(taken.contains(p))
            return taken;

        return null;
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой группой мест.
       @return Объект класса Group.
     */
    public Group group(Point p)
    {
        for(Group group: groups)
            if(group.contains(p))
                return group;
        return null;
    }

    /**
       Сделать указанное место свободным.
       @param seat Объект класса Seat.
     */
    public void setSeatFree(Seat seat)
    {
        seats.get(seat.getRow()).get(seat.getColumn()).setFreeness(true);
        taken_seats--;
        int type=seats.get(seat.getRow()).get(seat.getColumn()).getType();

        if(type==SIDE_SEAT)
        {
            seats.get(seat.getRow()).get(seat.getColumn()).setImage(image_side);
            taken_side--;
        }
        else if(type==SIMPLE_SEAT)
        {
            seats.get(seat.getRow()).get(seat.getColumn()).setImage(image_simple);
            taken_simple--;
        }
        else if(type==CENTRAL_SEAT)
        {
            seats.get(seat.getRow()).get(seat.getColumn()).setImage(image_central);
            taken_central--;
        }
        else
        {
            seats.get(seat.getRow()).get(seat.getColumn()).setImage(image_vip);
            taken_vip--;
        }

        fireCinemaChanged();
    }

    /**
       Сделать указанное место занятым.
       @param seat Объект класса Seat.
     */
    public void setSeatUnfree(Seat seat)
    {
        seats.get(seat.getRow()).get(seat.getColumn()).setFreeness(false);
        seats.get(seat.getRow()).get(seat.getColumn()).setImage(image_taken);
        taken_seats++;
        int type=seats.get(seat.getRow()).get(seat.getColumn()).getType();

        if(type==SIDE_SEAT)
            taken_side++;
        else if(type==SIMPLE_SEAT)
            taken_simple++;
        else if(type==CENTRAL_SEAT)
            taken_central++;
        else
            taken_vip++;

        fireCinemaChanged();
    }

    /**
       Сделать указанный ряд свободным.
       @param row Объект класса LineUp.
     */
    public void setRowFree(LineUp row)
    {
        for(int i=0; i<seats.get(row.getIndex()).size(); i++)
            if(!seats.get(row.getIndex()).get(i).isFree())
            {
                seats.get(row.getIndex()).get(i).setFreeness(true);
                taken_seats--;
                int type=seats.get(row.getIndex()).get(i).getType();

                if(type==SIDE_SEAT)
                {
                    seats.get(row.getIndex()).get(i).setImage(image_side);
                    taken_side--;
                }
                else if(type==SIMPLE_SEAT)
                {
                    seats.get(row.getIndex()).get(i).setImage(image_simple);
                    taken_simple--;
                }
                else if(type==CENTRAL_SEAT)
                {
                    seats.get(row.getIndex()).get(i).setImage(image_central);
                    taken_central--;
                }
                else
                {
                    seats.get(row.getIndex()).get(i).setImage(image_vip);
                    taken_vip--;
                }
            }
        fireCinemaChanged();
    }

    /**
       Сделать указанный ряд занятым.
       @param row Объект класса LineUp.
     */
    public void setRowUnfree(LineUp row)
    {
        for(int i=0; i<seats.get(row.getIndex()).size(); i++)
            if(seats.get(row.getIndex()).get(i).isFree())
            {
                seats.get(row.getIndex()).get(i).setFreeness(false);
                seats.get(row.getIndex()).get(i).setImage(image_taken);
                taken_seats++;
                int type=seats.get(row.getIndex()).get(i).getType();

                if(type==SIDE_SEAT)
                    taken_side++;
                else if(type==SIMPLE_SEAT)
                    taken_simple++;
                else if(type==CENTRAL_SEAT)
                    taken_central++;
                else
                    taken_vip++;
            }
        fireCinemaChanged();
    }

    /**
       Сделать указанную колонну свободной.
       @param column Объект класса LineUp.
     */
    public void setColumnFree(LineUp column)
    {
        for(int i=0; i<seats.size(); i++)
            if(!seats.get(i).get(column.getIndex()).isFree())
            {
                seats.get(i).get(column.getIndex()).setFreeness(true);
                taken_seats--;
                int type=seats.get(i).get(column.getIndex()).getType();

                if(type==SIDE_SEAT)
                {
                    seats.get(i).get(column.getIndex()).setImage(image_side);
                    taken_side--;
                }
                else if(type==SIMPLE_SEAT)
                {
                    seats.get(i).get(column.getIndex()).setImage(image_simple);
                    taken_simple--;
                }
                else if(type==CENTRAL_SEAT)
                {
                    seats.get(i).get(column.getIndex()).setImage(image_central);
                    taken_central--;
                }
                else
                {
                    seats.get(i).get(column.getIndex()).setImage(image_vip);
                    taken_vip--;
                }
            }
        fireCinemaChanged();
    }

    /**
       Сделать указанную колонну занятой.
       @param column Объект класса LineUp.
     */
    public void setColumnUnfree(LineUp column)
    {
        for(int i=0; i<seats.size(); i++)
            if(seats.get(i).get(column.getIndex()).isFree())
            {
                seats.get(i).get(column.getIndex()).setFreeness(false);
                seats.get(i).get(column.getIndex()).setImage(image_taken);
                taken_seats++;
                int type=seats.get(i).get(column.getIndex()).getType();

                if(type==SIDE_SEAT)
                    taken_side++;
                else if(type==SIMPLE_SEAT)
                    taken_simple++;
                else if(type==CENTRAL_SEAT)
                    taken_central++;
                else
                    taken_vip++;
            }
        fireCinemaChanged();
    }

    /**
       Сделать все места свободными.
     */
    public void setAllFree()
    {
        for(int i=0; i<seats.size(); i++)
            for(int j=0; j<seats.get(i).size(); j++)
            {
                if(!seats.get(i).get(j).isFree())
                {
                    seats.get(i).get(j).setFreeness(true);
                    taken_seats--;
                    int type=seats.get(i).get(j).getType();

                    if(type==SIDE_SEAT)
                    {
                        seats.get(i).get(j).setImage(image_side);
                        taken_side--;
                    }
                    else if(type==SIMPLE_SEAT)
                    {
                        seats.get(i).get(j).setImage(image_simple);
                        taken_simple--;
                    }
                    else if(type==CENTRAL_SEAT)
                    {
                        seats.get(i).get(j).setImage(image_central);
                        taken_central--;
                    }
                    else
                    {
                        seats.get(i).get(j).setImage(image_vip);
                        taken_vip--;
                    }
                }
            }
        fireCinemaChanged();
    }

    /**
       Сделать все места занятыми.
     */
    public void setAllUnfree()
    {
        for(int i=0; i<seats.size(); i++)
            for(int j=0; j<seats.get(i).size(); j++)
                if(seats.get(i).get(j).isFree())
                {
                    seats.get(i).get(j).setFreeness(false);
                    seats.get(i).get(j).setImage(image_taken);
                    taken_seats++;
                    int type=seats.get(i).get(j).getType();

                    if(type==SIDE_SEAT)
                        taken_side++;
                    else if(type==SIMPLE_SEAT)
                        taken_simple++;
                    else if(type==CENTRAL_SEAT)
                        taken_central++;
                    else
                        taken_vip++;
                }
        fireCinemaChanged();
    }

    /**
       Сделать указанную группу свободных мест занятой.
       @param group Объект класса Group.
     */
    public void setGroup(Group group)
    {
        for(int i=0; i<group.size(); i++)
        {
            int type=group.get(i).getType();

            group.get(i).setFreeness(false);
            group.get(i).setImage(image_taken);
            taken_seats++;

            if(type==SIDE_SEAT)
                taken_side++;
            else if(type==SIMPLE_SEAT)
                taken_simple++;
            else if(type==CENTRAL_SEAT)
                taken_central++;
            else
                taken_vip++;
        }

        groups.clear();
        selection_group=false;
        fireCinemaChanged();
    }

    /**
       Найти различные варианты свободных мест
       по указанному размеру группы.
       @param number Размер группы.
     */
    public void findFreeSeatsForGroup(int number)
    {
        if(getTakenSeats()<3*NUMBER_ALL_SEATS/8)
        {
            message=true;
            m_index=0;
        }
        else if(number>NUMBER_ALL_SEATS-getTakenSeats()&&getTakenSeats()!=200)
        {
            message=true;
            m_index=1;
        }
        else if(getTakenSeats()==NUMBER_ALL_SEATS)
        {
            message=true;
            m_index=2;
        }
        else
        {
            for(int i=0; i<seats.size(); i++)
            {
                int count=0;
                Group group=new Group();
                for(int j=0; j<seats.get(i).size(); j++)
                {
                    if(seats.get(i).get(j).isFree())
                    {
                        group.add(seats.get(i).get(j));
                        count++;
                        if(j==9&&count<number)
                        {
                            group.clear();
                            count=0;
                        }
                    }
                    else
                    {
                        group.clear();
                        count=0;
                    }
                    if(count==number)
                    {
                        group.createPath();
                        groups.add(group);
                        group=new Group();
                        count=0;
                    }
                }
            }
            if(groups.isEmpty())
            {
                message=true;
                m_index=3;
                selection_group=false;
            }
            else
            {
                selection_group=true;
                message=false;
            }
            fireCinemaChanged();
        }
    }

    /**
       Получение значения режима выбора группы свободных мест.
       @return Значение логического параметра.
     */
    public boolean getSelectionGroup()
    {
        return selection_group;
    }

    /**
       Получение количества свободных мест.
       @return Количество свободных мест.
     */
    public int getFreeSeats()
    {
        return ROWS*COLUMNS-taken_seats;
    }

    /**
       Получение количества занятых мест.
       @return Количество занятых мест.
     */
    public int getTakenSeats()
    {
        return taken_seats;
    }

    /**
       Получение количества занятых боковых мест.
       @return Количество занятых боковых мест.
     */
    public int getTakenSides()
    {
        return taken_side;
    }

    /**
       Получение количества занятых обычных мест.
       @return Количество занятых обычных мест.
     */
    public int getTakenSimples()
    {
        return taken_simple;
    }

    /**
       Получение количества занятых центральных мест.
       @return Количество занятых центральных мест.
     */
    public int getTakenCentrals()
    {
        return taken_central;
    }

    /**
       Получение количества занятых vip-мест.
       @return Количество занятых vip-мест.
     */
    public int getTakenVIPs()
    {
        return taken_vip;
    }

    /**
       Получение количества свободных мест в процентах.
       @return Количество свободных мест в процентах.
     */
    public double getrFreeSeatsPerc()
    {
        double n=getFreeSeats()/(double)(ROWS*COLUMNS)*100;
        BigDecimal free_P;
        if(getFreeSeats()!=0)
            free_P=BigDecimal.valueOf(n).setScale(2, 4);
        else
            free_P=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(free_P));
    }

    /**
       Получение количества занятых мест в процентах.
       @return Количество занятых мест в процентах.
     */
    public double getTakenSeatsPerc()
    {
        double n=getTakenSeats()/(double)(ROWS*COLUMNS)*100;
        BigDecimal taken_p;
        if(getTakenSeats()!=0)
            taken_p=BigDecimal.valueOf(n).setScale(2, 4);
        else
            taken_p=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(taken_p));
    }

    /**
       Получение количества занятых боковых мест в процентах.
       @return Количество занятых боковых мест в процентах.
     */
    public double getTakenSidesPerc()
    {
        double n=getTakenSides()/(double)getTakenSeats()*100;
        BigDecimal side;
        if(getTakenSides()!=0)
            side=BigDecimal.valueOf(n).setScale(2, 4);
        else
            side=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(side));
    }

    /**
       Получение количества занятых обычных мест в процентах.
       @return Количество занятых обычных мест в процентах.
     */
    public double getTakenSimplesPerc()
    {
        double n=getTakenSimples()/(double)getTakenSeats()*100;
        BigDecimal simple;
        if(getTakenSimples()!=0)
            simple=BigDecimal.valueOf(n).setScale(2, 4);
        else
            simple=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(simple));
    }

    /**
       Получение количества занятых центральных мест в процентах.
       @return Количество занятых центральных мест в процентах.
     */
    public double getTakenCentralsPerc()
    {
        double n=getTakenCentrals()/(double)getTakenSeats()*100;
        BigDecimal central;
        if(getTakenCentrals()!=0)
            central=BigDecimal.valueOf(n).setScale(2, 4);
        else
            central=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(central));
    }

    /**
       Получение количества занятых vip-мест в процентах.
       @return Количество занятых vip-мест в процентах.
     */
    public double getTakenVIPsPerc()
    {
        double n=getTakenVIPs()/(double)getTakenSeats()*100;
        BigDecimal vip;
        if(getTakenVIPs()!=0)
            vip=BigDecimal.valueOf(n).setScale(2, 4);
        else
            vip=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(vip));
    }

    /**
       Получение процентного соотношения занятых боковых мест ко всем местам.
       @return Процентное соотношение занятых боковых мест ко всем местам.
     */
    public double getTakenSidesToAllPerc()
    {
        double n=getTakenSides()/(double)getTakenSeats()*getTakenSeatsPerc();
        BigDecimal side;
        if(getTakenSides()!=0)
            side=BigDecimal.valueOf(n).setScale(2, 4);
        else
            side=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(side));
    }

    /**
       Получение процентного соотношения занятых обычных мест ко всем местам.
       @return Процентное соотношение занятых обычных мест ко всем местам.
     */
    public double getTakenSimplesToAllPerc()
    {
        double n=getTakenSimples()/(double)getTakenSeats()*getTakenSeatsPerc();
        BigDecimal simple;
        if(getTakenSimples()!=0)
            simple=BigDecimal.valueOf(n).setScale(2, 4);
        else
            simple=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(simple));
    }

    /**
       Получение процентного соотношения занятых центральных мест ко всем местам.
       @return Процентное соотношение занятых центральных мест ко всем местам.
     */
    public double getTakenCentralsToAllPerc()
    {
        double n=getTakenCentrals()/(double)getTakenSeats()*getTakenSeatsPerc();
        BigDecimal central;
        if(getTakenCentrals()!=0)
            central=BigDecimal.valueOf(n).setScale(2, 4);
        else
            central=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(central));
    }

    /**
       Получение процентного соотношения занятых vip-мест ко всем местам.
       @return Процентное соотношение занятых vip-мест ко всем местам.
     */
    public double getTakenVIPsToAllPerc()
    {
        double n=getTakenVIPs()/(double)getTakenSeats()*getTakenSeatsPerc();
        BigDecimal vip;
        if(getTakenVIPs()!=0)
            vip=BigDecimal.valueOf(n).setScale(2, 4);
        else
            vip=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(vip));
    }

    /**
       Получение изображения бокового места.
       @return Изображение.
     */
    public Image getImageSide()
    {
        return image_side;
    }

    /**
       Получение изображения обычного места.
       @return Изображение.
     */
    public Image getImageSimple()
    {
        return image_simple;
    }

    /**
       Получение изображения центрального места.
       @return Изображение.
     */
    public Image getImageCentral()
    {
        return image_central;
    }

    /**
       Получение изображения vip-места.
       @return Изображение.
     */
    public Image getImageVIP()
    {
        return image_vip;
    }

    /**
       Получение изображения занятого места.
       @return Изображение.
     */
    public Image getImageTaken()
    {
        return image_taken;
    }

    /**
       Получение выручки за все места.
       @return Выручка.
     */
    public double getRevenueAll()
    {
        double revenue=getRevenueSide()+getRevenueSimple()
                +getRevenueCentral()+getRevenueVIP();

        return revenue;
    }

    /**
       Получение выручки за боковые места.
       @return Выручка.
     */
    public double getRevenueSide()
    {
        return taken_side*PRICE_SIDE;
    }

    /**
       Получение выручки за обычные места.
       @return Выручка.
     */
    public double getRevenueSimple()
    {
        return taken_simple*PRICE_SIMPLE;
    }

    /**
       Получение выручки за центральные места.
       @return Выручка.
     */
    public double getRevenueCentral()
    {
        return taken_central*PRICE_CENTRAL;
    }

    /**
       Получение выручки за vip-места.
       @return Выручка.
     */
    public double getRevenueVIP()
    {
        return taken_vip*PRICE_VIP;
    }

    /**
       Получение выручки за боковые места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueSidePerc()
    {
        BigDecimal side;
        if(getRevenueSide()!=0)
            side=BigDecimal.valueOf(getRevenueSide()/getRevenueAll()*100).setScale(2, 4);
        else
            side=BigDecimal.ZERO;

        return Double.valueOf(String.valueOf(side));
    }

    /**
       Получение выручки за обычные места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueSimplePerc()
    {
        BigDecimal simple;
        if(getRevenueSimple()!=0)
            simple=BigDecimal.valueOf(getRevenueSimple()/getRevenueAll()*100).setScale(2, 4);
        else
            simple=BigDecimal.ZERO;
        return Double.valueOf(String.valueOf(simple));
    }

    /**
       Получение выручки за центральные места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueCentralPerc()
    {
        BigDecimal central;
        if(getRevenueCentral()!=0)
            central=BigDecimal.valueOf(getRevenueCentral()/getRevenueAll()*100).setScale(2, 4);
        else
            central=BigDecimal.ZERO;
        return Double.valueOf(String.valueOf(central));
    }

    /**
       Получение выручки за vip-места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueVIPPerc()
    {
        BigDecimal vip;
        if(getRevenueVIP()!=0)
            vip=BigDecimal.valueOf(getRevenueVIP()/getRevenueAll()*100).setScale(2, 4);
        else
            vip=BigDecimal.ZERO;
        return Double.valueOf(String.valueOf(vip));
    }

    /**
       Получение значения вывода сообщения.
       @return Значение логического параметра.
     */
    public boolean showMessage()
    {
        return message;
    }

    /**
       Получение текста выводимого сообщения.
       @return Текст сообщения.
     */
    public String getMessage()
    {
        return messages[m_index];
    }

    /**
       Вывод всех мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawSeats(Graphics2D g2)
    {
        for(int i=0; i<seats.size(); i++)
        {
            for(int j=0; j<seats.get(i).size(); j++)
            {
                Seat seat=seats.get(i).get(j);
                g2.drawImage(seat.getImage(), seat.getPositionOnPanel().x,
                             seat.getPositionOnPanel().y, null);
            }
        }
    }

    /**
       Вывод номеров рядов и колонн.
       @param g2 Объект класса Graphics2D.
     */
    public void drawRowsAndColumns(Graphics2D g2)
    {
        for(int i=0; i<rows.size(); i++)
            g2.drawImage(rows.get(i).getImage(), rows.get(i).getPosition().x,
                    rows.get(i).getPosition().y, null);

        for(int i=0; i<columns.size(); i++)
            g2.drawImage(columns.get(i).getImage(), columns.get(i).getPosition().x,
                    columns.get(i).getPosition().y, null);
    }

    /**
       Вывод номеров всех мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawSeatsDesignation(Graphics2D g2)
    {
        g2.setColor(Color.white);
        for(int i=0; i<seats.size(); i++)
        {
            for(int j=0; j<seats.get(i).size(); j++)
            {
                int x=seats.get(i).get(j).getPositionOnPanel().x+14
                    -g2.getFontMetrics().stringWidth(""+(j+1))/2;
                int y=seats.get(i).get(j).getPositionOnPanel().y+17;
                g2.drawString(""+(j+1), x, y);
            }
        }
    }

    /**
       Вывод кнопок всех свободных и занятых мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawFreeAndTaken(Graphics2D g2)
    {
        g2.drawImage(free.getImage(), free.getPosition().x, free.getPosition().y, null);
        g2.drawImage(taken.getImage(), taken.getPosition().x, taken.getPosition().y, null);
    }

    /**
       Вывод всех вариантов размещения групп.
       @param g2 Объект класса Graphics2D.
     */
    public void drawGroups(Graphics2D g2)
    {
        if(getSelectionGroup())
        {
            for(int i=0; i<groups.size(); i++)
            {
                g2.setColor(Color.green);
                g2.draw(groups.get(i).gethPath());
                g2.setColor(new Color(0, 255, 0, 47));
                g2.fill(groups.get(i).gethPath());
            }
        }
    }

    private void fireCinemaChanged()
    {
        setChanged();
        notifyObservers();
    }

    private int width;
    private int height;
    private Color[] side_colors=
    {
        new Color(175, 163, 12),
        new Color(132, 122, 9),
        new Color(223, 223, 0),
        new Color(209, 194, 14)
    };
    private Color[] simple_colors=
    {
        new Color(0, 128, 128),
        new Color(0, 119, 119),
        new Color(0, 191, 191),
        new Color(0, 166, 166)
    };
    private Color[] central_colors=
    {
        new Color(0, 98, 196),
        new Color(0, 74, 149),
        new Color(45, 150, 255),
        new Color(6, 131, 255)
    };
    private Color[] vip_colors=
    {
        new Color(0, 185, 185),
        new Color(0, 130, 130),
        new Color(47, 255, 255),
        new Color(0, 223, 223)
    };
    private Color[] taken_colors=
    {
        new Color(174, 0, 0),
        new Color(121, 0, 0),
        new Color(255, 0, 0),
        new Color(213, 0, 0)
    };
    private Color[] row_colors=
    {
        new Color(5, 197, 111),
        new Color(2, 77, 44)
    };
    private Color[] column_colors=
    {
        new Color(136, 23, 249),
        new Color(70, 4, 136)
    };
    private Color[] number_free_colors=
    {
        new Color(179, 221, 243),
        new Color(27, 120, 171)
    };
    private Color[] number_taken_colors=
    {
        new Color(213, 0, 0),
        new Color(159, 0, 0)
    };
    private Image image_side;
    private Image image_simple;
    private Image image_central;
    private Image image_vip;
    private Image image_taken;
    private Image[] row_images;
    private Image[] column_images;
    private ArrayList<LineUp> rows=new ArrayList<LineUp>();
    private ArrayList<LineUp> columns=new ArrayList<LineUp>();
    private LineUp free=new LineUp();
    private LineUp taken=new LineUp();
    private ArrayList<ArrayList<Seat>> seats;
    private int taken_seats;
    private int taken_side;
    private int taken_simple;
    private int taken_central;
    private int taken_vip;
    private boolean selection=false;
    private boolean selection_group=false;
    private boolean message;
    private int m_index;
    private String[] messages=
    {
        "Свободных мест достаточно много,. чтобы использовать поиск",
        "Количество свободных мест меньше. количества людей в группе",
        "Свободных мест нет",
        "Нет столько свободных мест,. расположенных рядом друг с другом"
    };
    private ArrayList<Group> groups=new ArrayList<Group>();

    public static final int ROWS=10;
    public static final int COLUMNS=20;

    public static final int SIDE_SEAT=0;
    public static final int SIMPLE_SEAT=1;
    public static final int CENTRAL_SEAT=2;
    public static final int VIP_SEAT=3;
    public static final int TAKEN_SEAT=4;

    public static final int NUMBER_ALL_SEATS=200;
    public static final int NUMBER_SIDE_SEATS=40;
    public static final int NUMBER_SIMPLE_SEATS=72;
    public static final int NUMBER_CENTRAL_SEATS=40;
    public static final int NUMBER_VIP_SEATS=48;

    public static final int PRICE_SIDE=30;
    public static final int PRICE_SIMPLE=60;
    public static final int PRICE_CENTRAL=120;
    public static final int PRICE_VIP=240;
}