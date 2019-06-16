package springframework.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@RunWith(SpringRunner.class)
@ContextConfiguration("/Springmvc.xml")
@WebAppConfiguration
public class ControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	// @Test
	public void test() throws Exception {
		this.mockMvc.perform(get("/accounts/5").param("age", 25 + "").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.name").value("ackerly5")).andExpect(jsonPath("$.age").value("25"))
				.andDo(MockMvcResultHandlers.print());

	}

	// @Test
	public void post() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/post/{name}", "ackerly").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.name").value("ackerly")).andDo(MockMvcResultHandlers.print());

	}

	@Before
	public void setUp() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(new CharacterEncodingFilter()).build();
		// 也可以只传递一个controller,但是推荐第一种
		// MockMvcBuilders.standaloneSetup(new TestController()).build();
	}

	// @Test
	public void file() throws Exception {
		this.mockMvc
				.perform(multipart("/post/file").file("file", "abc".getBytes("UTF-8"))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.size").value("abc".getBytes("UTF-8").length + ""))
				.andExpect(jsonPath("$.file").value("file")).andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void validate() throws Exception {
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/post/validate").param("age", 9 + "").param("name", "小明")
						.accept("text/html"))
				// .andExpect(status().isOk())
				// .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				// .andExpect(model().attributeHasErrors("error"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

	}
}
