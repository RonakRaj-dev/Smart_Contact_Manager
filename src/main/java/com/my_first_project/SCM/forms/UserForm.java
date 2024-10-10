package com.my_first_project.SCM.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Characters required")
    private String name;

    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 Characters required")
    private String password;

    @Size(min = 5, message = "Minimum 5 words")
    private String about;

    @Size(min = 10, max = 10, message = "Limit Exceed")
    private String phoneNumber;
}
