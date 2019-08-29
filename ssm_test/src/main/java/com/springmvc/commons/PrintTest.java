package com.springmvc.commons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.*;

import javax.swing.ImageIcon;


public class PrintTest implements Printable {

    private static Object getResource;

    public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {

        String r1 = " aaaa";
        String r2 = "bbbb";
        String r3 = " cccc";

        Component component = null;

// 转换成Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;

// 设置打印颜色为黑色
        graphics2D.setColor(Color.BLACK);

// 打印起点坐标
        double x = pf.getImageableX();
        double y = pf.getImageableY();

        switch (pageIndex) {
            case 0:
                Font font = new Font("新宋体", Font.PLAIN, 9);
                graphics2D.setFont(font); // 设置字体

                graphics2D.drawString(r1, (float) 10, 30); // 第一排
                graphics2D.drawString(r2, (float) 10, 40); // 第二排
                graphics2D.drawString(r3, (float) 10, 50); // 第三排


                Image image = new ImageIcon(PrintTest.class.getResource("D:\\picture\\13639685_123501617185_2.jpg")).getImage();

                graphics2D.drawImage(image, 200, 200, component);

                return PAGE_EXISTS;
            default:
                return NO_SUCH_PAGE;
        }
    }
}


class Print {

    public static void main(String[] args) {
        Book book = new Book();

        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        Paper pager = new Paper();
        pager.setSize(1024, 768);
        pager.setImageableArea(10, 10, 800, 600);
        pageFormat.setPaper(pager);
        book.append(new PrintTest(), pageFormat);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);

        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}