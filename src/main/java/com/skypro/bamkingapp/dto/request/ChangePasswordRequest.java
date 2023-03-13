package com.skypro.bamkingapp.dto.request;


public record ChangePasswordRequest(String oldPassword, String newPassword,
                                    String newPasswordRepeat) {

}
