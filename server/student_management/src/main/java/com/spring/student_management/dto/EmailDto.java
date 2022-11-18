package com.spring.student_management.dto;

import lombok.*;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class EmailDto implements Serializable {

    private String recipient;
    private String subject;
    private String message;
    private String firstName;
    private String lastName;
    private String email;

    public String initContent() {
        return String.format("<h3>You have received a new message from %s %s</h3><br/>", this.firstName, this.lastName) +
                this.getMessage() + "<hr/>Email: " + this.getEmail();
    }

}
