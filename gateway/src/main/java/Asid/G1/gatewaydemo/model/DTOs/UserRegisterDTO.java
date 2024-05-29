package Asid.G1.gatewaydemo.model.DTOs;

import Asid.G1.gatewaydemo.model.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match!"
)
public class UserRegisterDTO {

    @NotEmpty(message = "Enter your email.")
    @Email(message = "Invalid email.")
    private String email;

    private String password;

    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
