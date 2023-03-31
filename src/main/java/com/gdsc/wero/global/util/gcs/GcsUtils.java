package com.gdsc.wero.global.util.gcs;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GcsUtils {

    private final Storage storage;
    private final GcsInfoProperties gcsInfoProperties;

    public BlobInfo uploadFileToGCS(MultipartFile imgFile) throws IOException {

        String uploadFileName = UUID.randomUUID().toString();
        String contentType = imgFile.getContentType();

        log.info("============= file name : " + uploadFileName);
        log.info("============= content type : " + contentType);

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(gcsInfoProperties.getBucketName(), uploadFileName)
                        .setContentType(contentType)
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                imgFile.getInputStream());

        return blobInfo;
    }



    public boolean deleteFileFromGCS(String deleteFileName){

        log.info("============ file name : " + deleteFileName);

        BlobInfo blobInfo =storage.create(
                BlobInfo.newBuilder(gcsInfoProperties.getBucketName(), deleteFileName)
                        .build());
        BlobId blobId = blobInfo.getBlobId();

        boolean result = storage.delete(blobId);

        return result;
    }

}
