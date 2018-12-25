package com.ccr.client;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 配置文件解析
 * @author ccr12312@163.com at 2018-12-25
 */
public class DefaultFunctionOptionsFactoryImpl implements FunctionOptionsFactory {

    /**
     * 属性集合封装
     */
    private List<FunctionOption> functionOptions;

    public DefaultFunctionOptionsFactoryImpl() {
        init();
    }

    private void init() {
        InputStream in = this.getClass().getResourceAsStream("config.yml");
        Yaml yaml = new Yaml();
        Map ret = yaml.loadAs(in,Map.class);
        if(ret != null && ret.get("options") != null) {

        } else {
            throw new RuntimeException("illegal config file...");
        }
    }

    public void printFunctionOptions() {

    }
}
