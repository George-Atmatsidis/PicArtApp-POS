package com.chumbok.pos.service;

import com.chumbok.pos.dto.ReturnVoucherDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.Renta;
import com.chumbok.pos.entity.ReturnVoucher;
import com.chumbok.pos.repository.ProductRepository;
import com.chumbok.pos.repository.RentaRepository;
import com.chumbok.pos.repository.ReturnVoucherRepository;
import com.chumbok.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

@Transactional
@Service
public class ReturnVoucherServiceLIve implements ReturnVoucherService {

    @Autowired
    RentaRepository rentaRepository;

    @Autowired
    ReturnVoucherRepository returnVoucherRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<ReturnVoucher> findAll() {
        return returnVoucherRepository.findAll();
    }

    @Override
    public List<ReturnVoucher> findByCustomer(Customer customer) {
        return null;
    }

    @Override
    public void createVoucher(@Valid ReturnVoucherDTO returnVoucherDTO) {
        ReturnVoucher returnVoucher = new ReturnVoucher();
        rentaRepository.findOne(returnVoucherDTO.getIdRenta()).setActive(false); //deactivates this renta
        returnVoucher.setRenta(rentaRepository.findOne(returnVoucherDTO.getIdRenta()));
        returnVoucher.setDateWhenTheReturnWasMade(returnVoucherDTO.getDateOfReturn());
        returnVoucher.setComentary(returnVoucherDTO.getComments());
        //
        Renta rentaARevisar = rentaRepository.findOne(returnVoucherDTO.getIdRenta());
        if (rentaARevisar.getDateOfReturn().compareTo(Calendar.getInstance().getTime()) <= 0) { //revisa que la fecha de entrega no sea mayor a la fecha de hoy
            returnVoucherDTO.setStatus("La devolución se realizó fuera de tiempo.");
        } else { //si la fecha de devolución es menor o igual a hoy, es una devolución válida
            returnVoucherDTO.setStatus("La devolución se realizó a tiempo.");
        }
        returnVoucher.setUser(userRepository.findByEmail(returnVoucherDTO.getUserMakingTheReturn()));
        returnVoucher.setWasItMadeOnTime(returnVoucherDTO.getStatus());
        //saving this shit
        returnVoucherRepository.save(returnVoucher);
        //the good old trick of a java trigger
        returnVoucher.getRenta().getProduct().setQuantity(returnVoucher.getRenta().getProduct().getQuantity() + Math.abs(rentaRepository.findOne(returnVoucherDTO.getIdRenta()).getQuantity()));
    }

    @Override
    public ReturnVoucher findOne(long id) {
        return returnVoucherRepository.findOne(id);
    }
}
