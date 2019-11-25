package com.chumbok.pos.service;

import com.chumbok.pos.dto.UserSalesDTO;
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
import java.util.List;

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
        productRepository.findOne(ventaDTO.getProductId()).setQuantity(productRepository.findOne(ventaDTO.getProductId()).getQuantity() - ventaDTO.getQuantity());
        return venta;
    }

    @Override
    public long quantityOfVentasByUser(long userid) {
        return ventaRepository.quantityOfVentasByUser(userid);
    }

    @Override
    public List<UserSalesDTO> howMuchEachUserSoldThisMonth(int month, int year) {
        return ventaRepository.howMuchEachUserSoldThisMonth(month, year);
    }

    @Override
    public List<Venta> getVentasEnElMes(int month, int year) {
        return ventaRepository.getVentasEnElMes(month, year);
    }

    @Override
    public long ventasTotalesPorMes(int month, int year) {
        return ventaRepository.ventasTotalesPorMes(month, year);
    }
}
