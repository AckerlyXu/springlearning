package spring.webmvc;

import org.springframework.web.util.UriComponentsBuilder;

public class Test {

	@org.junit.Test
	public void test() {
//		UriComponents uriComponent = UriComponentsBuilder.fromUriString("http://localhost:8000/pet/{id}")
//				.queryParam("q", "{q}").build();
//		String str = uriComponent.expand("1", "123%= 456").encode().toString();
//		System.out.println(str);

		// 路径和queryString中都会编码
		String str2 = UriComponentsBuilder.fromUriString("http://localhost:8000/pe_ %=t/{id}").queryParam("q", "{q}")
				.buildAndExpand("123=%&", 234).encode().toString();

		System.out.println(str2);

		String str3 = UriComponentsBuilder.fromPath("/pe% t/{id}").queryParam("q", "{q}").buildAndExpand("123* 5", 12)
				.encode().toString();
		System.out.println(str3);
	}

}
