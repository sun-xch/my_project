package com.springmvc.service.impl;

import com.springmvc.dao.ItemsMapper;
import com.springmvc.entity.Items;
import com.springmvc.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;

    public List<Items> queryItems(Items items) {
        return itemsMapper.queryItems(items);
    }

    @Override
    public Items queryItemsById(Items items) {
        return itemsMapper.queryItemsById(items);
    }

    @Override
    public int updateItemById(Items items) {
        int i = itemsMapper.updateItemById(items);
        return i;
    }
}
