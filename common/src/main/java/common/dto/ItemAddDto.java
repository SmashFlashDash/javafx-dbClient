package common.dto;

import common.ItemCategory;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ItemAddDto {
    private String name;
    private ItemCategory category;
    private LocalDateTime dateTimeUPD;
    private Float price;
    private Integer amount;
}
