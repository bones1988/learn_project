package com.epam.esm.file.impl;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.file.FileProcessor;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.CertificateValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileProcessorImpl implements FileProcessor {
    private static final ObjectMapper mapper = new ObjectMapper();
    private GiftCertificateService giftCertificateService;
    private CertificateValidator validator;

    @Autowired
    public FileProcessorImpl(GiftCertificateService giftCertificateService, CertificateValidator validator) {
        this.giftCertificateService = giftCertificateService;
        this.validator = validator;
    }

    @Override
    public boolean processFile(Path path, String startPath) throws IOException {
        try {
            GiftCertificateDto[]   certificateDtos = mapper.readValue(new FileReader(path.toString()), GiftCertificateDto[].class);
            saveCertificates(certificateDtos);
        } catch (ValidatorException | MismatchedInputException e) {
            moveFile(path, startPath);
            return false;
        } catch (Exception ex) {
            File oldFile = new File(path.toString());
            String filename = path.getFileName().toString().replace("~", "");
            String newFolder = path.getParent().toString() + "\\" + filename;
            File newFile = new File(newFolder);
            boolean a = false;
            while (!a) {
                a = oldFile.renameTo(newFile);
            }
        } finally {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
        return true;
    }

    private void moveFile(Path path, String startPath) throws IOException {
        Path movePath = Paths.get(startPath + "\\error\\" + path.getFileName().toString().replaceAll("~", ""));
        if (Files.exists(movePath)) {
            int i = 0;
            while (Files.exists(movePath)) {
                movePath = Paths.get(startPath + "\\error\\" + i + "_" + movePath.getFileName());
                i++;
            }
        }
        Files.move(path, movePath, StandardCopyOption.ATOMIC_MOVE);
    }

    private void saveCertificates(GiftCertificateDto[] certificateDtos) {
        for (GiftCertificateDto giftCertificateDto : certificateDtos)
            validator.validateCertificateForAdd(giftCertificateDto);
        for (GiftCertificateDto giftCertificateDto : certificateDtos)
            giftCertificateService.save(giftCertificateDto);
    }
}
