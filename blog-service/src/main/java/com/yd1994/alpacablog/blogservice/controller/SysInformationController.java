package com.yd1994.alpacablog.blogservice.controller;

import com.yd1994.alpacablog.blogservice.dto.SysInformation;
import com.yd1994.alpacablog.blogservice.service.ISysInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p></p>
 *
 * @author yd
 */
@RestController
@RequestMapping("/system/information")
public class SysInformationController {

    @Autowired
    private ISysInformationService sysInformationService;

    @GetMapping("/{name}")
    public ResponseEntity getByKey(@PathVariable("name") String name) throws Exception {
        return ResponseEntity.ok(this.sysInformationService.get(name));
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PutMapping("/{name}")
    public ResponseEntity update(@PathVariable("name") String name, SysInformation sysInformation, Principal principal) throws Exception {
        sysInformation.setName(name);
        this.sysInformationService.update(sysInformation, principal.getName());
        return ResponseEntity.ok().build();
    }

}
