import com.ccr.protocol.Application;
import com.ccr.protocol.CustomerProtos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author ccr12312@163.com at 2019-4-24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {
    private static final String CUSTOMER1_URL = "http://localhost:8080/customers/1";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testFindCustomer(){
        ResponseEntity<CustomerProtos.Customer> customer = restTemplate.getForEntity(CUSTOMER1_URL, CustomerProtos.Customer.class);

        System.out.println("received customer:" + customer.toString());
    }
}
