package springframework.test.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import springframework.test.model.MyModel;

@Controller
public class TestController {
	@RequestMapping(path = "/accounts/{id}", produces = { "application/json" })
	@ResponseBody
	public Map<String, String> accounts(@PathVariable("id") int id, @RequestParam("age") int age) {
		Map<String, String> map = new HashMap<>();
		map.put("name", "ackerly" + id);
		map.put("age", age + "");
		return map;
	}

	@PostMapping(path = "/post/{name}", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Map<String, String> post(@PathVariable String name) {
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		return map;
	}

	@PostMapping(path = "/post/file", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Map<String, String> post(MultipartFile file) {
		Map<String, String> map = new HashMap<>();
		map.put("size", file.getSize() + "");
		map.put("file", file.getName());
		return map;
	}

	@PostMapping(path = "/post/validate")
	// @ResponseBody
	public ModelAndView post(@Valid MyModel myModel, BindingResult result, ModelAndView model) {
		// model.setAttribute("mymodel", );
		model.addObject("mymodel", myModel);

		model.addObject("error", result.getAllErrors());
		model.setViewName("user");
		return model;

	}
}
