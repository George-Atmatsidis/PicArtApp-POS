package com.chumbok.pos.service;

import com.chumbok.pos.dto.StockDTO;
import com.chumbok.pos.entity.Stock;

import java.util.Date;
import java.util.List;

public interface StockService {

    List<Stock> getAllStocks();

    Stock getStock(long stockId);

    Stock createStock(StockDTO stockDTO);

    void updateStock(Stock stock);

    void deleteStock(long stockId);

    Long totalQuantityInStock(Long productId);

    long cantidadDeAltasEnUnPeriodo(int mes, int año);

    long cantidadDeBajasEnUnPeriodo(int mes, int año);

    List<Stock> getAllStocksBetweenDates(int mes, int año);

}
