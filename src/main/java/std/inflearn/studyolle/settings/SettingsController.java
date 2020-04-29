package std.inflearn.studyolle.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import std.inflearn.studyolle.account.AccountService;
import std.inflearn.studyolle.account.CurrentUser;
import std.inflearn.studyolle.domain.Account;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    static final String SETTINGS_PROFILE_VIEW_NAME = "settings/profile";
    static final String SETTINGS_PROFILE_URL = "/settings/profile";

    private final AccountService accountService;

    @GetMapping(SETTINGS_PROFILE_URL)
    public String profileUpdateForm(@CurrentUser Account currentUser, Model model) {
        model.addAttribute(currentUser);
        model.addAttribute(new Profile(currentUser));
        return  SETTINGS_PROFILE_VIEW_NAME;
    }

    @PostMapping("/settings/profile")
    public String updateProfile(@CurrentUser Account currentUser, @Valid Profile profile, Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(currentUser);
            return SETTINGS_PROFILE_VIEW_NAME;
        }
        accountService.updateProfile(currentUser, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");
        return "redirect:"+ SETTINGS_PROFILE_URL;
    }
}
