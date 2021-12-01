package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep.webshopback.dtos.ProductUserViewDTO;
import sep.webshopback.dtos.StoreBasicInfoDTO;
import sep.webshopback.dtos.StoreNameDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;
import sep.webshopback.repositories.ProductRepository;
import sep.webshopback.repositories.StoreRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String getStoreNameByOwnerId(long ownerId) throws StoreNotFoundException {
        Store store = storeRepository.findStoreByOwnerId(ownerId);
        if(store != null) return store.getName();
        throw new StoreNotFoundException();
    }

    public void setStoreNameByOwnerId(long ownerId, String name) throws StoreNotFoundException {
        Store store = storeRepository.findStoreByOwnerId(ownerId);
        if(store != null) {
            store.setName(name);
            storeRepository.save(store);
        } else
            throw new StoreNotFoundException();
    }

    public List<StoreBasicInfoDTO> getAllBasicInfo() {
        return storeRepository.findAll().stream().
                sorted(Comparator.comparing(Store::getName)).
                map(s -> new StoreBasicInfoDTO(
                        s.getId(),
                        s.getName(),
                        s.getOwner().getEmail(),
                        s.getOwner().getPhone())).
                collect(Collectors.toList());
    }

    public StoreBasicInfoDTO getBasicInfoById(long id) throws StoreNotFoundException {
        Store store = getById(id);
        return new StoreBasicInfoDTO(
                store.getId(),
                store.getName(),
                store.getOwner().getEmail(),
                store.getOwner().getPhone(),
                store.getProducts().stream().map(p -> new ProductUserViewDTO(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getImageUrl()
                )).collect(Collectors.toList())
        );
    }
}
