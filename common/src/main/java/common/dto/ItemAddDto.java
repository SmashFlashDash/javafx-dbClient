package common.dto;

import com.sun.istack.NotNull;
import common.data.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAddDto {
    @NotBlank
    private String name;
    @NotNull
    private ItemCategory category;
    @NotNull
    private LocalDateTime dateTimeUPD;
    @Min(0)
    private Float price;
    @Min(0)
    private Integer amount;
}
