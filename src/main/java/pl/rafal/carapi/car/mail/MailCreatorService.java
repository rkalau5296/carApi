package pl.rafal.carapi.car.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.rafal.carapi.car.config.AdminConfig;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String buildCardEmail(String message) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig);
        context.setVariable("company_details", "Adress: 1 Test street 1 12345 TestCity.");
        context.setVariable("preview_message", "This is some preview message.");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        return templateEngine.process("mail/index", context);
    }
}
