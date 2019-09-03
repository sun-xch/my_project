package com.springmvc.commons.print;

import java.awt.*;
import java.awt.print.*;


/**
 *         
 *  *  通用热敏打印机类
 *  
 */
public class Prient implements Printable {

    //打印内容
    private PrintModel printModel;

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        //设置标题字体
        g2d.setFont(new Font(null, Font.PLAIN, 10));
        //获取字符串宽度
        int strWidth = g2d.getFontMetrics().stringWidth(printModel.getTitle());
        //打印标题
        //标题位置 x坐标起始位置：纸宽/2-标题宽度/2  y坐标起始位置：纸高/10
        g2d.drawString(printModel.getTitle(), (int)(pf.getWidth()/2 - strWidth/2), (int)(pf.getHeight()/10));
        //设置内容信息字体
        g2d.setFont(new Font(null, Font.PLAIN, 8));
        //打印条形码
        //条形码位置 x坐标起始位置：边距/2  y坐标起始位置：纸高/6
        g2d.drawImage(printModel.getImage(), 15, (int)(pf.getHeight()/6), (int)pf.getWidth()-30,(int)(pf.getHeight()/3),null);
        //打印条码编号 x坐标起始位置：边距/2  y坐标位置： 2.5(纸高)/4
        g2d.drawString(printModel.getCode(), 15, (int)(2.5*(pf.getHeight())/4));
        //打印内容 x坐标起始位置：边距/2 y坐标起始位置： 3(纸高)/4
        g2d.drawString(printModel.getContent() , 15, (int)(3*(pf.getHeight())/4));
        return PAGE_EXISTS;
    }

    /**
     *PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
     * width = xxcm/25.4*72 height = xxcm/25.4*72
     * @param width 纸宽
     * @param height 纸高
     * @return
     */
    public void commonPrint(double width, double height) {

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
        p.setImageableArea(0, 0, width, height);
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
    }

    public Prient(PrintModel printModel) {
        this.printModel = printModel;
    }

    public static void main(String[] args) {
        try {
            Image src = Toolkit.getDefaultToolkit().getImage("D:\\picture\\13639685_123501617185_2.jpg");
            PrintModel printModel = new PrintModel();
            printModel.setTitle("标题");
            printModel.setImage(src);
            printModel.setCode("0055726");
            printModel.setContent("商品名称");
            Prient p = new Prient(printModel);

            p.commonPrint(113, 85);
        } catch (Exception e1) {
        }
    }

}

