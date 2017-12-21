package com.wsd.test;

import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper1.UserRepositoty;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestMethod {



 @Autowired
 private UserRepositoty userRepositoty;

    @Test
    public void test() {
        SystemUser systemUser=userRepositoty.findInfo(135);
        System.out.print(systemUser);
    }
}
