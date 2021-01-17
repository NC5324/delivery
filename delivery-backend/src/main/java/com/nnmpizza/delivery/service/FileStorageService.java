package com.nnmpizza.delivery.service;

import com.nnmpizza.delivery.models.FileDB;
import com.nnmpizza.delivery.repository.FileDBRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final FileDBRepository fileDBRepository;

    public FileStorageService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    @Transactional
    public Stream<FileDB> getPageFiles(int currentPage, int perPage, String type) {
        Pageable pageRequest = PageRequest.of(currentPage-1, perPage);
        Page<FileDB> dbFiles = fileDBRepository.findPageFiles(pageRequest, type.toLowerCase());

        return dbFiles.getContent().stream();
    }
}