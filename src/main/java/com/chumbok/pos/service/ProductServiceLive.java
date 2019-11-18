package com.chumbok.pos.service;

import com.chumbok.pos.dto.ProductDTO;
import com.chumbok.pos.dto.ProductWithStockQuantity;
import com.chumbok.pos.entity.Product;
import com.chumbok.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Transactional
@Service
public class ProductServiceLive implements ProductService {

    //final private List<Product> books = Product.buildProducts();

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(long productId) {
        return productRepository.findOne(productId);
    }


   /* public Product findByPagination(Long id){

            List<Product> products =  productRepository.findProductsByPagination(id, new PageRequest(0, 3, Sort.Direction.ASC, "id"));

return product;
    }*/

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
    public Page<Product> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> listOfProducts;

        return null;
    }

    @Override
    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    /**
     * Crea un producto con base en la información obtenida del usuario. Establece algunas propiedades
     * por defecto puesto que el usuario no tendría por qué tener acceso a dichas características.
     *
     * @param productDTO con la información a establecer
     * @return el producto creado
     */
    @Override
    public Product createProduct(@Valid ProductDTO productDTO) {
        //no se pueden crear productos que tengan exactamente el mismo nombre
        if (productRepository.isProductExists(productDTO.getDisplayName())) {
            throw new IllegalArgumentException("Un producto con ese nombre ya existe.");
        }
        Product product = new Product();
        product.setQuantity(0); //la cantidad se inicializa en 0
        product.setDisabled(false); //el producto se inicializa habilitado
        product.setBarcode(productDTO.getRentPrice()); //precio de renta -> barcode
        product.setWeight(productDTO.getSalesPrice()); //precio de venta -> weight
        product.setCatagory(productDTO.getCatagory()); //establece la categoría recibida
        product.setDescription(productDTO.getDescription());
        product.setDisplayName(productDTO.getDisplayName());
        productRepository.save(product);
        return product;
    }

    /**
     * Método que implementa la venta con base en los valores obtenidos del usuario
     * @param productDTO with the new data from the user
     */
    @Override
    public void updateProduct(@Valid ProductDTO productDTO) {
        Product productById = productRepository.findOne(productDTO.getId());
        productById.setDisplayName(productDTO.getDisplayName()); //establece el nombre del producto
        productById.setVendor(productDTO.getVendor()); //establece la marca
        productById.setCatagory(productDTO.getCatagory()); //establece la categoría
        productById.setDescription(productDTO.getDescription()); //establece la descripción del producto
        productById.setWeight(productDTO.getSalesPrice()); //establece el precio de venta
        productById.setBarcode(productDTO.getRentPrice()); //establece el precio de renta
        productRepository.save(productById);
    }

    /**
     * Este método cambia el estado de un producto para no ser apto a la venta
     * con base en si actualmente es apto o no
     *
     * @param productId del producto cuyo estado se modificará
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
    public List<Product> searchProduct(String displayName, Pageable pageable) {
        return productRepository.findProductsByDisplayName(displayName, pageable);
    }
}