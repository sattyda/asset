package AssetManagement.Asset.services;

import AssetManagement.Asset.entity.Staff;
import AssetManagement.Asset.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class WebService {
    @Autowired
    StaffRepo staffRepo;

    public void saveStaff(Staff staff) {
        staffRepo.save(staff);
    }

    public Staff verifyStaff(String email, String password) {

        List<Staff> sStaff = staffRepo.findByEmail(email);


        List<Staff> staff = staffRepo.myquery(email);


        return sStaff.get(0);
    }
}
