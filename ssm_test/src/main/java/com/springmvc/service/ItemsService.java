package com.springmvc.service;

import com.springmvc.entity.Items;

import java.util.List;

public interface ItemsService {

    public List<Items> queryItems(Items items);
}
