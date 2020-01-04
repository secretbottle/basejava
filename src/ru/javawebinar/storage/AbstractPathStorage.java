package ru.javawebinar.storage;

import java.nio.file.Path;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    /*
    private Path directory;

    protected AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir, " dir must not be null");
        if(!Files.isDirectory(directory)|| !Files.isWritable(directory)){
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(OutputStream os, Resume resume) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void updateElement(Path Path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(new PathOutputStream(Path)), resume);
        } catch (IOException e) {
            throw new StorageException("IOError at write operation: ", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveElement(Path Path, Resume resume) {
        try {
            Path.createNewPath();
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("IOError Couldn't create Path " + Path.getAbsolutePath(), Path.getName(), e);
        }
        updateElement(Path, resume);
    }

    @Override
    protected Resume getElement(Path Path) {
        try {
            return doRead(new BufferedInputStream(new PathInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("IOError at read operation: ", Path.getName(), e);
        }
    }

    @Override
    protected void deleteElement(Path Path) {
        if (!Path.delete())
            throw new StorageException("IOError at delete operation: ", Path.getName());
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected boolean isExist(Path Path) {
        return Path.exists();
    }

    @Override
    protected List<Resume> getAsList() {
        Path[] PathList = getPathList();
        List<Resume> resumeList = new ArrayList<>();

        for (Path Path : PathList) {
            resumeList.add(getElement(Path));
        }

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
        return getPathList().length;
    }

    private Path[] getPathList() {
        Path[] PathList = directory.listPaths();
        if (PathList == null)
            throw new StorageException("IOError while getting listPaths in directory: ", directory.getPath());
        return PathList;
    }
    */

}
