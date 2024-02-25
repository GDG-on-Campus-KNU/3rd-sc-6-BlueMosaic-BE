package com.gdsc.knu.config;

import com.gdsc.knu.util.DummyDataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private DummyDataCreator dummyDataCreator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dummyDataCreator.createDummyData();
    }
}
