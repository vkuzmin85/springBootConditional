package ru.netology.springbootconditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    public static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }
    @Test
    void devContextLoads() {
        ResponseEntity<String> devEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", devEntity.getBody());
        System.out.println(devEntity.getBody());
    }

    @Test
    void prodContextLoads() {
        ResponseEntity<String> prodEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is prod", prodEntity.getBody());
        System.out.println(prodEntity.getBody());
    }

}
