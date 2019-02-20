package com.ccr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * 启动类
 * @author ccr12312@163.com at 2019-2-20
 */
@SpringBootApplication
public class StateMachineBootstrap implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(StateMachineBootstrap.class, args);
    }

    @Autowired
    private StateMachine<StateConcern.States, StateConcern.Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(StateConcern.Events.CONDITION_1_MEETED);
    }

}
