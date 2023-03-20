package com.springboot.address_Book_Backend.controller;

import com.springboot.address_Book_Backend.dto.AddressBookDTO;
import com.springboot.address_Book_Backend.dto.ResponseDTO;
import com.springboot.address_Book_Backend.entity.AddressBook;
import com.springboot.address_Book_Backend.service.IAddressBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/app")
public class AddressBookController {
    @Autowired(required = true)
    IAddressBookService addressBookService;

//    http://localhost:8080/app/get
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getAll(){
        List<AddressBook> addressBookList = addressBookService.showDetails();
        ResponseDTO responseDTO = new ResponseDTO("Details Found",addressBookList);

    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

//    http://localhost:8080/app/getbyid/1
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable long id){
        Optional<AddressBook> addressBook = addressBookService.showById(id);
        ResponseDTO responseDTO = new ResponseDTO("Details found by id",addressBook);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

//    http://localhost:8080/app/post
    @PostMapping("/post")
    public ResponseEntity<ResponseDTO> postData(@Valid @RequestBody AddressBookDTO addressBookDTO){      //@Valid is used to enable validation.required to ensure the request meet the constraints specified for JSON elements.
        AddressBook addressBook = addressBookService.postData(addressBookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data Added",addressBook);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

//    http://localhost:8080/app/put/id
    @PutMapping("/put/{id}")
    public ResponseEntity<ResponseDTO> updateData(@Valid @RequestBody AddressBookDTO addressBookDTO, @PathVariable long id){
        AddressBook addressBook = addressBookService.updateData(addressBookDTO,id);
        ResponseDTO responseDTO = new ResponseDTO("Data Updated",addressBookDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

//    http://localhost:8080/app/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteData(@PathVariable long id ){
         String msg = addressBookService.deleteData(id);
        ResponseDTO responseDTO = new ResponseDTO("Data has been Deleted",id);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


}
