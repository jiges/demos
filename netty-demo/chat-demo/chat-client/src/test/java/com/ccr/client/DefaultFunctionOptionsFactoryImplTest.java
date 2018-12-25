package com.ccr.client;

import org.junit.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author ccr12312@163.com at 2018-12-25
 */
public class DefaultFunctionOptionsFactoryImplTest {


    @Test
    public void testYaml() {
        Constructor constructor = new Constructor(Map.class);
        TypeDescription customTypeDescription = new TypeDescription(Map.class);
        customTypeDescription.putListPropertyType("options", FunctionOption.class);
        constructor.addTypeDescription(customTypeDescription);
        InputStream in = DefaultFunctionOptionsFactoryImpl.class.getResourceAsStream("/config.yml");
        Yaml yaml = new Yaml(constructor);
        for (Object o : yaml.loadAll(in)) {
            System.out.println(o);
        }
    }

}