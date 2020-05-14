package com.github.rich.base.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.rich.common.core.datatype.CustomLocalDateTimeDeserializer;
import com.github.rich.common.core.datatype.CustomLocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailDTO implements Serializable {
    private static final long serialVersionUID = 6633834274600102991L;

    private String id;

    private String userName;

    private Integer userSex;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userBorn;

    private String userAvatar;

    private String email;

    private String userAddress;

    private String mobileTel;

    private String userTel;

    private String userIdenType;

    private String userIden;

    private String regionId;
}
