package com.chumbok.pos.service;

import com.chumbok.pos.dto.VentaDTO;
import com.chumbok.pos.entity.Venta;
import com.chumbok.pos.repository.ProductRepository;
import com.chumbok.pos.repository.UserRepository;
import com.chumbok.pos.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Calendar;

@Transactional
@Service("ventaService")
public class VentaServiceLive implements VentaService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VentaRepository ventaRepository;

    /**
     * Teóricamente, aquí estoy creando una venta de verdad
     * con base en la información que se está enviando desde
     * el formulario en HTML.
     *
     * @param ventaDTO que es el objeto de intercambio de datos
     * @return venta que es el objeto venta realizado
     */
    @Override
    public Venta createVenta(@Valid VentaDTO ventaDTO) {
        //Utilizado para acceder a la fecha de hoy
        Venta venta = new Venta();
        venta.setProduct(productRepository.findOne(ventaDTO.getProductId()));
        venta.setQuantity(ventaDTO.getQuantity());
        venta.setSalesDate(Calendar.getInstance().getTime());
        venta.setUser(userRepository.findByEmail(ventaDTO.getEmail()));
        ventaRepository.save(venta);
        //this is another mf trigger in Java, holy smokes
        //Product product = productRepository.findOne(ventaDTO.getIdProduct());
        //product.setQuantity(product.getQuantity() - ventaDTO.getQuantity());
        //this is a damn trigger made with fucking java -> holy smokes, what have we done ; help
        //productRepository.findOne(venta.getProduct().getId()).setQuantity(productRepository.findOne(venta.getProduct().getId()).getQuantity() - venta.getQuantity());
        //productRepository.findOne(ventaDTO.getIdProduct()).setQuantity(productRepository.findOne(ventaDTO.getIdProduct()).getQuantity() - ventaDTO.getQuantity());
        return venta;
    }
}
