package com.SeedOasis.notices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class NoticeAdminController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<?> notices() {
        List<NoticeListAdminDto> listAll = noticeService.generateNoticeListAdminDto();
        return ResponseEntity.ok(listAll);
    }

    @PostMapping("write")
    public ResponseEntity<?> writeNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> validatorResult = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                validatorResult.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validatorResult);
        }

        try {
            noticeService.saveNotice(noticeFormDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/toggle-visibility")
    public ResponseEntity<?> toggleVisibility(@PathVariable("id") Long id) {
        try {
            noticeService.toggleVisibility(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}

