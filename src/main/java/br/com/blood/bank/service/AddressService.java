package br.com.blood.bank.service;

import br.com.blood.bank.domain.Address;
import br.com.blood.bank.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void saveAddress(final Long idEntry, Address dtoAddress) {
        addressRepository.findById(idEntry)
                .map(address -> {
                    address.setAddress(dtoAddress.getAddress());
                    address.setCity(dtoAddress.getCity());
                    address.setState(dtoAddress.getState());
                    address.setNumber(dtoAddress.getNumber());
                    address.setZipNumber(dtoAddress.getZipNumber());
                    address.setNeighborhood(dtoAddress.getNeighborhood());
                    return addressRepository.save(address);
                })
                .orElseGet(() -> {
                    dtoAddress.setIdPerson(idEntry);
                    return addressRepository.save(dtoAddress);
                });
    }
}
