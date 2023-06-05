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
    public Long id;
    @NotBlank
    public String name;
    @NotNull
    public ItemCategory category;
    @NotNull
    public LocalDateTime dateTimeUPD;
    @Min(0)
    public Integer amount;
    @Min(0)
    public Float price;
}
