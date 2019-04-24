package com.ccr.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccr12312@163.com at 2019-4-24
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    CustomerRepository customerRepository() {
        CustomerProtos.Customer c1 = CustomerProtos.Customer.newBuilder()
                .setId(1)
                .setName("安徽辉隆集团股份有限公司")
                .setCode("KH201904240001")
                .setMobile("18055652654")
                .addEmail(CustomerProtos.Customer.EmailAddress.newBuilder()
                        .setEmail("785458458@qq.com")
                        .setType(CustomerProtos.Customer.EmailType.PRIVATE)
                        .build())
                .addEmail(CustomerProtos.Customer.EmailAddress.newBuilder()
                        .setEmail("ccr@nrj.com")
                        .setType(CustomerProtos.Customer.EmailType.PROFESSIONAL)
                        .build())
                .build();
        CustomerProtos.Customer c2 = CustomerProtos.Customer.newBuilder()
                .setId(2)
                .setName("安徽隆盛集团")
                .setCode("KH201904240002")
                .setMobile("17755652654")
                .addEmail(CustomerProtos.Customer.EmailAddress.newBuilder()
                        .setEmail("741852963@qq.com")
                        .setType(CustomerProtos.Customer.EmailType.PRIVATE)
                        .build())
                .addEmail(CustomerProtos.Customer.EmailAddress.newBuilder()
                        .setEmail("cjj@nrj.com")
                        .setType(CustomerProtos.Customer.EmailType.PROFESSIONAL)
                        .build())
                .build();
        Map<Integer, CustomerProtos.Customer> customerMap = new HashMap<>();
        customerMap.putIfAbsent(c1.getId(),c1);
        customerMap.put(c2.getId(),c2);
        return new CustomerRepository(customerMap);
    }
}
