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

    //Utilizado para acceder a la fecha de hoy
    private Calendar today = Calendar.getInstance();
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
        Venta venta = new Venta();
        venta.setProduct(productRepository.findOne(ventaDTO.getIdProduct()));
        venta.setQuantity(ventaDTO.getQuantity());
        venta.setSalesDate(today.getTime());
        venta.setUser(userRepository.findByEmail(ventaDTO.getEmail()));
        ventaRepository.save(venta);
        return venta;
    }
}
