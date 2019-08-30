package com.springmvc.test;

import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


/**
 *         
 *  *  通用热敏打印机类
 *  
 */
public class Prient implements Printable {

    //打印内容
    private PrintModel printModel;

    /**
     *
     * @param x
     * @param y
     * @param width 纸宽
     * @param height 纸高
     * @return
     */
    public int commonPrint(double x, double y, double width, double height) {

        Book book = new Book();
        // 打印格式  
        PageFormat pf = new PageFormat();
        //原点在纸张的左上方，x 指向右方，y 指向下方。
        pf.setOrientation(PageFormat.PORTRAIT);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();
        p.setSize(width, height);
        //设置可成像区域左上角的X坐标
        //设置可成像区域左上角的Y坐标
        //宽度设置纸张可成像区域宽度的值
        //高度设置纸张可成像区域高度的值
        p.setImageableArea(x, y, width, height);
        pf.setPaper(p);
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(new Prient(this.printModel), pf);
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);
        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(new Font(null, Font.PLAIN, 10));
        //获取字符串width
        int strWidth = g2d.getFontMetrics().stringWidth(printModel.getTitle());
        g2d.drawString(printModel.getTitle(), (int)(pf.getWidth()/2 - strWidth), (int)(pf.getHeight()/10));
        g2d.setFont(new Font(null, Font.PLAIN, 8));
        g2d.drawImage(printModel.getImage(), 15, (int)(pf.getHeight()/6), (int)pf.getWidth()-30,(int)(pf.getHeight()/3),null);
        g2d.drawString(printModel.getCode() + printModel.getContent() , 15, (int)(2*(pf.getHeight())/3));
        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        try {
            Image src = Toolkit.getDefaultToolkit().getImage("E:\\store\\send\\send\\20190829\\F44A31A06C1F4469A2B8B60B074E1615.png");
            PrintModel printModel = new PrintModel();
            printModel.setTitle("标题");
            printModel.setImage(src);
            printModel.setCode("0055726");
            printModel.setContent("商品名称");
            Prient p = new Prient(printModel);
            p.commonPrint(5, -20, 115, 85);
        } catch (Exception e1) {
        }
    }

    public Prient(PrintModel printModel) {
        this.printModel = printModel;
    }
}

