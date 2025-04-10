package Offime.Offime.service.member;

import Offime.Offime.dto.request.member.MemberFileUploadDto;
import Offime.Offime.entity.member.Member;
import Offime.Offime.entity.member.MemberProfileFiles;
import Offime.Offime.exception.ResourceNotFoundException;
import Offime.Offime.repository.member.MemberRepository;
import Offime.Offime.repository.member.ProfileFilesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProfileFileService {

    private final MemberRepository memberRepository;
    private final ProfileFilesRepository profileFilesRepository;

    @Value("${project.member.folderPath}")
    private String FOLDER_PATH;
    private String SRC = "/images/member/";


    public List<MemberFileUploadDto> uploadFile(Long id, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Member not found"));

        List<MemberFileUploadDto> fileUploadDTOList = new ArrayList<>();

        for (MultipartFile file : files) {
            String originFileName = file.getOriginalFilename();
            if (originFileName == null || !originFileName.contains(".")) {
                throw new IllegalArgumentException("Invalid file type");
            }
            String randomId = UUID.randomUUID().toString();
            String storedFileName = randomId + originFileName.substring(originFileName.lastIndexOf("."));

            String filePath = FOLDER_PATH + File.separator + storedFileName;

            File f = new File(FOLDER_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(filePath));

            String fileType = file.getContentType();

            MemberProfileFiles memberProfileFiles = new MemberProfileFiles();
            memberProfileFiles.setMember(member);
            memberProfileFiles.setOriginFileName(originFileName);
            memberProfileFiles.setFilePath(SRC + storedFileName);
            memberProfileFiles.setFileType(fileType);

            MemberProfileFiles savedFile = profileFilesRepository.save(memberProfileFiles);

            fileUploadDTOList.add(MemberFileUploadDto.fromEntity(savedFile));
        }

        return fileUploadDTOList;

    }

    public void delete (Long id){
        MemberProfileFiles files = profileFilesRepository.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("MemberProfileFiles", "id", String.valueOf(id)));
        String filePath = FOLDER_PATH + File.separator + files.getFilePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        profileFilesRepository.delete(files);
    }

    public MemberProfileFiles getProfileFileById(Long id) {
        return profileFilesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MemberProfileFiles", "id", String.valueOf(id)));
    }
}