package com.springmvc.dto;


import com.springmvc.entity.Items;

import java.util.List;

public class ItemsVo {

    private Items items;

    private List<Items> itemsList;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }
}
