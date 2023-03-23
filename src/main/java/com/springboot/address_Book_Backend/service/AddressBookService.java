package com.springboot.address_Book_Backend.service;

import com.springboot.address_Book_Backend.dto.AddressBookDTO;
import com.springboot.address_Book_Backend.entity.AddressBook;
import com.springboot.address_Book_Backend.exception.AddressBookException;
import com.springboot.address_Book_Backend.repository.AddressBookRepository;
import com.springboot.address_Book_Backend.utility.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    AddressBookRepository addressBookRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public List<AddressBook> showDetails(){
        List<AddressBook> addressBook = addressBookRepository.findAll();
        return addressBook;
    }

    public Optional<AddressBook> showById(long id){
        Optional<AddressBook> addressBook = addressBookRepository.findById(id);
        if (addressBookRepository.findById(id).isPresent()){
        return addressBook;
        }
        else throw new AddressBookException("Id is Not Present to find");
    }

    public AddressBook postData(AddressBookDTO addressBookDTO){
    AddressBook addressBook = new AddressBook(addressBookDTO);
     addressBookRepository.save(addressBook);
     emailSenderService.sendMail(addressBook.getEmail(),"Registered in Address Book",addressBook.getId() + "\n" + addressBook.getEmail());
     return addressBook;
    }

    public AddressBook updateData(AddressBookDTO addressBookDTO, long id){
        AddressBook addressBook = addressBookRepository.findById(id).get();
        if (addressBookRepository.findById(id).isPresent()){
            addressBook.setId(addressBook.getId());
            addressBook.setName(addressBookDTO.getName());
            addressBook.setCity(addressBookDTO.getCity());
            addressBook.setState(addressBookDTO.getState());
            addressBook.setAddress(addressBookDTO.getAddress());
            addressBook.setPincode(addressBookDTO.getPincode());
            addressBook.setEmail(addressBookDTO.getEmail());
            addressBook.setPhoneNumber(addressBook.getPhoneNumber());
            addressBookRepository.save(addressBook);
            emailSenderService.sendMail(addressBook.getEmail(),"Employee Details has been Updated for " +addressBook.getId(),"Employee Details has been Updated for " +addressBook.getId() );
            return addressBook;

        }
        else throw new AddressBookException("Id is Not found for Update");
    }

   public String deleteData(long id){
        if (addressBookRepository.findById(id).isPresent()){
            addressBookRepository.deleteById(id);
        }
        throw new AddressBookException("Id not found to delete");
   }

}
