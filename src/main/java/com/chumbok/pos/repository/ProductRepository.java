package com.chumbok.pos.repository;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    /**
     * Gets everything from the Products and then... does something, I guess.
     *
     * @param pageable to embed products on it
     * @return the products (?
     */
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE display_name = ?")
    boolean isProductExists(String displayName);

    @Query("SELECT p FROM Product p WHERE p.displayName LIKE %?1%")
    List<Product> findProductsByDisplayName(String displayName, Pageable pageable);

    @Query("delete from Product p where p.id in ?1")
      void deleteBulkProduct(List<Long> ids);


    @Query("SELECT new com.chumbok.pos.dto.ProductWithStockQuantity(p.id, p.displayName, p.vendor, p.catagory, p.brand, p.description, p.weight, p.barcode, SUM(s.quantiy)) FROM Stock s " +
            "RIGHT JOIN s.product p " +
            "GROUP BY p.id")
    List<ProductWithStockQuantity> productStock();

    /**
     * Method for getting a product list similar to productStock but without the products that don't have quantity
     * Also, it checks for products where the "disabled" status isn't active.
     *
     * @return a list of them products
     */
    @Query("SELECT new com.chumbok.pos.dto.ProductWithStockQuantity(p.id, p.displayName, p.vendor, p.catagory, p.brand, p.description, p.weight, p.barcode, SUM(s.quantiy)) FROM Stock s " +
            "RIGHT JOIN s.product p " +
            "WHERE p.disabled <> true " +
            "GROUP BY p.id HAVING SUM(s.quantiy) > 0")
    List<ProductWithStockQuantity> productHavingStock();

}