package ru.skillbox.auth_basic_example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.skillbox.auth_basic_example.entity.Role;
import ru.skillbox.auth_basic_example.entity.RoleType;
import ru.skillbox.auth_basic_example.entity.User;
import ru.skillbox.auth_basic_example.repository.UserRepository;
import ru.skillbox.auth_basic_example.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
public class AbstractTest {

	protected static PostgreSQLContainer postgresSqlContainer;

	static {
		DockerImageName postgres = DockerImageName.parse("postgres:12.3");
		postgresSqlContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
				.withReuse(true);

		postgresSqlContainer.start();
	}

	@DynamicPropertySource
	public static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresSqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresSqlContainer::getUsername);
		registry.add("spring.datasource.password", postgresSqlContainer::getPassword);

	}

	@Autowired
	protected UserService userService;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	public void beforeEach() {
		userService.createNewAccount(User.builder()
				.username("testUser")
				.password("12345")
				.build(), Role.from(RoleType.ROLE_USER));

		userService.createNewAccount(User.builder()
				.username("testAdmin")
				.password("11111")
				.build(), Role.from(RoleType.ROLE_ADMIN));
	}

	@AfterEach
	public void afterEach() {
		userRepository.deleteAll();
	}
}