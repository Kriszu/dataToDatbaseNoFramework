package app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileLoadService {

    private static final Logger LOGGER = Logger.getLogger( FileLoadService.class.getName() );

    private String path;
    private Map<String, String> filesContent;

    public FileLoadService(String path){
        this.path = path;
        this.filesContent = loadFile(path);

    }

    private Map<String, String> loadFile(String folderPath){

        Map<String, String> filesContent = new HashMap<>();

        try(Stream<Path> pathStream = Files.walk(Paths.get(folderPath))){
            pathStream
                    .filter(Files::isRegularFile)
                    .forEach(o -> {
                        try {
                            if ((getExtension(o.toFile()).equals(".txt") ||
                                    getExtension(o.toFile()).equals(".xml"))) {
                                filesContent.put(getExtension(o.toFile()), new String(Files.readAllBytes(Paths.get(o.toUri()))));

                            }
                        } catch (IOException e) {
                            LOGGER.log(Level.SEVERE,"Problem with readAllBytes");
                        }
                    });
        } catch (IOException e){
           LOGGER.log(Level.SEVERE,"Problem with load file");
        }
        return filesContent;
    }

    private String getExtension(File file){


        String fileName = file.getName();
        int lastIndexOf = fileName.lastIndexOf(".");
        if(lastIndexOf == -1){
            LOGGER.log(Level.FINE, "File {0} has not extension", fileName);
            return "";
        }
        String extension = fileName.substring(lastIndexOf);
        LOGGER.log(Level.FINE, "File {0} has {1} extension",new Object[]{ fileName, extension});
        return extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getFilesContent() {
        return filesContent;
    }

    public void setFilesContent(Map<String, String> filesContent) {
        this.filesContent = filesContent;
    }
}
