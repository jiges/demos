package com.ccr;

/**
 * 定义状态机相关的枚举，包括状态、事件等
 * @author ccr12312@163.com at 2019-2-20
 */
public class StateConcern {
    public enum States {
        SATISFIED,  //满足条件的
        UNSATISFIED //不满足条件的
    }

    public enum Events {
        CONDITION_1_MEETED, //达到条件1
        CONDITION_2_MEETED, //达到条件2
        CONDITION_3_MEETED  //达到条件3
    }
}
