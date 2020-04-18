package org.superbiz.moviefun.blobstore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class FileStore implements BlobStore {

    private HashMap<String, Blob> fileStore = new HashMap<>();

    @Override
    public void put(Blob blob) throws IOException {
        fileStore.put(blob.name, blob);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        return Optional.ofNullable(fileStore.get(name));
    }

    @Override
    public void deleteAll() {
        fileStore.clear();
    }
}
