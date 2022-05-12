package AssetManagement.Asset.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Staff {
    @Id
    Long Id;
    String Name;
    String Mobile;
    String Email;
    String Password;
}
