package std.inflearn.studyolle.settings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import std.inflearn.studyolle.WithAccount;
import std.inflearn.studyolle.account.AccountRepository;
import std.inflearn.studyolle.account.AccountService;
import std.inflearn.studyolle.domain.Account;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    void afterEach(){
        accountRepository.deleteAll();
    }

    @WithAccount("tklee")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void updateProfile() throws Exception {
        String bio = "짧은 소개를 수정하는 경우";
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                .param("bio", bio)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(flash().attributeExists("message"));
        Account tklee = accountRepository.findByNickname("tklee");
        assertEquals(tklee.getBio(),bio);
    }

    @WithAccount("tklee")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void updateProfile_error() throws Exception {
        String bio = "짧은 소개를 수정하는 경우 짧은 소개를 수정하는 경우 짧은 소개를 수정하는 경우 짧은 소개를 수정하는 경우 짧은 소개를 수정하는 경우";
        mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                .param("bio", bio)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PROFILE_VIEW_NAME))
                .andExpect(flash().attributeExists("account"));
        Account tklee = accountRepository.findByNickname("tklee");
        assertEquals(tklee.getBio(),bio);
    }
}