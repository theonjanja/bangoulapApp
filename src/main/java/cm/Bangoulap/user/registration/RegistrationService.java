package cm.Bangoulap.user.registration;

import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.user.appuser.AppUser;
import cm.Bangoulap.user.appuser.AppUserRole;
import cm.Bangoulap.user.appuser.AppUserService;
import cm.Bangoulap.user.email.EmailSender;
import cm.Bangoulap.user.registration.token.ConfirmationToken;
import cm.Bangoulap.user.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final ResponseEntity responseEntity = new ResponseEntity();

    public ResponseEntity register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail) {
            // throw new IllegalStateException("Email not valid !");
            responseEntity.setCode(1001);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Email not valid !");
            responseEntity.setValue(null);
            return responseEntity;
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLasttName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.MEMBER,
                        request.getCountry(),
                        request.getGender(),
                        request.getPhoneNumber(),
                        request.getResidence(),
                        request.getTitle(),
                        request.getYearOfBirth()
                )
        ).getMessage();
        if(request.getEmail() != null) {
            String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
            emailSender.send(
                    request.getEmail(),
                    buildEmail(request.getFirstName(), link));
            responseEntity.setMessage("Votre compte a été créé avec succès. Le lien de confirmation de votre compte vous a été envoyé par mail. Veuillez consulter votre boîte mail.");
        }else {
            // TODO: Send SMS.
            responseEntity.setMessage("Votre compte a été créé avec succès. Le code de confirmation de votre compte vous a été envoyé par SMS. Veuillez renseigner ci-dessous.");

            // TODO: Traiter le cas de l'échec d'envoi de l'OTP.
        }

        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");

        responseEntity.setValue(null);


        return responseEntity;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null) {
            return "email already confirmed";
            // throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail()
        );
        return "Confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div> " +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "    <!-- LOGO -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#FFA73B\" align=\"center\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                        <h1 style=\"font-size: 20px; font-weight: 400; margin: 2;\">Bienvenu(e),&nbsp;<em th:text=\"${username}\"></em> " + name + " !</h1> <img\n" +
                "                            src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">Vous être membre de la communauté Bangoulap, et avez sollicité une incription sur la plateforme de gestion du village Bangoulap." +
                "                                Si c'est le cas, veuillez juste cliquer sur le bouton ci-dessous pour confirmer votre inscription. Sinon, ignorez jus ce message." +
                "                        </p>\n" +
                                         "<p style=\"margin: 0;\"\n>Ce lien de confirmation s'expirera au bout de 24h.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                            <tr>\n" +
                "                                <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                                    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                        <tr>\n" +
                "                                            <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#FFA73B\">\n" +
                "                                                <a href=\"" + link + "\"\n" +
                "                                                   target=\"_blank\"\n" +
                "                                                   style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #FFA73B; display: inline-block;\">\n" +
                "                                                    Confirmer mon inscription\n" +
                "                                                </a>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr> <!-- COPY -->\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">If that doesn't work, copy and paste the following link in your browser:</p>\n" +
                "                    </td>\n" +
                "                </tr> <!-- COPY -->\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">\n" +
                "                            <a th:href=\"${confirmationUrl}\" target=\"_blank\" style=\"color: #FFA73B;\">\n" +
                "                        <p th:text = \"${confirmationUrl}\"></p>\n" +
                "                        </a>\n" +
                "                        </p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">If you have any questions, just reply to this email—we're always happy to help out.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                        <p style=\"margin: 0;\">Brothers code</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>" +
                "</dive>";
    }
}
