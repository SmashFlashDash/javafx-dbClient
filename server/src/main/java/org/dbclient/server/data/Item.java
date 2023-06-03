package org.dbclient.server.data;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @Column(name = "datetime_upd")
    @UpdateTimestamp
    private LocalDateTime dateTimeUPD;

    private Float price;

    private Integer amount;

    public enum ItemCategory {
        NOTEBOOK, DESKTOP, SERVER
    }
}
