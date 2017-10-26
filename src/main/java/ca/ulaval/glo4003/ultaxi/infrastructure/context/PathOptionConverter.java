package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import com.beust.jcommander.IStringConverter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathOptionConverter implements IStringConverter<Path> {

    @Override
    public Path convert(String path) {
        return Paths.get(path);
    }
}
