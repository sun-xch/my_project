package com.springmvc.dao;

import com.springmvc.entity.Items;

import java.util.List;

public interface ItemsMapper {

    public List<Items> queryItems(Items items);
}
