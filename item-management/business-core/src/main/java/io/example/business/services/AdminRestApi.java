package io.example.business.services;

import io.example.business.api.AdminActions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminRestApi {
    private final AdminActions adminActions;
}
