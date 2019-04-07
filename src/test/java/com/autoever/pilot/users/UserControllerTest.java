package com.autoever.pilot.users;

import com.autoever.pilot.common.BaseControllerTest;
import com.autoever.pilot.mapper.UserMapper;
import com.autoever.pilot.model.User;
import com.autoever.pilot.users.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.junit.Assert.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class UserControllerTest extends BaseControllerTest {

    private MockHttpSession mockHttpSession;
    @Autowired UserService userService;

    @Before
    public void initMockMvc() throws Exception {

        this.mockHttpSession = (MockHttpSession)mockMvc.perform(post("/api/login")
                .param("username", "kwonssy02")
                .param("password", "1234"))
            .andExpect(status().isOk())
            .andReturn().getRequest().getSession();
    }

    @Test
    public void 로그인_성공() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "kwonssy02")
                .param("password", "1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login",
//                        requestHeaders(
//                                headerWithName(HttpHeaders.ACCEPT).description("application/json;charset=UTF-8"),
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("application/json;charset=UTF-8")
//                        ),
                        requestParameters(
                            parameterWithName("username").description("로그인 아이디"),
                            parameterWithName("password").description("로그인 패스워드")
                        )
//                        requestFields(
//                                fieldWithPath("username").description("로그인 아이디"),
//                                fieldWithPath("password").description("로그인 패스워드")
//                        )
//                        responseHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("application/json;charset=UTF-8")
//                        ),
//                        responseFields(
//
//                        )
                ))
        ;
    }

    @Test
    public void 로그인_실패_BadCredential() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("username", "kwonssy02")
                .param("password", "221424"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void 전체사용자조회_성공() throws Exception {
        mockMvc.perform(get("/api/users")
                .session(this.mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void 사용자조회_성공() throws Exception {
        mockMvc.perform(get("/api/users/kwonssy02")
                .session(this.mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void 사용자조회_실패_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/invalidUser")
                .session(this.mockHttpSession))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void 사용자생성_성공() throws Exception {
        User newUser = User.builder()
                .username("testuser")
                .name("테스트유저")
                .password("test")
                .email("test@test.com")
                .build()
        ;

        mockMvc.perform(post("/api/users")
                .session(this.mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void 사용자생성_실패() throws Exception {
        User newUser = User.builder()
                .username("kwonssy02")
                .name("테스트유저")
                .password("test")
                .email("test@test.com")
                .build()
                ;

        mockMvc.perform(post("/api/users")
                .session(this.mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void 사용자삭제_성공() throws Exception {
        mockMvc.perform(delete("/api/users/dbaldud333")
                .session(this.mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void 사용자삭제_실패() throws Exception {
        mockMvc.perform(delete("/api/users/failuser")
                .session(this.mockHttpSession))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
}