package com.SeedOasis.voca;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("voca")
@RequiredArgsConstructor
public class VocaController {

    private final VocaService vocaService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VocaWriteDto vocaWriteDto, BindingResult bindingResult) {

        Map<String, String> map = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(err -> {
                map.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(map);
        }

        try {
            Long id = vocaService.create(vocaWriteDto);
            map.put("id", id.toString());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            List<VocaDto> list = vocaService.visibleVocaList();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("my-page")
    public ResponseEntity<?> myPage() {
        try {
            List<VocaDto> list = vocaService.getMyPostsList();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            VocaDto vocaDto = vocaService.read(id, name);
            return ResponseEntity.ok(vocaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody VocaWriteDto vocaWriteDto, BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(err -> {
                map.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(map);
        }

        try {
            vocaService.update(vocaWriteDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{id}/toggle-visibility")
    public ResponseEntity<?> toggleVisibility(@PathVariable("id") Long id) {
        try {
            vocaService.toggleVisibility(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
