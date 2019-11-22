package com.chumbok.pos.service;

import com.chumbok.pos.dto.StockDTO;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.entity.Stock;
import com.chumbok.pos.repository.ProductRepository;
import com.chumbok.pos.repository.StockRepository;
import com.chumbok.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class StockServiceLive implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Stock getStock(long stockId) {
        return stockRepository.findOne(stockId);
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * This method creates a stock in both the deprecated way and the new way
     *
     * @param stockDTO of the stock modification inserted
     * @return idk, the stock (?
     */
    @Override
    public Stock createStock(StockDTO stockDTO) {
        Stock stockById = new Stock();
        stockById.setPurchasePrice(stockDTO.getPurchasePrice());
        stockById.setSalePrice(stockDTO.getSalePrice());
        stockById.setStockEntryDate(stockDTO.getStockEntryDate());
        stockById.setStockExpireDate(stockDTO.getStockExpireDate());
        stockById.setQuantiy(stockDTO.getQuantiy());
        stockById.setUser(userRepository.findByEmail(stockDTO.getUser())); //this makes sure we use the current logged in user
        Product product = productRepository.findOne(stockDTO.getProductId());
        stockById.setProduct(product);
        stockRepository.save(stockById);
        //now, let's modify product quantity || we shall get a - or a + from any controller calling, so, it should work
        product.setQuantity(product.getQuantity() + stockDTO.getQuantiy());
        return stockById;
    }

    @Override
    public void updateStock(Stock stock) {

        Stock stockById = getStock(stock.getId());

        stockById.setPurchasePrice(stock.getPurchasePrice());
        stockById.setSalePrice(stock.getSalePrice());

        stockById.setStockEntryDate(stock.getStockEntryDate());
        stockById.setStockExpireDate(stock.getStockExpireDate());
        stockById.setQuantiy(stock.getQuantiy());

        stockRepository.save(stockById);
    }

    @Override
    public void deleteStock(long stockId) {
        stockRepository.delete(stockId);
    }

    @Override
    public Long totalQuantityInStock(Long productId) {
        return stockRepository.totalQuantityInStock(productId);
    }

    @Override
    public long cantidadDeAltasEnUnPeriodo(int month, int year) {
        return stockRepository.totalAltasByMonthAndYear(month, year);
    }

    @Override
    public long cantidadDeBajasEnUnPeriodo(int month, int year) {
        return stockRepository.totalBajasByMonthAndYear(month, year);
    }

    @Override
    public List<Stock> getAllStocksBetweenDates(int month, int year) {
        return stockRepository.findAllByStockEntryDateByMonthAndYear(month, year);
    }
}
