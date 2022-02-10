package br.com.dashgo.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInPostRequestBody {

    @NotBlank(message = "O username é requerido!")
    private String username;

    @NotBlank(message = "O password é requerido!")
    private String password;

}
