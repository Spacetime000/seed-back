package com.SeedOasis.voca;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocaService {

    private final VocaRepository vocaRepository;

    @Transactional
    public Long create(VocaWriteDto vocaWriteDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String contents = objectMapper.writeValueAsString(vocaWriteDto.getContents());

        VocaEntity vocaEntity = new VocaEntity();
        vocaEntity.setTitle(vocaWriteDto.getTitle());
        vocaEntity.setContents(contents);

        VocaEntity save = vocaRepository.save(vocaEntity);
        return save.getId();
    }

    public List<VocaDto> visibleVocaList() throws JsonProcessingException {
        List<VocaEntity> vocaEntityList = vocaRepository.findByVisibilityOrderByCreatedDateDesc(true);
        List<VocaDto> vocaDtoList = new ArrayList<>();
        System.out.println(vocaEntityList.toString());

        for (VocaEntity vocaEntity : vocaEntityList) {
            VocaDto vocaDto = new VocaDto();
            vocaDto.setId(vocaEntity.getId());
            vocaDto.setTitle(vocaEntity.getTitle());
            vocaDto.setCreateBy(vocaEntity.getCreateBy());
            vocaDto.setCreatedDate(vocaEntity.getCreatedDate());

            ObjectMapper objectMapper = new ObjectMapper();
            List<VocaContentDto> list = objectMapper.readValue(vocaEntity.getContents(), new TypeReference<List<VocaContentDto>>() {});
            vocaDto.setContents(list);
            vocaDtoList.add(vocaDto);
        }

        return vocaDtoList;
    }

    public VocaDto read(Long id, String name) throws JsonProcessingException, AccessDeniedException {
        VocaEntity vocaEntity = vocaRepository.findById(id).orElseThrow();

        if (!vocaEntity.getVisibility() && !vocaEntity.getCreateBy().equals(name)) {
            //비공개이면서 작성자가 아닐 경우 실행
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        VocaDto vocaDto = new VocaDto();
        vocaDto.setId(vocaEntity.getId());
        vocaDto.setTitle(vocaEntity.getTitle());
        vocaDto.setCreateBy(vocaEntity.getCreateBy());
        vocaDto.setCreatedDate(vocaEntity.getCreatedDate());

        ObjectMapper objectMapper = new ObjectMapper();
        List<VocaContentDto> list = objectMapper.readValue(vocaEntity.getContents(), new TypeReference<List<VocaContentDto>>() {
        });

        vocaDto.setContents(list);
        return vocaDto;
    }

    @Transactional
    public void update(VocaWriteDto vocaWriteDto) throws JsonProcessingException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        ObjectMapper objectMapper = new ObjectMapper();
        VocaEntity vocaEntity = vocaRepository.findByIdAndCreateBy(vocaWriteDto.getId(), name).orElseThrow();
        vocaEntity.setTitle(vocaWriteDto.getTitle());
        vocaEntity.setContents(objectMapper.writeValueAsString(vocaWriteDto.getContents()));
    }

    public List<VocaDto> getMyPostsList() throws JsonProcessingException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<VocaEntity> entityList = vocaRepository.findByCreateByOrderByIdDesc(name);
        List<VocaDto> vocaDtoList = new ArrayList<>();

        for (VocaEntity entity : entityList) {
            VocaDto vocaDto = VocaDto.of(entity);
            vocaDtoList.add(vocaDto);
        }

        return vocaDtoList;

    }

    @Transactional
    public void toggleVisibility(Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        VocaEntity vocaEntity = vocaRepository.findByIdAndCreateBy(id, name).orElseThrow();
        vocaEntity.setVisibility(!vocaEntity.getVisibility());
    }

}
