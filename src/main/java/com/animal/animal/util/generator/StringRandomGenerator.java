package com.animal.animal.util.generator;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StringRandomGenerator {
    public String publicIdGenerator(){
        return UUID.randomUUID().toString();
    }
}
