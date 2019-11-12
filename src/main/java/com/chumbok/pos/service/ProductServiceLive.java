package com.chumbok.pos.service;

import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProductServiceLive implements ProductService {

    @Autowired
    private ProductRepository productRepository;




    @Override
    public Product getProduct(long productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


   /* public Product findByPagination(Long id){

            List<Product> products =  productRepository.findProductsByPagination(id, new PageRequest(0, 3, Sort.Direction.ASC, "id"));

return product;
    }*/

   public Page<Product> findAllByPage(Pageable pageable){
       Page<Product> products =  productRepository.findAll(pageable);
       return products;
    }

   @Override
    public Page<ProductWithStockQuantity> findProductWithStockQuantityByPage(Pageable pageable) {

       List<ProductWithStockQuantity> list= productRepository.productStock();

        Page<ProductWithStockQuantity> pageProductListWithStockQuantity  = new PageImpl<>(list);

        return pageProductListWithStockQuantity;
    }

    @Override
    public Page<ProductWithStockQuantity> findProductWithStockQuantityByPageGraterThanZero(Pageable pageable) {
        List<ProductWithStockQuantity> list = productRepository.productHavingStock(); //Gets a list of products that have stock
        Page<ProductWithStockQuantity> pageProductListWithStockQuantity = new PageImpl<>(list); //Adds said list to the page
        return pageProductListWithStockQuantity; //returns said fucking page
    }


    @Override
    public Product createProduct(Product product) {

        //no se pueden crear productos que tengan exactamente el mismo nombre
        if (productRepository.isProductExists(product.getDisplayName())) {
            throw new IllegalArgumentException("Un producto con ese nombre ya existe.");
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public void updateProduct(Product product) {

        Product productById = productRepository.findOne(product.getId());

        productById.setDisplayName(product.getDisplayName());
        productById.setVendor(product.getVendor());
        productById.setCatagory(product.getCatagory());
        productById.setBrand(product.getBrand());
        productById.setDescription(product.getDescription());
        productById.setWeight(product.getWeight());
        productById.setBarcode(product.getBarcode());

        productRepository.save(productById);
    }

    /**
     * Este metodo cambia el estado de un producto a deshabilitado
     * con base en si actualmente se encuentra deshabilitado o no
     * Solamente lo togglea'
     *
     * @param productId del producto a deshabilitar
     */
    @Override
    public void deleteProduct(long productId) {
        Product product = productRepository.findOne(productId);
        if (product.isDisabled()) {
            product.setDisabled(false);
        } else {
            product.setDisabled(true);
        }
    }

    @Override
    public void deleteBulkProduct(List<Long> ids) {
        productRepository.deleteBulkProduct(ids);
    }

    @Override
    public List<Product> searchProduct(String displayName) {
        return  productRepository.findProductsByDisplayName(displayName);
    }
}