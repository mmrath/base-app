package com.mmrath.sapp.web.rest.data;

import com.mmrath.sapp.repository.data.TableDefRepository;
import com.mmrath.sapp.service.data.TableDefService;
import com.mmrath.sapp.web.errors.ExceptionTranslator;
import com.mmrath.sapp.web.rest.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class TableDefResourceTest extends AbstractWebIntegrationTest {

    @Autowired
    private TableDefRepository tableDefRepository;

    @Autowired
    private TableDefService tableDefService;

    private MockMvc mockMvc;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        TableDefResource tableDefResource = new TableDefResource(tableDefRepository, tableDefService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tableDefResource)
                .setControllerAdvice(new ExceptionTranslator()).build();
    }

    @Test
    public void testSearchByName() throws Exception {
        mockMvc.perform(get("/api/table/search/name").param("name", "T_ROLE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.name").value("T_ROLE"));
    }

    @Test
    public void testSearchByNameShouldThrow404IfNotFound() throws Exception {
        mockMvc.perform(get("/api/table/search/name").param("name", "T_ROL"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }
}
