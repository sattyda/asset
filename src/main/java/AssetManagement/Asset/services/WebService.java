package AssetManagement.Asset.services;

import AssetManagement.Asset.entity.Staff;
import AssetManagement.Asset.repository.StaffRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintDeclarationException;
import java.util.List;
import java.util.Stack;


@Service
public class WebService {

    final Logger logger = LoggerFactory.getLogger(WebService.class);

    public int myValue = 0;

    @Autowired
    StaffRepo staffRepo;

    public void saveStaff(Staff staff) {

        try {
            staffRepo.save(staff);
        } catch(Exception cdx){
            logger.info(cdx.getMessage());
        }
    }

    public Staff verifyStaff(String email, String password) {
        List<Staff> sStaff = staffRepo.findByEmail(email);
        List<Staff> staff = staffRepo.myquery(email);
        return sStaff.get(0);
    }

    public List<Staff> getAllStaff() {
        return staffRepo.findAll();
    }
}
