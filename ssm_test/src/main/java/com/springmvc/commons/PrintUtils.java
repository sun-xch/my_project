package com.springmvc.commons;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintUtils implements Printable {
    private String url;

    public PrintUtils(String url) {
        this.url = url;
    }

    @Override
    public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
        System.out.println("pageIndex="+pageIndex);
        Component c = null;

        //print string
        String str = "中华民族是勤劳、勇敢和富有智慧的伟大民族。";

        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) gra;

        //设置打印颜色为黑色
        g2.setColor(Color.black);

        //打印起点坐标
        double x = pf.getImageableX();
        double y = pf.getImageableY();

        switch(pageIndex){
            case 0:
                //设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
                //Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput
                Font font = new Font("新宋体", Font.PLAIN, 9);
                g2.setFont(font);//设置字体

                //BasicStroke bs_3=new BasicStroke(0.5f);

                float[] dash1 = {2.0f};

                //设置打印线的属性。
                //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
                g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
                //g2.setStroke(bs_3);//设置线宽
                float heigth = font.getSize2D();//字体高度
                System.out.println("x="+x);
                // -1- 用Graphics2D直接输出
                //首字符的基线(右下部)位于用户空间中的 (x, y) 位置处
                //g2.drawLine(10,10,200,300);

                Image src = Toolkit.getDefaultToolkit().getImage(url);
                g2.drawImage(src,(int)x,(int)y,c);
                int img_Height=src.getHeight(c);
                int img_width=src.getWidth(c);
                //System.out.println("img_Height="+img_Height+"img_width="+img_width) ;

                g2.drawString(str, (float)x, (float)y+1*heigth+img_Height);
                g2.drawLine((int)x,(int)(y+1*heigth+img_Height+10),(int)x+200,(int)(y+1*heigth+img_Height+10));
                g2.drawImage(src,(int)x,(int)(y+1*heigth+img_Height+11),c);

                return PAGE_EXISTS;

            default:

                return NO_SUCH_PAGE;

        }
    }

    public static void main(String[] args) {
        //获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new PrintUtils("D:\\store\\store\\20190809\\09\\FA\\FA0CFEDD03414B0A9B8F78C609527A27.jpg"));//设置打印类
        try {
            //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            boolean a=job.printDialog();
            if(a)
            {
                job.print();
            } else{
                job.cancel();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }

    }
}
