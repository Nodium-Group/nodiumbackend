package nodium.group.backend.serviceTest;

import nodium.group.backend.request.FirmJobRequest;
import nodium.group.backend.request.FirmRegisterRequest;
import nodium.group.backend.request.JobRequest;
import nodium.group.backend.response.FirmJobResponse;
import nodium.group.backend.response.FirmRegisterResponse;
import nodium.group.backend.service.interfaces.FirmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FirmServiceTest {
    @Autowired
    private FirmService firmService;
    @Test
    void testFirmCanPostJobs(){
        FirmRegisterRequest register = new FirmRegisterRequest("nodium","description description","address",
                new File("C:\\Users\\DELL\\OneDrive\\Pictures\\Screenshots\\" +
                        "Screenshot 2024-07-04 142337.png"));
        FirmRegisterResponse response = firmService.register(register);
        assertNotNull(response);
        FirmJobRequest jobRequest= new FirmJobRequest(response.getId(),
                new JobRequest(null,"Lagos","6 Years Experience","Backend Engineer",new BigDecimal("2000"),"REMOTE"));
        FirmJobResponse jobResponse = firmService.postJob(jobRequest);
        assertNotNull(jobResponse);
    }
}
