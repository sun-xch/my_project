package com.springmvc.entity;

import com.springmvc.controller.validation.ValidGroup1;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Items {

    private String id;

    //校验名称在1到10字符之间 message是提示的错误信息
    //groups:此校验属于哪个分组，groups可以定义多个分组
    @Size(min=1,max=10,message="{items.name.length.error}",groups = {ValidGroup1.class})
    private String name;

    //非空校验
    @NotNull(message = "{items.price.isNULL}")
    private BigDecimal price;

    private String detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
