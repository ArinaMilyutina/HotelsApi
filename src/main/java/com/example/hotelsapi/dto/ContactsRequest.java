package com.example.hotelsapi.dto;

import lombok.*;


import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactsRequest {
    @Size(min = 3, max = 254, message = "Email must be between 3 and 254 characters")
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^\\+\\d[\\d-]{0,19}\\d$", message = "Invalid phone number format. Must start with '+' and contain 1 to 19 digits and hyphens.")
    private String phone;
}
