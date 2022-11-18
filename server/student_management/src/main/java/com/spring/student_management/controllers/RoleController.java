package com.spring.student_management.controllers;

import com.spring.student_management.dto.responses.CustomResponse;
import com.spring.student_management.dto.responses.ErrorResponseDto;
import com.spring.student_management.dto.responses.ResponseDto;
import com.spring.student_management.dto.RoleDto;
import com.spring.student_management.dto.mapper.RoleMapper;
import com.spring.student_management.exceptions.AppRoleNotFoundException;
import com.spring.student_management.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAllRoles() {
        log.info("getAllRoles");
        try {
            final List<RoleDto> roleDtoList = RoleMapper.toDtoList(this.roleRepository.findAll());
            log.info("Successfully getAllRoles");
            return ResponseEntity.ok(ResponseDto.createResponse(roleDtoList));
        } catch (Exception e) {
            log.error("Cannot getAllRoles", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomResponse> getRoleById(@PathVariable(name = "id") long roleId) {
        log.info("getRoleById");
        try {
            final RoleDto roleDto = RoleMapper.toDto(this.roleRepository.findById(roleId)
                    .orElseThrow(() -> new AppRoleNotFoundException(roleId))
            );
            log.info("Successfully getRoleById with: id = {}", roleId);
            return ResponseEntity.ok(ResponseDto.createResponse(roleDto));
        } catch (Exception e) {
            log.error("Cannot getRoleById: id = {}", roleId, e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }
}
