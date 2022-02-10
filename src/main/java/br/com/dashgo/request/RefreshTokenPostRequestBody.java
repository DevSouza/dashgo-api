package br.com.dashgo.request;

import javax.validation.constraints.NotBlank;

public class RefreshTokenPostRequestBody {
  @NotBlank
  private String refreshToken;

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
