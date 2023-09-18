package cm.Bangoulap.user.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lasttName;
    private final String email;
    private final String password;
    private final String country;
    private final String gender;
    private final String phoneNumber;
    private final String residence;
    private final String title;
    private final int yearOfBirth;
}
