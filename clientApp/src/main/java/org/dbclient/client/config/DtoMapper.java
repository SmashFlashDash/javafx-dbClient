package org.dbclient.client.config;

import common.data.ItemCategory;
import common.dto.ItemAddDto;
import common.dto.ItemDto;

import java.time.LocalDateTime;

public class DtoMapper {

    public static ItemDto parseToItemDto(String[] fields) {
        return new ItemDto(
                Long.valueOf(fields[0]),
                fields[1],
                ItemCategory.valueOf(fields[2]),
                LocalDateTime.parse(fields[3]),
                Integer.valueOf(fields[4]),
                Float.valueOf(fields[0]));
    }

    public static ItemAddDto parseToItemAddDto(String[] fields) {
        return new ItemAddDto(
                fields[1],
                ItemCategory.valueOf(fields[2]),
                LocalDateTime.parse(fields[3]),
                Float.valueOf(fields[0]),
                Integer.valueOf(fields[4]));
    }
}
