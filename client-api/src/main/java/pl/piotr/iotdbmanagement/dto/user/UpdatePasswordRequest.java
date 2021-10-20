package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePasswordRequest {

    @NotEmpty(message = "can not be empty")
    @Email(message = "must be a valid e-mail address")
    private String email;

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String currentPassword;

    @NotEmpty(message = "can not be empty")
    @Size(min = 8, message = "should have at least 8 characters")
    private String newPassword;

}