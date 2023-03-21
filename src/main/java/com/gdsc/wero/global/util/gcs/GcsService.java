package com.gdsc.wero.global.util.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;
    private final GcsInfoProperties gcsInfoProperties;

    public BlobInfo uploadFileToGCS(MultipartFile imgFile) throws IOException {

        String uploadFileName = UUID.randomUUID().toString();
        String contentType = imgFile.getContentType();


        log.info("============= file name : " + uploadFileName);
        log.info("============= content type : " + contentType);


        BlobInfo blobInfo =storage.create(
                BlobInfo.newBuilder(gcsInfoProperties.getBucketName(), uploadFileName)
                        .setContentType(contentType)
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                imgFile.getInputStream());


        return blobInfo;
    }
}
