package Offime.Offime.controller.member;

import Offime.Offime.dto.request.member.MemberFileUploadDto;
import Offime.Offime.entity.member.MemberProfileFiles;
import Offime.Offime.service.member.ProfileFileService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileFileController {
    private final ProfileFileService profileFileService;

    @PostMapping("/member/{id}/upload")
    public ResponseEntity<List<MemberFileUploadDto>> upload (
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        List<MemberFileUploadDto> saveFile = profileFileService.uploadFile(id, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveFile);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> delete(@RequestParam("id") Long reviewFilesId){
        profileFileService.delete(reviewFilesId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
