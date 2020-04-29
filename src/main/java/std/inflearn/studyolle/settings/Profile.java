package std.inflearn.studyolle.settings;

import lombok.Data;
import lombok.NoArgsConstructor;
import std.inflearn.studyolle.domain.Account;

@Data
@NoArgsConstructor
public class Profile {
    private String bio;
    private String url;
    private String occupation;
    private String location;
    private String profileImage;

    public Profile(Account account) {
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();

    }
}
