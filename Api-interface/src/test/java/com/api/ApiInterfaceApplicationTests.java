package com.api;

import com.api.client.ApiClient;
import com.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiInterfaceApplicationTests {

    @Autowired
    private ApiClient apiClient;

    @Test
    void contextLoads() {

    }

}
