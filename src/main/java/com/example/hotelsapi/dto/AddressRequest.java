package com.example.hotelsapi.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @Min(value = 1, message = "House number must be at least 1")
    private int houseNumber;
    @NotBlank(message = "Street cannot be blank")
    @Size(min = 1, max = 70, message = "Street must be between 1 and 70 characters")
    private String street;
    @NotBlank(message = "City cannot be blank")
    @Size(min = 1, max = 70, message = "City must be between 1 and 70 characters")
    private String city;
    @NotBlank(message = "County cannot be blank")
    @Size(min = 1, max = 70, message = "County must be between 1 and 70 characters")
    private String county;
    @NotBlank(message = "Post code cannot be blank")
    @Size(min = 1, max = 70, message = "Post code must be between 2 and 70 characters")
    private String postCode;
}
