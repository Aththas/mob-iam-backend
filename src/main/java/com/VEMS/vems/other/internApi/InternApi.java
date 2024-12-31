package com.VEMS.vems.other.internApi;

import com.VEMS.vems.dto.responseDto.InternDetailsDto;
import com.VEMS.vems.other.exception.NoAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class InternApi {

    private final WebClient webClient;

    public InternDetailsDto getInternDetailsByUsername(String username) {
        String url = "http://localhost:8090/vems_backend/api/v1/dummyInternDetails";

        Map<String, String> requestBody = Map.of("username", username);

        Map<String, Object> response = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) {
            throw new NoAccess("No response from API");
        }

        Boolean success = (Boolean) response.get("success");

        if (Boolean.FALSE.equals(success)) {
            throw new NoAccess("Access Denied");
        }

        Map<String, Object> data = (Map<String, Object>) response.get("data");

        if (data == null) {
            throw new NoAccess("No data found for the given username");
        }

        InternDetailsDto internDetailsDto = new InternDetailsDto();
        internDetailsDto.setFirstName((String) data.get("firstname"));
        internDetailsDto.setLastName((String) data.get("lastname"));
        internDetailsDto.setJob((String) data.get("job"));
        internDetailsDto.setDepartment((String) data.get("department"));
        internDetailsDto.setManager((String) data.get("manager"));
        internDetailsDto.setIntern((String) data.get("intern"));
        internDetailsDto.setVehicleNo(null);
        internDetailsDto.setInTime(null);
        internDetailsDto.setOutTime(null);
        internDetailsDto.setPassNo(null);

        return internDetailsDto;
    }

    public void verifyAPI(String username) {
        String url = "http://localhost:8090/vems_backend/api/v1/dummyInternDetails";

        Map<String, String> requestBody = Map.of("username", username);

        Map<String, Object> response = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) {
            throw new NoAccess("No response from API");
        }

        Boolean success = (Boolean) response.get("success");

        if (Boolean.FALSE.equals(success)) {
            throw new NoAccess("Access Denied");
        }
    }
}
