package com.spring.student_management.services;

import com.spring.student_management.dto.InstitutionDto;
import com.spring.student_management.dto.mapper.AddressMapper;
import com.spring.student_management.dto.mapper.AppUserMapper;
import com.spring.student_management.dto.mapper.InstitutionMapper;
import com.spring.student_management.exceptions.AddressTypeNotFoundException;
import com.spring.student_management.exceptions.AppException;
import com.spring.student_management.exceptions.InstitutionNotFoundException;
import com.spring.student_management.models.Address;
import com.spring.student_management.models.AddressType;
import com.spring.student_management.models.AppUser;
import com.spring.student_management.models.Institution;
import com.spring.student_management.repositories.AddressTypeRepository;
import com.spring.student_management.repositories.AppUserRepository;
import com.spring.student_management.repositories.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InstitutionService {
    private static final long ADDRESS_TYPE_INSTITUTION = 1L;
    private final InstitutionRepository institutionRepository;
    private final AppUserRepository appUserRepository;
    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository,
                              AppUserRepository userRepository,
                              AddressTypeRepository addressTypeRepository
    ) {
        this.institutionRepository = institutionRepository;
        this.appUserRepository = userRepository;
        this.addressTypeRepository = addressTypeRepository;
    }

    public List<InstitutionDto> findAll() {
        return InstitutionMapper.toDtoList(this.institutionRepository
                .findAll(Sort.by("institutionName").ascending()
                        .and(Sort.by("foundedDate").ascending())
                )
        );
    }

    private void setInstitutionAddress(Institution institution) throws AddressTypeNotFoundException {
        final Address newAddress = institution.getAddress();
        if (newAddress != null) {
            AddressType type = this.addressTypeRepository.findById(ADDRESS_TYPE_INSTITUTION)
                    .orElseThrow(() -> new AddressTypeNotFoundException(ADDRESS_TYPE_INSTITUTION));
            newAddress.setType(type);
        }
    }

    private void setInstitutionUsers(Institution institution) {
        final List<AppUser> appUsers = new LinkedList<>();
        if (institution.getAppUsers() != null && !institution.getAppUsers().isEmpty()) {
            institution.getAppUsers().forEach(appUser -> {
                final Optional<AppUser> userOptional = this.appUserRepository.findById(appUser.getId());
                userOptional.ifPresent(appUsers::add);
            });
            institution.setAppUsers(appUsers);
        }
    }

    public InstitutionDto saveInstitution(InstitutionDto institutionDto) {
        log.info("Trying to saveInstitution = {}", institutionDto);
        try {
            final Institution institution = InstitutionMapper.toModel(institutionDto);
            this.setInstitutionUsers(institution);
            this.setInstitutionAddress(institution);
            return InstitutionMapper.toDto(this.institutionRepository.save(institution));
        } catch (Exception e) {
            log.info("Unable to saveInstitution", e);
            throw new PersistenceException(e);
        }
    }

    private Institution findById(long id) throws InstitutionNotFoundException {
        return this.institutionRepository.findById(id)
                .orElseThrow(() -> new InstitutionNotFoundException(id));
    }

    public InstitutionDto getInstitutionById(long id) throws InstitutionNotFoundException {
        return InstitutionMapper.toDto(this.findById(id));
    }

    public InstitutionDto editInstitution(long id, InstitutionDto ins) {
        try {
            final Institution oldIns = this.findById(id);

            if (StringUtils.isNotBlank(ins.getInstitutionName())) oldIns.setInstitutionName(ins.getInstitutionName());
            if (ins.getDescription() != null) oldIns.setDescription(ins.getDescription());
            if (ins.getAddress() != null) oldIns.setAddress(AddressMapper.toModel(ins.getAddress()));
            if (ins.getFoundedDate() != null) oldIns.setFoundedDate(ins.getFoundedDate());
            if (ins.getAppUsers() != null) {
                List<AppUser> newAppUsers = AppUserMapper.toModelList(ins.getAppUsers());
                if (!oldIns.getAppUsers().equals(newAppUsers)) oldIns.setAppUsers(newAppUsers);
            }
            final Institution updatedInstitution = this.institutionRepository.save(oldIns);
            return InstitutionMapper.toDto(updatedInstitution);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public String delInstitutionByIdOrName(Object idOrName) throws AppException {
        if (idOrName.toString().matches("\\d+")) {
            final long insId = Long.parseLong(idOrName.toString());
            this.institutionRepository.deleteById(insId);
            return String.format("Successfully removed institution by id = %d", insId);
        } else if (idOrName instanceof String) {
            final String institutionName = idOrName.toString();
            this.institutionRepository.deleteByInstitutionName(institutionName);
            return String.format("Successfully removed institution by institutionName = %s", institutionName);
        } else {
            throw new AppException(String.format("Cannot remove institution by idOrName = %s", idOrName));
        }
    }

    public String deleteAllInstitutions() throws AppException {
        try {
            this.institutionRepository.deleteAll();
            return "Successfully removed all institutions!";
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

}
