package com.spring.student_management.dto.responses;

import com.spring.student_management.dto.parents.PrimaryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto implements Serializable, CustomResponse {

    private static final long serialVersionUID = 4438194440128196285L;

    private transient Object data;
    public static <T extends PrimaryDto> ResponseDto createResponse(T data) {

        return new ResponseDto(data);
    }
    public static <T extends PrimaryDto> ResponseDto createResponse(List<T> dataList) {

        return new ResponseDto(dataList);
    }
}
