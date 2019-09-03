package com.springmvc.commons.print;

import java.awt.*;

/**
 *         
 *  *  热敏打印机辅助类
 *  
 */
public class PrintModel {

    //标题
    private String title;

    //内容
    private String content;

    //代码
    private String code;

    //图片信息
    private Image image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
