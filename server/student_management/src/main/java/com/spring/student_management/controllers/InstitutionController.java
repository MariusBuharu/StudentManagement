package com.spring.student_management.controllers;

import com.spring.student_management.dto.responses.CustomResponse;
import com.spring.student_management.dto.responses.ErrorResponseDto;
import com.spring.student_management.dto.InstitutionDto;
import com.spring.student_management.dto.responses.ResponseDto;
import com.spring.student_management.exceptions.AppException;
import com.spring.student_management.services.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;


@RestController
@RequestMapping(path = "/api/v1/institutions")
//@CrossOrigin
@Slf4j
public class InstitutionController {
    private final InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> getInstitutions() {
        try {
            final List<InstitutionDto> institutionDtoList = this.institutionService.findAll();
            final ResponseDto institutionsResponse = ResponseDto.createResponse(institutionDtoList);
            return ResponseEntity.ok(institutionsResponse);
        } catch (Exception e) {
            log.error("Unable to fetch institutions!", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> getInstitutionById(@PathVariable("id") long id) {
        log.info("getInstitutionById with: id = {}", id);
        try {
            InstitutionDto institutionDto = this.institutionService.getInstitutionById(id);
            ResponseDto institutionResponse = ResponseDto.createResponse(institutionDto);
            return ResponseEntity.ok(institutionResponse);
        } catch (Exception e) {
            log.error("Unable to findInstitutionById with: id = {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> saveInstitution(@RequestBody InstitutionDto institutionDto) {
        log.info("Trying to saveInstitution = {}", institutionDto);
        try {
            InstitutionDto savedInstitution = this.institutionService.saveInstitution(institutionDto);
            ResponseDto responseDto = ResponseDto.createResponse(savedInstitution);
            log.info("Successfully saved institution = {}", savedInstitution);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("Unable to saveInstitution = {}", institutionDto, e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @PutMapping(path = "/{id}",
            consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE, APPLICATION_FORM_URLENCODED_VALUE},
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> editInstitution(@PathVariable(name = "id") long id,
                                                          @RequestBody InstitutionDto institutionDto) {
        log.info("Trying to editInstitution with id = {}, institutionDto = {}", id, institutionDto);
        try {
            InstitutionDto editedInstitution = this.institutionService.editInstitution(id, institutionDto);
            ResponseDto responseDto = ResponseDto.createResponse(editedInstitution);
            log.info("Successfully editInstitution = {}", editedInstitution);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("Unable to editInstitution with id = {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .statusTxt(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .errMsg(e.getMessage())
                            .build());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllInstitutions() throws AppException {
        log.info("Trying to deleteAllInstitutions");
        try {
            final String message = this.institutionService.deleteAllInstitutions();
            log.info(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("Unable to deleteAllInstitutions", e);
            throw new AppException(e);
        }
    }

    @DeleteMapping(path = "/{idOrName}")
    public ResponseEntity<String> deleteInstitution(@PathVariable("idOrName") Object idOrName) throws AppException {
        log.info("Trying to deleteInstitution with: idOrName = {}", idOrName);
        try {
            String message = this.institutionService.delInstitutionByIdOrName(idOrName);
            log.info(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("Unable to deleteInstitution with: idOrName = {}", idOrName, e);
            throw new AppException(e);
        }
    }
}
