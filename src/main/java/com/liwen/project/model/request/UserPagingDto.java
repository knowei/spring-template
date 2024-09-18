package com.liwen.project.model.request;

import com.liwen.project.common.dto.PagingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPagingDto extends PagingDto {
    private String name;
    private String age;
}
