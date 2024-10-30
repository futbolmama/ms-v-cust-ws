package dev.futbolmama.msvcustws.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(@NotNull Long id, @NotEmpty String fName, String mName, @NotEmpty String lName,
                                    @Email String email, @NotEmpty @Size(min = 10) String phone) {
}
