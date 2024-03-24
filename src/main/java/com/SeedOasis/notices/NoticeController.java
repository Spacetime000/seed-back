package com.SeedOasis.notices;

import com.SeedOasis.auth.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final JwtService jwtService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<?> notices() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"))) {
            List<NoticeListAdminDto> listAll = noticeService.generateNoticeListAdminDto();
            return ResponseEntity.ok(listAll);
        } else {
            List<NoticeListDto> listAll = noticeService.generateNoticeListDto();
            return ResponseEntity.ok(listAll);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> notice(@PathVariable("id") Long id) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities.stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"))) {
            //ADMIN
            return ResponseEntity.ok(noticeService.generateNotice(id));
        } else {
            //USER
            return ResponseEntity.ok(noticeService.generatePublicNotice(id));
        }
    }

    @PutMapping("*")
    public ResponseEntity<?> edit(@Valid NoticeEditDto noticeEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validatorResult = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                validatorResult.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validatorResult);
        }

        try {
            noticeService.editNotice(noticeEditDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("recent")
    public ResponseEntity<?> recent(@RequestParam(value = "size", defaultValue = "1") Integer size) {
        List<NoticeDto> list = noticeService.recentNotice(size);
        return ResponseEntity.ok(list);
    }

}
