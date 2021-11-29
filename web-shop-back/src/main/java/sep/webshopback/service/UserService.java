package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sep.webshopback.dtos.NewInfoDTO;
import sep.webshopback.dtos.UserRegistrationDTO;
import sep.webshopback.model.User;
import sep.webshopback.repositories.UserRepository;

import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user;
        } else throw new UsernameNotFoundException("There is no user with username " + username);
    }

    public User registration(UserRegistrationDTO newUser){
        User user = new User(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), newUser.getRole());
        return userRepository.save(user);
    }

    public void updateInfo(@Valid NewInfoDTO infoDTO, long id) {
        User user = userRepository.findById(id).get();
        user.setFirstName(infoDTO.getFirstName());
        user.setLastName(infoDTO.getLastName());
        user.setPhone(infoDTO.getPhone());
        user.setAddress(infoDTO.getAddress());
        userRepository.save(user);
    }
}
