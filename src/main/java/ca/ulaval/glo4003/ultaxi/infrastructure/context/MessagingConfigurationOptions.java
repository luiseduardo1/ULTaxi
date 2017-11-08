package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import com.beust.jcommander.SubParameter;

public class MessagingConfigurationOptions {

    @SubParameter(order = 0)
    public String filePath;

    @SubParameter(order = 1)
    public String fileType;

    public MessagingConfigurationOptions(String filePath, String filetype) {
        this.filePath = filePath;
        this.fileType = filetype;
    }
}
