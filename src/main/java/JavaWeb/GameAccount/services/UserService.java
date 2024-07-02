package JavaWeb.GameAccount.services;

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
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.
    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        var user = userRepository.findByUsername(username);
        var userRole = roleRepository.findRoleById(Role.USER.value);
        user.getRoles().add(userRole);
    }
    // Tải thông tin chi tiết người dùng để xác thực.
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

    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<User> findByUsername(String username) throws
            UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username));
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