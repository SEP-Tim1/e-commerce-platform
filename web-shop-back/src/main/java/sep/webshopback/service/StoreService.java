package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;
import sep.webshopback.repositories.ProductRepository;
import sep.webshopback.repositories.StoreRepository;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

  
    public Store getById(long id) throws StoreNotFoundException {
        if(storeRepository.findById(id).isPresent()) return storeRepository.findById(id).get();
        throw new StoreNotFoundException();
    }

    public List<Store> getAllByName(String name){
        return storeRepository.findAllByName(name);
    }

    public Store getByOwnerId(long ownerId) throws StoreNotFoundException {
        Store store = storeRepository.findStoreByOwnerId(ownerId);
        if(store != null) return store;
        throw new StoreNotFoundException();
    }

}
