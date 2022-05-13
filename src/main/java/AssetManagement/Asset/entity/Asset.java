package AssetManagement.Asset.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank
    String name;

    @Temporal(TemporalType.TIMESTAMP)
    Date manufacturingId;

    @Temporal(TemporalType.TIMESTAMP)
    Date expiry_date;

    @OneToMany(fetch = FetchType.LAZY)
    List<Staff> assigned_to;

}


