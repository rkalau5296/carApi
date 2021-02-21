package pl.rafal.carapi.car.mail;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.rafal.carapi.car.config.AdminConfig;
import pl.rafal.carapi.car.model.Vehicle;
import static java.util.Optional.ofNullable;

@Aspect
@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;
    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "New film added to db";

    @After("execution( void pl.rafal.carapi.car.service.FilmService.addNewFilm(..))")
    public void send(){
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimMessage(new Mail(adminConfig.getAdminMail(), SUBJECT,
                    "The film has been added.")));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }


    private MimeMessagePreparator createMimMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildCardEmail(mail.getMessage()), true);
        };
    }

//    //@After("execution(public void pl.rafal.carapi.car.controller.VehicleApi.addVehicle(vehicle))")
//    public void sendMail(final Vehicle vehicle) {
//        ofNullable(vehicle).ifPresent(email -> send(new Mail(adminConfig.getAdminMail(), SUBJECT,
//                "The vehicle: "  + vehicle.getBrand()
//                                        + " "
//                                        + vehicle.getModel()
//                                        + "has been added.")));
//
//    }
}
