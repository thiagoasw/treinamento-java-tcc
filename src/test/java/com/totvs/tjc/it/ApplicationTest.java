package com.totvs.tjc.it;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Tag("integration")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationTest {

    @Test
    void contextLoads() {}
    
}
