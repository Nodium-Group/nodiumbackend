package nodium.group.backend.serviceTest;

import lombok.extern.slf4j.Slf4j;
import nodium.group.backend.request.FirmJobRequest;
import nodium.group.backend.request.FirmRegisterRequest;
import nodium.group.backend.request.JobRequest;
import nodium.group.backend.request.UpdateAddressRequest;
import nodium.group.backend.response.FirmJobResponse;
import nodium.group.backend.response.FirmRegisterResponse;
import nodium.group.backend.service.interfaces.FirmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class FirmServiceTest {
    @Autowired
    private FirmService firmService;
    @Test
    void testFirmsCanRegisterAndPostJobs(){
        FirmRegisterRequest register =new FirmRegisterRequest();
        register.setFirmName("nodium");
        register.setImageUrl("description description");
        register.setAddress( new UpdateAddressRequest("122", "test", "test", "test", ""));
        register.setImageUrl("https:www.home/kals/fileUrl");
        FirmRegisterResponse registerResponse = firmService.register(register);
        assertNotNull(registerResponse);
        log.info("RegisterResponse ===> {}",registerResponse);
        FirmJobRequest jobRequest = new FirmJobRequest();
        jobRequest.setJobRequest(new JobRequest(null,"location",
                "software engineer","BACKEND",
                           new BigDecimal("1000"),"ON-SITE"));
        jobRequest.setNumberOfPeopleNeeded(5);
        FirmJobResponse jobRespone = firmService.postJob(jobRequest);
        assertNotNull(jobRespone);
        log.info("JOBRESPONE --------->{}",jobRespone);

    }
}
