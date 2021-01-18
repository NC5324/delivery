package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.models.Role;
import com.nnmpizza.delivery.payload.beans.RoleBean;
import com.nnmpizza.delivery.payload.request.MemberRequest;
import com.nnmpizza.delivery.payload.response.MessageResponse;
import com.nnmpizza.delivery.repository.MemberRepository;
import com.nnmpizza.delivery.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/member")
public class MemberController {

    final MemberRepository memberRepository;
    final RoleRepository roleRepository;


    public MemberController(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/all")
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    public ResponseEntity<?> saveMember (@Valid @RequestBody MemberRequest memberRequest) {
        boolean isNew = memberRequest.getId() == null;

        Member user = new Member(memberRequest);

        Set<RoleBean> jsonRoles = memberRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (memberRequest.getRoles() == null || jsonRoles.size() == 0) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            for(var jsonRole : jsonRoles) {
                var role = roleRepository.findByName(jsonRole.getName())
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(role);
            }
        }

        user.setRoles(roles);
        memberRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(isNew ? "Профилът беше създаден успешно!" : "Профилът беше редактиран успешно!"));
    }

    @GetMapping("/roles/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById (@RequestParam Long id){
        memberRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

}
