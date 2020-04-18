package org.superbiz.moviefun.blobstore;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class S3Store implements BlobStore {
    private AmazonS3Client s3Client;
    private String photoStorageBucket;

    public S3Store(AmazonS3Client s3Client, String photoStorageBucket) {
        this.s3Client = s3Client;
        this.photoStorageBucket = photoStorageBucket;
    }

    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(blob.contentType);
        PutObjectRequest request = new PutObjectRequest(photoStorageBucket, blob.name,  blob.inputStream, metadata);
        s3Client.putObject(request);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        S3Object s3ClientObject = s3Client.getObject(photoStorageBucket, name);
        Blob blob = new Blob(
                s3ClientObject.getKey(),
                s3ClientObject.getObjectContent(),
                s3ClientObject.getObjectMetadata().getContentType()
        );
        return Optional.of(blob);
    }

    @Override
    public void deleteAll() {
        s3Client.deleteBucket(photoStorageBucket);
    }
}
