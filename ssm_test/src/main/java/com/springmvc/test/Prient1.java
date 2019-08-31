package com.springmvc.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *         
 *  *  通用热敏打印机类
 *  
 */
public class Prient1 implements Printable {

    //打印内容
    private List<PrintModel> printModelList;

    public Prient1(List<PrintModel> printModelList) {
        this.printModelList = printModelList;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.translate(pf.getImageableX(), pf.getImageableY());
        //设置标题字体
        g2d.setFont(new Font(null, Font.PLAIN, 10));
        //获取字符串宽度
        int strWidth = g2d.getFontMetrics().stringWidth(printModelList.get(pageIndex).getTitle());
        //打印标题
        //标题位置 x坐标起始位置：纸宽/2-标题宽度/2  y坐标起始位置：纸高/10
        g2d.drawString(printModelList.get(pageIndex).getTitle(), (int) (pf.getWidth() / 2 - strWidth / 2), (int) (pf.getHeight() / 10));
        //设置内容信息字体
        g2d.setFont(new Font(null, Font.PLAIN, 8));
        //打印条形码
        //条形码位置 x坐标起始位置：边距/2  y坐标起始位置：纸高/6
        g2d.drawImage(printModelList.get(pageIndex).getImage(), 15, (int) (pf.getHeight() / 6), (int) pf.getWidth() - 30, (int) (pf.getHeight() / 3), null);
        //打印条码编号 x坐标起始位置：边距/2  y坐标位置： 2.5(纸高)/4
        g2d.drawString(printModelList.get(pageIndex).getCode(), 15, (int) (2.5 * (pf.getHeight()) / 4));
        //打印内容 x坐标起始位置：边距/2 y坐标起始位置： 3(纸高)/4
        g2d.drawString(printModelList.get(pageIndex).getContent(), 15, (int) (3 * (pf.getHeight()) / 4));
        // 存在打印页时 0，继续打印工作
        return PAGE_EXISTS;
    }

    /**
     * PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
     * width = xxcm/25.4*72 height = xxcm/25.4*72
     * @param width  纸宽
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
        //book.append(new Prient1(this.printModelList), pf);
        book.append(new Prient1(this.printModelList), pf, this.printModelList.size());
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);
        try {
            //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            /*boolean flag = job.printDialog();
            if (flag) {
                job.print();
            } else {
                job.cancel();
            }*/
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Image src = Toolkit.getDefaultToolkit().getImage("D:\\picture\\13639685_123501617185_2.jpg");
            PrintModel aa = new PrintModel();
            aa.setTitle("标题");
            aa.setImage(src);
            aa.setCode("0055726");
            aa.setContent("商品名称");
            PrintModel bb = new PrintModel();
            bb.setTitle("标题1");
            bb.setImage(src);
            bb.setCode("00557261");
            bb.setContent("商品名称1");
            List<PrintModel> list = new ArrayList<PrintModel>();
            list.add(aa);
            list.add(bb);
            Prient1 p = new Prient1(list);

            p.commonPrint(113, 85);
        } catch (Exception e1) {
        }
    }

    public void printImage(File imageFile) throws IOException {
        Image image = ImageIO.read(imageFile);
    }

}

