package com.associacao.api.v1.SuperClasses.dto;
import java.time.LocalDateTime;

public interface BaseDTO {
    void setCreatedBy(String createdBy);
    void setCreatedDate(LocalDateTime createdDate);
    void setModifiedBy(String modifiedBy);
    void setModifiedDate(LocalDateTime modifiedDate);
}

