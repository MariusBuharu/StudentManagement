package com.spring.student_management.services;

import com.spring.student_management.dto.AppUserDto;
import com.spring.student_management.dto.mapper.AppUserMapper;
import com.spring.student_management.dto.RoleDto;
import com.spring.student_management.dto.utils.PasswordChangerDto;
import com.spring.student_management.exceptions.AddressTypeNotFoundException;
import com.spring.student_management.exceptions.AppException;
import com.spring.student_management.exceptions.AppUserNotFoundException;
import com.spring.student_management.models.Address;
import com.spring.student_management.models.AddressType;
import com.spring.student_management.models.AppUser;
import com.spring.student_management.models.Role;
import com.spring.student_management.repositories.AddressTypeRepository;
import com.spring.student_management.repositories.AppUserRepository;
import com.spring.student_management.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.format;


@Service
@Transactional
@Slf4j
public class AppUserService {
    private static final long ROLE_STUDENT_ID = 1L;
    private static final long ADDRESS_TYPE_USER = 2L;
    private static final String ROLE_NOT_FOUND = "Role with id %d not found!";
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AppUserService(AppUserRepository userRepository,
                          RoleRepository roleRepository,
                          AddressTypeRepository addressTypeRepository,
                          BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.encoder = encoder;
    }

    public List<AppUserDto> findAll() {
        log.info("findAll");
        return AppUserMapper.toDtoList(this.userRepository.findAll());
    }

    //Helper method
    private void setUserRole(AppUser user) throws AppException {
        final Role role = user.getRole();
        if (role == null) {
            user.setRole(this.roleRepository.findById(ROLE_STUDENT_ID)
                    .orElseThrow(() -> new AppException(format(ROLE_NOT_FOUND, ROLE_STUDENT_ID))));
        } else {
            user.setRole(this.roleRepository.findById(role.getId())
                    .orElseThrow(() -> new AppException(format(ROLE_NOT_FOUND, role.getId()))));
        }
    }

    //Helper method
    private void setUserAddress(AppUser user) throws AddressTypeNotFoundException {
        if (user.getAddress() != null) {
            Address newAddress = user.getAddress();
            AddressType addressType = this.addressTypeRepository.findById(ADDRESS_TYPE_USER)
                    .orElseThrow(() -> new AddressTypeNotFoundException(ADDRESS_TYPE_USER));
            newAddress.setType(addressType);
        }
    }

    public AppUserDto saveUser(AppUserDto userDto) {
        try {
            log.info("Trying to saveUser = {}", userDto);
            final AppUser user = AppUserMapper.toModel(userDto);
            final String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            this.setUserRole(user);
            this.setUserAddress(user);
            AppUser saved = this.userRepository.save(user);
            return AppUserMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Unable to save user", e);
            throw new PersistenceException(e);
        }
    }

    public AppUserDto editUser(long id, AppUserDto userDto) throws AppUserNotFoundException, AppException {
        AppUser foundUser = findByIdOptional(id);
        RoleDto role = userDto.getRole();

        if (StringUtils.isNotBlank(userDto.getFirstName())) foundUser.setFirstName(userDto.getFirstName());
        if (StringUtils.isNoneBlank(userDto.getLastName())) foundUser.setLastName(userDto.getLastName());
        if (StringUtils.isNoneBlank(userDto.getUsername())) foundUser.setUsername(userDto.getUsername());
        if (StringUtils.isNotBlank(userDto.getPhoneNumber())) foundUser.setPhoneNumber(userDto.getPhoneNumber());
        if (StringUtils.isNoneBlank(userDto.getEmail())) foundUser.setEmail(userDto.getEmail());
        if (userDto.getDob() != null) foundUser.setDob(userDto.getDob());
        if (userDto.getIsActive() != null) foundUser.setActive(userDto.getIsActive());
        if (StringUtils.isNoneBlank(userDto.getPassword())) {
            final String encodedPassword = encoder.encode(userDto.getPassword());
            foundUser.setPassword(encodedPassword);
        }
        if (role != null && role.getId() != null) {
            foundUser.setRole(roleRepository.findById(role.getId())
                    .orElseThrow(() -> new AppException(String.format(ROLE_NOT_FOUND, role.getId()))));
        }

        return AppUserMapper.toDto(this.userRepository.save(foundUser));
    }

    private AppUser findByIdOptional(long id) throws AppUserNotFoundException {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
    }

    public AppUserDto findById(long id) throws AppUserNotFoundException {
        return AppUserMapper.toDto(this.findByIdOptional(id));
    }

    public AppUserDto findByUsernameOrEmail(String usernameOrEmail) throws AppUserNotFoundException {
        return AppUserMapper.toDto(
                this.userRepository.findByUserNameOrEmail(usernameOrEmail)
                        .orElseThrow(() -> new AppUserNotFoundException(usernameOrEmail))
        );
    }

    public String deleteByIdOrUsernameOrEmail(Object data) throws AppUserNotFoundException {
        AppUser user;
        String message;
        if (data.toString().matches("\\d+")) {
            long id = Long.parseLong(data.toString());
            user = findByIdOptional(id);
            message = format("Successfully removed user with id %d", id);
        } else {
            user = this.userRepository.findByUserNameOrEmail(data.toString())
                    .orElseThrow(() -> new IllegalStateException(format("User with param: %s not found!", data)));
            message = format("Successfully removed user with username or email %s", data);
        }
        this.userRepository.delete(user);
        log.info(message);
        return message;
    }

    public String changePassword(long id, PasswordChangerDto passwordChangerDto) throws AppException {
        log.info("Trying to changePassword with params: id = {}, passwordChangerDto = {}", id, passwordChangerDto);
        try {
            final AppUserDto appUserDto = AppUserMapper.toDto(this.findByIdOptional(id));
            boolean passwordMatches = this.encoder.matches(passwordChangerDto.getOldPassword(), appUserDto.getPassword());
            if (passwordMatches) {
                if (passwordChangerDto.getNewPassword().equals(passwordChangerDto.getConfirmedPassword())) {
                    appUserDto.setPassword(this.encoder.encode(passwordChangerDto.getNewPassword()));
                    this.userRepository.save(AppUserMapper.toModel(appUserDto));
                    log.info("Password successfully changed for user with id = {}", id);
                    return "Password successfully changed!";
                } else {
                    log.warn("Password does not match! params: id = {}, passwordChangerDto = {}", id, passwordChangerDto);
                    return "Passwords does not match!";
                }
            } else {
                log.error("Old password is incorrect! params: id = {}, passwordChangerDto = {}", id, passwordChangerDto);
                return "Old password is incorrect!";
            }
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to change password for user with id = {}", id, e);
            throw new AppException(e);
        }
    }
}
