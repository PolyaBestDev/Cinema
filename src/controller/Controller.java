package controller;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Observer;
import model.LineUp;
import model.Cinema;
import model.Group;
import model.Seat;

public class Controller
{
    public Controller()
    {
        cinema=new Cinema();
    }

    public void addObserver(Observer observer)
    {
        cinema.addObserver(observer);
    }

    /**
       Создание кнопок всех свободных и занятых мест.
     */
    public void createFreeAndTaken()
    {
        cinema.createFreeAndTaken();
    }

    /**
       Получение размера схемы кинотеатра (в пикселях).
       @return Размер схемы.
     */
    public Dimension getCinemaSize()
    {
        return cinema.getCinemaSize();
    }

    /**
       Получение длины схемы кинотеатра (в пикселях).
       @return Длина схемы.
     */
    public int getWidth()
    {
        return cinema.getWidth();
    }

    /**
       Получение высоты схемы кинотеатра (в пикселях).
       @return Высота схемы.
     */
    public int getHeight()
    {
        return cinema.getHeight();
    }

    /**
       Задание режима редактирования, т.е. отмечание занятых мест.
       @param selection Включить режим редактирования: true - да, false - нет.
     */
    public void setSelection(boolean selection)
    {
        cinema.setSelection(selection);
    }

    /**
       Получение значения режима редактирования.
       @return Значение логического параметра.
     */
    public boolean getSelection()
    {
        return cinema.getSelection();
    }

    /**
       Получение объекта класса Seat по заданному ряду и колонне.
       @param row Ряд.
       @param column Колонна.
       @return Объект класса Seat.
     */
    public Seat getSeat(int row, int column)
    {
        return cinema.getSeat(row, column);
    }

    /**
       Получение объекта класса Seat, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой местом.
       @return Объект класса Seat.
     */
    public Seat seat(Point p)
    {
        return cinema.seat(p);
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой номером ряда.
       @return Объект класса LineUp.
     */
    public LineUp row(Point p)
    {
        return cinema.row(p);
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой номером колонны.
       @return Объект класса LineUp.
     */
    public LineUp column(Point p)
    {
        return cinema.column(p);
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области,
       занимаемой кнопкой свободных мест.
       @return Объект класса LineUp.
     */
    public LineUp free(Point p)
    {
        return cinema.free(p);
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области,
       занимаемой кнопкой занятых мест.
       @return Объект класса LineUp.
     */
    public LineUp taken(Point p)
    {
        return cinema.taken(p);
    }

    /**
       Получение объекта класса LineUp, который содержит указанную точку.
       @param p Точка, которая находится в области, занимаемой группой мест.
       @return Объект класса Group.
     */
    public Group group(Point p)
    {
        return cinema.group(p);
    }

    /**
       Сделать указанное место свободным.
       @param seat Объект класса Seat.
     */
    public void setSeatFree(Seat seat)
    {
        cinema.setSeatFree(seat);
    }

    /**
       Сделать указанное место занятым.
       @param seat Объект класса Seat.
     */
    public void setSeatUnfree(Seat seat)
    {
        cinema.setSeatUnfree(seat);
    }

    /**
       Сделать указанный ряд свободным.
       @param row Объект класса LineUp.
     */
    public void setRowFree(LineUp row)
    {
        cinema.setRowFree(row);
    }

    /**
       Сделать указанный ряд занятым.
       @param row Объект класса LineUp.
     */
    public void setRowUnfree(LineUp row)
    {
        cinema.setRowUnfree(row);
    }

    /**
       Сделать указанную колонну свободной.
       @param column Объект класса LineUp.
     */
    public void setColumnFree(LineUp column)
    {
        cinema.setColumnFree(column);
    }

    /**
       Сделать указанную колонну занятой.
       @param column Объект класса LineUp.
     */
    public void setColumnUnfree(LineUp column)
    {
        cinema.setColumnUnfree(column);
    }

    /**
       Сделать все места свободными.
     */
    public void setAllFree()
    {
        cinema.setAllFree();
    }

    /**
       Сделать все места занятыми.
     */
    public void setAllUnfree()
    {
        cinema.setAllUnfree();
    }

    /**
       Сделать указанную группу свободных мест занятой.
       @param group Объект класса Group.
     */
    public void setGroup(Group group)
    {
        cinema.setGroup(group);
    }

    /**
       Найти различные варианты свободных мест
       по указанному размеру группы.
       @param number Размер группы.
     */
    public void findFreeSeatsForGroup(int number)
    {
        cinema.findFreeSeatsForGroup(number);
    }

    /**
       Получение значения режима выбора группы свободных мест.
       @return Значение логического параметра.
     */
    public boolean getSelectionGroup()
    {
        return cinema.getSelectionGroup();
    }

    /**
       Получение количества свободных мест.
       @return Количество свободных мест.
     */
    public int getFreeSeats()
    {
        return cinema.getFreeSeats();
    }

    /**
       Получение количества занятых мест.
       @return Количество занятых мест.
     */
    public int getTakenSeats()
    {
        return cinema.getTakenSeats();
    }

    /**
       Получение количества занятых боковых мест.
       @return Количество занятых боковых мест.
     */
    public int getTakenSides()
    {
        return cinema.getTakenSides();
    }

    /**
       Получение количества занятых обычных мест.
       @return Количество занятых обычных мест.
     */
    public int getTakenSimples()
    {
        return cinema.getTakenSimples();
    }

    /**
       Получение количества занятых центральных мест.
       @return Количество занятых центральных мест.
     */
    public int getTakenCentrals()
    {
        return cinema.getTakenCentrals();
    }

    /**
       Получение количества занятых vip-мест.
       @return Количество занятых vip-мест.
     */
    public int getTakenVIPs()
    {
        return cinema.getTakenVIPs();
    }

    /**
       Получение количества свободных мест в процентах.
       @return Количество свободных мест в процентах.
     */
    public double getFreeSeatsPerc()
    {
        return cinema.getrFreeSeatsPerc();
    }

    /**
       Получение количества занятых мест в процентах.
       @return Количество занятых мест в процентах.
     */
    public double getTakenSeatsPerc()
    {
        return cinema.getTakenSeatsPerc();
    }

    /**
       Получение количества занятых боковых мест в процентах.
       @return Количество занятых боковых мест в процентах.
     */
    public double getTakenSidesPerc()
    {
        return cinema.getTakenSidesPerc();
    }

    /**
       Получение количества занятых обычных мест в процентах.
       @return Количество занятых обычных мест в процентах.
     */
    public double getTakenSimplesPerc()
    {
        return cinema.getTakenSimplesPerc();
    }

    /**
       Получение количества занятых центральных мест в процентах.
       @return Количество занятых центральных мест в процентах.
     */
    public double getTakenCentralsPerc()
    {
        return cinema.getTakenCentralsPerc();
    }

    /**
       Получение количества занятых vip-мест в процентах.
       @return Количество занятых vip-мест в процентах.
     */
    public double getTakenVIPsPerc()
    {
        return cinema.getTakenVIPsPerc();
    }

    /**
       Получение процентного соотношения занятых боковых мест ко всем местам.
       @return Процентное соотношение занятых боковых мест ко всем местам.
     */
    public double getTakenSidesToAllPerc()
    {
        return cinema.getTakenSidesToAllPerc();
    }

    /**
       Получение процентного соотношения занятых обычных мест ко всем местам.
       @return Процентное соотношение занятых обычных мест ко всем местам.
     */
    public double getTakenSimplesToAllPerc()
    {
        return cinema.getTakenSimplesToAllPerc();
    }

    /**
       Получение процентного соотношения занятых центральных мест ко всем местам.
       @return Процентное соотношение занятых центральных мест ко всем местам.
     */
    public double getTakenCentralsToAllPerc()
    {
        return cinema.getTakenCentralsToAllPerc();
    }

    /**
       Получение процентного соотношения занятых vip-мест ко всем местам.
       @return Процентное соотношение занятых vip-мест ко всем местам.
     */
    public double getTakenVIPsToAllPerc()
    {
        return cinema.getTakenVIPsToAllPerc();
    }

    /**
       Получение изображения бокового места.
       @return Изображение.
     */
    public Image getImageSide()
    {
        return cinema.getImageSide();
    }

    /**
       Получение изображения обычного места.
       @return Изображение.
     */
    public Image getImageSimple()
    {
        return cinema.getImageSimple();
    }

    /**
       Получение изображения центрального места.
       @return Изображение.
     */
    public Image getImageCentral()
    {
        return cinema.getImageCentral();
    }

    /**
       Получение изображения vip-места.
       @return Изображение.
     */
    public Image getImageVIP()
    {
        return cinema.getImageVIP();
    }

    /**
       Получение изображения занятого места.
       @return Изображение.
     */
    public Image getImageTaken()
    {
        return cinema.getImageTaken();
    }

    /**
       Получение цены бокового места.
       @return Цена.
     */
    public int getPriceSide()
    {
        return Cinema.PRICE_SIDE;
    }

    /**
       Получение цены обычного места.
       @return Цена.
     */
    public int getPriceSimple()
    {
        return Cinema.PRICE_SIMPLE;
    }

    /**
       Получение цены центрального места.
       @return Цена.
     */
    public int getPriceCentral()
    {
        return Cinema.PRICE_CENTRAL;
    }

    /**
       Получение цены vip-места.
       @return Цена.
     */
    public int getPriceVIP()
    {
        return Cinema.PRICE_VIP;
    }

    /**
       Получение выручки за все места.
       @return Выручка.
     */
    public double getRevenueAll()
    {
        return cinema.getRevenueAll();
    }

    /**
       Получение выручки за боковые места.
       @return Выручка.
     */
    public double getRevenueSide()
    {
        return cinema.getRevenueSide();
    }

    /**
       Получение выручки за обычные места.
       @return Выручка.
     */
    public double getRevenueSimple()
    {
        return cinema.getRevenueSimple();
    }

    /**
       Получение выручки за центральные места.
       @return Выручка.
     */
    public double getRevenueCentral()
    {
        return cinema.getRevenueCentral();
    }

    /**
       Получение выручки за vip-места.
       @return Выручка.
     */
    public double getRevenueVIP()
    {
        return cinema.getRevenueVIP();
    }

    /**
       Получение выручки за боковые места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueSidePerc()
    {
        return cinema.getRevenueSidePerc();
    }

    /**
       Получение выручки за обычные места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueSimplePerc()
    {
        return cinema.getRevenueSimplePerc();
    }

    /**
       Получение выручки за центральные места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueCentralPerc()
    {
        return cinema.getRevenueCentralPerc();
    }

    /**
       Получение выручки за vip-места в процентах.
       @return Выручка в процентах.
     */
    public double getRevenueVIPPerc()
    {
        return cinema.getRevenueVIPPerc();
    }

    /**
       Получение значения вывода сообщения.
       @return Значение логического параметра.
     */
    public boolean showMessage()
    {
        return cinema.showMessage();
    }

    /**
       Получение текста выводимого сообщения.
       @return Текст сообщения.
     */
    public String getMessage()
    {
        return cinema.getMessage();
    }

    /**
       Вывод всех мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawSeats(Graphics2D g2)
    {
        cinema.drawSeats(g2);
    }

    /**
       Вывод номеров рядов и колонн.
       @param g2 Объект класса Graphics2D.
     */
    public void drawRowsAndColumns(Graphics2D g2)
    {
        cinema.drawRowsAndColumns(g2);
    }

    /**
       Вывод номеров всех мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawSeatsDesignation(Graphics2D g2)
    {
        cinema.drawSeatsDesignation(g2);
    }

    /**
       Вывод кнопок всех свободных и занятых мест.
       @param g2 Объект класса Graphics2D.
     */
    public void drawFreeAndTaken(Graphics2D g2)
    {
        cinema.drawFreeAndTaken(g2);
    }

    /**
       Вывод всех вариантов размещения групп.
       @param g2 Объект класса Graphics2D.
     */
    public void drawGroups(Graphics2D g2)
    {
        cinema.drawGroups(g2);
    }

    private Cinema cinema;
}