package cm.Bangoulap.user.appuser;

import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.user.registration.token.ConfirmationToken;
import cm.Bangoulap.user.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        System.out.println("Email reçu: " + phoneNumber);
        AppUser appUser = appUserRepository.findByEmail(phoneNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, phoneNumber)));

        System.out.println(appUser);

        return appUser;
    }

    public ResponseEntity signUpUser(AppUser appUser) {
        ResponseEntity responseEntity = new ResponseEntity();
        boolean userExists = appUserRepository
                .findByPhoneNumber(appUser.getPhoneNumber())
                .isPresent();

        if(userExists) {

            responseEntity.setCode(1000);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Ce numéro de téléphone existe déjà dans le système. Veuillez choisir un autre numéro.");
            responseEntity.setValue(null);
            log.info("ERROR: Ce numéro de téléphone existe déjà dans le système. Veuillez choisir un autre numéro.");

            return responseEntity;
        }

        userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if(userExists) {
            // throw new IllegalStateException("Email already taken");
            responseEntity.setCode(1000);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Cet email existe déjà dans le système. Veuillez choisir un autre.");
            responseEntity.setValue(null);
            log.info("ERROR: Cet email existe déjà dans le système. Veuillez choisir un autre.");

            return responseEntity;
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        ;
        // Saving and Setting last user who update this.
        appUser.setUpdateBy(
                appUserRepository.save(appUser)
        );
        appUserRepository.save(appUser);
        String token;
        if(appUser.getEmail() != null) {
            token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(60),
                    appUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
        }else {
            token = generateOTP();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(60),
                    appUser
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
        }



        // TODO: SEND EMAIL
        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");
        responseEntity.setMessage(token);
        responseEntity.setValue(null);

        return responseEntity;
    }

    public void enableAppUser(String email) {
        AppUser appUser = appUserRepository.findByEmail(email).get();
        appUser.setEnabled(true);
        appUser.setUpdateBy(appUserRepository.findByEmail("no-reply@yahoo.fr").get());
        appUserRepository.save(appUser);
    }

    private String generateOTP() {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 4; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
