package com.SeedOasis.voca;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VocaDto {

    private Long id;
    private String title;
    private List<VocaContentDto> contents;
    private String createBy;
    private LocalDateTime createdDate;
    private Boolean visibility;

    public static VocaDto of(VocaEntity vocaEntity) throws JsonProcessingException {
        VocaDto vocaDto = new VocaDto();
        vocaDto.setId(vocaEntity.getId());
        vocaDto.setTitle(vocaEntity.getTitle());
        vocaDto.setVisibility(vocaEntity.getVisibility());
        vocaDto.setCreateBy(vocaEntity.getCreateBy());
        vocaDto.setCreatedDate(vocaEntity.getCreatedDate());

        ObjectMapper objectMapper = new ObjectMapper();
        List<VocaContentDto> vocaContentDtoList = objectMapper.readValue(vocaEntity.getContents(), new TypeReference<List<VocaContentDto>>() {
        });

        vocaDto.setContents(vocaContentDtoList);
        return vocaDto;
    }
}
