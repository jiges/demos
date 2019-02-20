package com.ccr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * 创建状态机配置类
 * @author ccr12312@163.com at 2019-2-20
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<StateConcern.States, StateConcern.Events> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configure(StateMachineStateConfigurer<StateConcern.States, StateConcern.Events> states)
            throws Exception {
        states
                .withStates()
                .initial(StateConcern.States.UNSATISFIED)
                .states(EnumSet.allOf(StateConcern.States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StateConcern.States, StateConcern.Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(StateConcern.States.UNSATISFIED).target(StateConcern.States.SATISFIED)
                .event(StateConcern.Events.CONDITION_1_MEETED);

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<StateConcern.States, StateConcern.Events> config)
            throws Exception {
        config
                .withConfiguration()
                .listener(listener());
    }

    @Bean
    public StateMachineListener<StateConcern.States, StateConcern.Events> listener() {
        return new StateMachineListenerAdapter<StateConcern.States, StateConcern.Events>() {

            @Override
            public void transition(Transition<StateConcern.States, StateConcern.Events> transition) {
                logger.info(transition.getSource().getId().name());
                logger.info(transition.getTarget().getId().name());
            }

        };
    }
}
