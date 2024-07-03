package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.constants.Provider;
import JavaWeb.GameAccount.constants.Role;
import JavaWeb.GameAccount.model.User;
import JavaWeb.GameAccount.repositories.IRoleRepository;
import JavaWeb.GameAccount.repositories.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;


    @Transactional(isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class})
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) throws
            UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public void saveOauthUser(String email, @NotNull String username) {
        User user;
        if (userRepository.existsByUsername(username)) {
            user = userRepository.findByUsername(username);
        } else {
            user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(new BCryptPasswordEncoder().encode(username));
            user.setProvider(Provider.GOOGLE.value);
        }
        var userRole = roleRepository.findRoleById(Role.USER.value);
        user.getRoles().add(userRole);
        System.out.println("Saving user: " + user + " with roles: " + user.getRoles());
        userRepository.save(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class, Throwable.class})
    public void setDefaultRole(String username) {
        var user = userRepository.findByUsername(username);
        var userRole = roleRepository.findRoleById(Role.USER.value);
        user.getRoles().add(userRole);
    }
    // Kiểm tra sự tồn tại của username.
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Kiểm tra sự tồn tại của email.
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Kiểm tra sự tồn tại của phone.
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}