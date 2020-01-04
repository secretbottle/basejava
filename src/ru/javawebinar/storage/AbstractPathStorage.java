package ru.javawebinar.storage;

import ru.javawebinar.exception.StorageException;
import ru.javawebinar.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void updateElement(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("IOError at write operation: ", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveElement(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("IOError Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        updateElement(path, resume);
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IOError at read operation: ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("IOError at delete operation: ", path.getFileName().toString());
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getAsList() {
        List<Resume> resumeList = new ArrayList<>();
        getFileList().forEach(x -> resumeList.add(getElement(x)));
        return resumeList;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return getFileList().size();
    }

    private List<Path> getFileList() {
        try (Stream<Path> list = Files.list(directory)) {
            return list.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("IOError while getting list of Files in directory: ", directory.toString());
        }
    }
}
