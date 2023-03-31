package com.gdsc.wero.gcsService;

import com.gdsc.wero.global.util.gcs.GcsInfoProperties;
import com.gdsc.wero.global.util.gcs.GcsUtils;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GcsServiceTest {

    @Autowired
    GcsUtils gcsService;

    @Autowired
    Storage storage;

    @Autowired
    GcsInfoProperties gcsInfoProperties;

    @Test
    public void deleteFileTest(){
        // given
        String fileName = "1c4ba995-d2e6-46af-a975-4e74f3982df0";

        BlobInfo blobInfo =storage.create(
                BlobInfo.newBuilder(gcsInfoProperties.getBucketName(), fileName)
                        .build());
        BlobId blobId = blobInfo.getBlobId();

        // when
        boolean result = storage.delete(blobId);

        // then
        Assertions.assertThat(result).isTrue();

    }

}
