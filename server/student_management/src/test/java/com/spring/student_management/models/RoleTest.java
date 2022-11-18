package com.spring.student_management.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class RoleTest extends AbstractModelTest<Role> {

    private static final String TEST_ROLE = "Test role";
    private static final long ID = 1L;
    @BeforeEach
    public void setUp() {
        super.model = Role.builder()
                .id(ID)
                .roleName(TEST_ROLE)
                .build();
    }

    @Test
    @Override
    public void testNoArgConstructor(){
        this.model = new Role();
        assertNotNull(this.model);
    }

    @Test
    @Override
    public void testAllArgConstructor(){
        this.model = new Role(3L, "ADMIN_TEST");
        assertEquals(3L, this.model.getId());
        assertEquals("ADMIN_TEST", this.model.getRoleName());
    }

    @Test
    void setId(){
        assertEquals(1L, this.model.getId());
        this.model.setId(5L);
        assertEquals(5L, this.model.getId());
    }

    @Test
    void setRoleName(){
        assertEquals(TEST_ROLE, this.model.getRoleName());
        this.model.setRoleName("TEST");
        assertEquals("TEST", this.model.getRoleName());
    }
}