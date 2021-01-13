package com.nnmpizza.delivery.controllers;

import com.nnmpizza.delivery.models.Member;
import com.nnmpizza.delivery.payload.request.MemberRequest;
import com.nnmpizza.delivery.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/member")
public class MemberController {

    final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @GetMapping("/all")
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveMember(@RequestBody MemberRequest memberRequest) {
        var member = new Member(memberRequest);
        memberRepository.save(member);

        return ResponseEntity.ok(String.format("member with ID: %d saved successfully", memberRequest.getId()));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById (@RequestParam Long id){
        memberRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

}
