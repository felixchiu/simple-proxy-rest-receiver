package com.platformnexus.enterprise.notification;

import ai.grakn.redismock.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;


/**
 * Created by Felix Chiu on 2/23/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SimpleProxyRestReceiverTest {

    private static RedisServer server = null;

    @Before
    public void before() throws IOException {
        server = RedisServer.newRedisServer(3777);
        server.start();
    }

    @Test
    public void contextLoads() {

    }

    @After
    public void after() {
        server.stop();
        server = null;
    }

    public File getFile(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(path).getFile());
    }
}