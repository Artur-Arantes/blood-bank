package br.com.blood.bank.repository;

import br.com.blood.bank.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
