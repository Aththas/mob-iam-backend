package com.VEMS.vems.other.internApi;

import com.VEMS.vems.dto.responseDto.InternDetailsDto;
import com.VEMS.vems.other.exception.NoAccess;
import org.springframework.stereotype.Component;

@Component
public class InternApi {
    public InternDetailsDto getInternDetailsByUsername(String username){
        verifyAPI(username);

        InternDetailsDto internDetailsDto = new InternDetailsDto();
        internDetailsDto.setFirstName("Aththas");
        internDetailsDto.setLastName("Rizwan");
        internDetailsDto.setJob("Information Security");
        internDetailsDto.setDepartment("Information Systems");
        internDetailsDto.setManager("Musthalie");
        internDetailsDto.setIntern(username);
        internDetailsDto.setVehicleNo(null);
        internDetailsDto.setInTime(null);
        internDetailsDto.setOutTime(null);
        internDetailsDto.setPassNo(null);

        return internDetailsDto;
    }

    //            username goes through api verification, if failed throw no access exception
    public void verifyAPI(String username){
        if(!username.equalsIgnoreCase("aththas")){
            throw new NoAccess("Access Denied");
        }
    }
}
