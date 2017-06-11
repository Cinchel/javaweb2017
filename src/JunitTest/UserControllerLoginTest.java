package JunitTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:web/WEB-INF/applicationContext.xml")
@WebAppConfiguration("web") // web项目的根目录，默认为 file:src/main/webapp
public class UserControllerLoginTest {
	@Autowired
	private UserService userService;
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private MockHttpSession session;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUpSession() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RequestBuilder builder = MockMvcRequestBuilders.post("/loginPost").param("userName", "root").param("password", "e10adc3949ba59abbe56e057f20f883e");
		// 执行
		ResultActions resultActions = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
		// MockMvcResultHandlers提供结果处理功能
		// MockMvcResultMatchers提供断言功能
		// .andExpect(MockMvcResultMatchers.status().isOk()
		MvcResult result = resultActions.andReturn();
		session = (MockHttpSession) result.getRequest().getSession();
	}

	@Test
    public void addUser() throws Exception {
//      {"message":"添加成功","status":1}  {"message":"添加失败，用户已存在！","status":0}
        RequestBuilder builder = MockMvcRequestBuilders
                .post("/post/userAdminToggleRole")
                .param("userId", "2")
//                .param("title","教授")
//                .param("introduction","我就是张猛治，哈哈")
//                .param("role","teacher")
//                .param("phone","15545016598")
                .session(session);
        ResultActions resultActions = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
        MvcResult result = resultActions.andReturn();
        session = (MockHttpSession) result.getRequest().getSession();
    }

	public void getUsersList() throws Exception {
//	    {"total":2,"rows":[{"role":"teacher","id":2,"userName":"张猛治4","title":"教授","introduction":"我就是张猛治，哈哈"},{"role":"teacher","phone":"15545016598","id":3,"userName":"张猛治5","title":"教授","introduction":"我就是张猛治，哈哈"}]}
		RequestBuilder builder = MockMvcRequestBuilders
				.post("/post/usersListPost?offset=0&limit=2")
				//.param("offset", "0") //从第offset条开始，抓取limit条数据，即抓取第offset条 到 offset+limit-1 结束
                //.param("limit","10")
                .session(session);
		ResultActions resultActions = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
		MvcResult result = resultActions.andReturn();
		session = (MockHttpSession) result.getRequest().getSession();
	}
}
