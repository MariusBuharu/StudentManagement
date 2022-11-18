package com.spring.student_management.controllers;

import com.spring.student_management.dto.AppUserDto;
import com.spring.student_management.dto.responses.CustomResponse;
import com.spring.student_management.dto.responses.ErrorResponseDto;
import com.spring.student_management.dto.responses.ResponseDto;
import com.spring.student_management.dto.utils.PasswordChangerDto;
import com.spring.student_management.exceptions.AppException;
import com.spring.student_management.services.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(path = "/api/v1/users", consumes = ALL_VALUE)
@Slf4j
public class UserController {

    private final AppUserService userService;

    @Autowired
    public UserController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> getAllUsers() {
        log.info("getAllUsers");
        try {
            return ResponseEntity.ok(ResponseDto.createResponse(this.userService.findAll()));
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to getAllUsers", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> getUserById(@PathVariable(name = "id") long id) {
        log.info("getUserById with: id = {}", id);
        try {
            final AppUserDto userDto = this.userService.findById(id);
            return ResponseEntity.ok(ResponseDto.createResponse(userDto));
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to getUserById with: id = {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }
    @PostMapping(consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveUser(@RequestBody AppUserDto userDto) {

        try {
            AppUserDto savedUser = this.userService.saveUser(userDto);

            return ResponseEntity.ok(ResponseDto.createResponse(savedUser));
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to saveUser", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @PutMapping(path = "/{id}",
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> editUser(@PathVariable("id") long id, @RequestBody AppUserDto userDto) {
        try {
            return ResponseEntity.ok(ResponseDto.createResponse(this.userService.editUser(id, userDto)));
        } catch (Exception e) {
            log.error("Unexpected error occurred trying to editUser", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @PutMapping(path = "/password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") long id,
                                                 @RequestBody PasswordChangerDto passwordChangerDto) throws AppException {

        return ResponseEntity.ok(this.userService.changePassword(id, passwordChangerDto));
    }

    @DeleteMapping("/{data}")
    public ResponseEntity<String> delete(@PathVariable("data") Object data) throws AppException {
        try {
            return ResponseEntity.ok(this.userService.deleteByIdOrUsernameOrEmail(data));
        } catch (Exception e) {
            log.error("Unable to remove user!", e);
            throw new AppException(e);
        }
    }
}
