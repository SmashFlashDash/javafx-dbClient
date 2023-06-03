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
public class ItemDto {
    @NotNull
    Long id;
    @NotBlank
    String name;
    @NotNull
    ItemCategory category;
    @NotNull
    LocalDateTime dateTimeUPD;
    @Min(0)
    Integer amount;
    @Min(0)
    private
    Float price;
}
