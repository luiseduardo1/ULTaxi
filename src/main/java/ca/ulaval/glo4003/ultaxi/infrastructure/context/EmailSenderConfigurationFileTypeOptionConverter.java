package ca.ulaval.glo4003.ultaxi.infrastructure.context;

import ca.ulaval.glo4003.ultaxi.infrastructure.messaging.EmailSenderConfigurationFileType;
import ca.ulaval.glo4003.ultaxi.utils.StringUtil;
import com.beust.jcommander.IStringConverter;

public class EmailSenderConfigurationFileTypeOptionConverter implements
    IStringConverter<EmailSenderConfigurationFileType> {


    @Override
    public EmailSenderConfigurationFileType convert(String filetype) {
        return EmailSenderConfigurationFileType.valueOf(StringUtil.capitalize(filetype));
    }
}
