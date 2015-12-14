package com.marfeel.test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeType;
import org.springframework.web.context.WebApplicationContext;

import com.marfeel.app.beans.Site;
import com.marfeel.app.config.AppConfig;
import com.marfeel.app.controllers.MarfeelController;
import com.marfeel.app.repository.SiteRepository;
import com.marfeel.config.WebAppContextTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppContextTestConfig.class, AppConfig.class})
@WebAppConfiguration
public class MarfeelTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }


	@Autowired
	private SiteRepository siteRepositoryMock;

	private final static List<Site> SITES = Arrays.asList(
			new Site("www.google.es", true),
			new Site("www.facebook.com", true),
			new Site("www.gmail.es", false),
			new Site("www.logitech.es", false)

			);
	
	
	@Test
	public void repositoryTest() throws Exception {

		//remove all
		siteRepositoryMock.deleteAll();
		Assert.assertTrue(siteRepositoryMock.findAll().isEmpty());

		siteRepositoryMock.save(SITES);

		List<Site> findAll = siteRepositoryMock.findAll();

		assertEquals(4, findAll.size());

		Site google = findAll.get(0);

		assertEquals("www.google.es", google.getUrl());
		assertEquals(true, google.isMarfeelizable());

	}
	
	
	@Test
	public void restTest() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/test"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(content().string(is("\"Server Online\"")));
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/check")
				.contentType(MediaType.APPLICATION_JSON_UTF8).
				content("[{\"url\":\"www.google.es\"}]")
				)
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}


}