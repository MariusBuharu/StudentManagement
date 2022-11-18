package com.spring.student_management.dto.responses;

import com.spring.student_management.dto.responses.CustomResponse;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ErrorResponseDto implements Serializable, CustomResponse {
    private static final long serialVersionUID = 4235416092422110325L;

    private String additionalMsg;
    private String errMsg;
    private String statusTxt;
    private int status;
}
