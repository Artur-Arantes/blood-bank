package br.com.blood.bank.service;

import br.com.blood.bank.domain.Contact;
import br.com.blood.bank.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final  ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveContact(final Long idEntry, Contact dtoContact) {
        contactRepository.findById(idEntry)
                .map(contact -> {
                    contact.setCel(dtoContact.getCel());
                    contact.setHomePhone(dtoContact.getHomePhone());
                    return contactRepository.save(contact);
                })
                .orElseGet(() -> {
                    dtoContact.setIdPerson(idEntry);
                    return contactRepository.save(dtoContact);
                });
    }
}
