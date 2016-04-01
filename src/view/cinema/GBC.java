package view.cinema;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC extends GridBagConstraints
{
    /**
       Создает объект GBC, определяя позиции gridx и gridy.
       Значения остальных параметров принимаются по умолчанию.
       @param gridx Позиция gridx - номер столбца.
       @param gridy Позиция gridy - номер строки.
     */
    public GBC(int gridx, int gridy)
    {
        super.gridx=gridx;
        super.gridy=gridy;
    }

    /**
       Создает объект GBC, определяя позиции gridx, gridy, gridwidth и gridheight.
       Значения остальных параметров принимаются по умолчанию.
       @param gridx Позиция gridx - номер столбца.
       @param gridy Позиция gridy - номер строки.
       @param gridwidth Расширение ячейки в направлении x - количество занимаемых строк.
       @param gridheight Расширение ячейки в направлении y - количество занимаемых столбцов.
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight)
    {
        super.gridx=gridx;
        super.gridy=gridy;
        super.gridwidth=gridwidth;
        super.gridheight=gridheight;
    }

    /**
       Устанавливает параметр anchor.
       @param anchor Значение параметра.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setAnchor(int anchor)
    {
        super.anchor=anchor;
        return this;
    }

    /**
       Устанавливает параметр fill.
       @param fill Значение параметра - какую часть занимает
       компонент в своем контейнере.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setFill(int fill)
    {
        super.fill=fill;
        return this;
    }

    /**
       Устанавливает веса ячейки.
       @param weightx Вес в направлении х - какую часть по ширине
       занимает компонент от всего пространства.
       @param weighty Вес в направлении у - какую часть по высоте
       занимает компонент от всего пространства.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setWeight(double weightx, double weighty)
    {
        super.weightx=weightx;
        super.weighty=weighty;
        return this;
    }

    /**
       Устанавливает размеры свободного пространства для ячейки.
       @param distance Размеры по всем направлениям - количество пикселей
       по всем 4 сторонам, отделяющих компонент от всех остальных компонентов.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setInsets(int distance)
    {
        super.insets=new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * Устанавливает размеры свободного пространства для ячейки.
       @param top Размер верхней части свободного пространства.
       @param left Размер левой части свободного пространства.
       @param bottom Размер нижней части свободного пространства.
       @param right Размер правой части свободного пространства.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setInsets(int top, int left, int bottom, int right)
    {
        super.insets=new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * Устанавливает внутреннее заполнение.
       @param ipadx Внутреннее заполнение в направлении х - размер пространства
       компонента по ширине в пикселях.
       @param ipady Внутреннее заполнение в направлении у - размер пространства
       компонента по высоте в пикселях.
       @return Объект this, пригодный для дальнейшей модификации.
     */
    public GBC setIpad(int ipadx, int ipady)
    {
        super.ipadx=ipadx;
        super.ipady=ipady;
        return this;
    }
}