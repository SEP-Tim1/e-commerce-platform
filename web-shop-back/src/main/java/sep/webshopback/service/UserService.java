package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sep.webshopback.dtos.NewInfoDTO;
import sep.webshopback.dtos.UserRegistrationDTO;
import sep.webshopback.exceptions.EmailNotUniqueException;
import sep.webshopback.exceptions.UsernameNotUniqueException;
import sep.webshopback.model.Role;
import sep.webshopback.model.Store;
import sep.webshopback.model.User;
import sep.webshopback.repositories.StoreRepository;
import sep.webshopback.repositories.UserRepository;

import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user;
        } else throw new UsernameNotFoundException("There is no user with username " + username);
    }

    public User registration(UserRegistrationDTO newUser) throws EmailNotUniqueException, UsernameNotUniqueException {
        validateEmail(newUser.getEmail());
        validateUsername(newUser.getUsername());
        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail(), newUser.getRole());
        user = userRepository.save(user);
        if (user.getRole() == Role.USER) {
            return user;
        }
        Store store = new Store("initial name", user);
        storeRepository.save(store);
        return user;
    }

    public void updateInfo(@Valid NewInfoDTO infoDTO, long id) {
        User user = userRepository.findById(id).get();
        user.setFirstName(infoDTO.getFirstName());
        user.setLastName(infoDTO.getLastName());
        user.setPhone(infoDTO.getPhone());
        user.setAddress(infoDTO.getAddress());
        userRepository.save(user);
    }

    public User getById(long id){
        return userRepository.findById(id).get();
    }

    private void validateEmail(String email) throws EmailNotUniqueException {
        if (userRepository.findUserByEmail(email) != null)
            throw new EmailNotUniqueException();
    }

    private void validateUsername(String username) throws UsernameNotUniqueException {
        if (userRepository.findUserByUsername(username) != null)
            throw new UsernameNotUniqueException();
    }
}
