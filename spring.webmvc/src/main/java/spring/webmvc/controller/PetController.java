package spring.webmvc.controller;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import spring.webmvc.model.Pet;

/**
 * 
 * @author xuqiang 当具有SessionAttributes时，如果方法中向模型中加入pet属性，会自动添加到session中
 */
@SessionAttributes("pet")
@Controller
@RequestMapping("/pet")
public class PetController {
	@RequestMapping(path = "/{petId}", method = { RequestMethod.POST })
	@ResponseBody
	/*
	 * 測試@ModelAttribute的數據源，這裏得到可以來自@PathVariable，也可以來自RequestParam 也可以來自session
	 */
	public Pet EditPet(@ModelAttribute() Pet pet, BindingResult result, WebRequest request) {

		Object session = request.getAttribute("pet", WebRequest.SCOPE_SESSION);
		return (Pet) session;
	}

	/*
	 * spring会自动在所有被requestMapping注解的方法之前调用这个方法，并把返回值放到模型中
	 */
	@ModelAttribute("info")
	public String getInfo() {
		return "this is info";
	}

	/**
	 * 也可以通过这种方式初始化model
	 * 
	 */
	@ModelAttribute
	public void model(@PathVariable Optional<Integer> petId, Model model) {
		petId.ifPresent(System.out::println);
		model.addAttribute("model", "my model");
	}

	// @GetMapping("/query1")
	@RequestMapping(path = "/query1", method = { RequestMethod.GET })
	// 这种做法会默认把query1作为要寻找的视图名称
	// 其他方面和没有requestMapping的ModelAttribute一样
	@ModelAttribute("pet")
	public Pet query() {
		Pet pet = new Pet();
		pet.setName("name");
		pet.setAge(11);
		return pet;
	}

	@RequestMapping("/buildpath/{path}")
	@ResponseBody
	public String buildPath(HttpServletRequest request, @PathVariable String path) {
		// 通过request创建连接
		String str = ServletUriComponentsBuilder.fromRequest(request).replaceQueryParam("name", "{name}")
				.buildAndExpand("123").encode().toString();

		// 通过方法名称创建连接
		// 如果pathValable已经被赋值，那么路径中的{path}就是这个值，如这里已经传递了456作为参数，并且这个参数是PathVariable
		// 那么mapping中的{path}就是这个值
		str = MvcUriComponentsBuilder.fromMethodName(PetController.class, "buildPath", request, "456")
				.queryParam("pet", "{pet}").buildAndExpand("123").encode().toString();

		// 通过模拟创建连接
		// str = MvcUriComponentsBuilder
		// .fromMethodCall(MvcUriComponentsBuilder.on(PetController.class).buildPath(request,
		// "454"))
		// .queryParam("pet", "{pet}").buildAndExpand("123").encode().toString();
		return str;
	}

	/*
	 * model中也会包含session中的值
	 */
	@RequestMapping(path = "/{petId}", method = { RequestMethod.GET })
	public String EditPet(@PathVariable int petId, ModelMap model) {
		Set<String> keySet = model.keySet();
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setName("miki");
		pet.setAge(1);
		pet.setOwnerId(15);
		model.addAttribute("pet", pet);
		return "pet";
	}

	@RequestMapping("/redirect/{path}")
	public String redirect(RedirectAttributesModelMap attr) {
		// 向RedirectAttribute中添加的值也能在redirect中获取到，
		// 它比模型的好处是如果本次不是重定向就不会应用里面的值。
		attr.addAttribute("attr", "myattr");
		// 这里面redirect能够自动解析pathvariable中的值，以及放入模型中的值

		return "redirect:/{path}/{attr}";
	}

	@RequestMapping(path = "/postJson", method = { RequestMethod.GET })
	public String postJson() {
		return "json";
	}

	/**
	 * 
	 * @param pet HttpEntity类型就相当于加了@RequestBody
	 * @return
	 */

	@RequestMapping(path = "/postJson", method = { RequestMethod.POST })
	// jsonView可以限制要返回的属性
	@JsonView(Pet.WithoutOwnerView.class)
	@ResponseBody
	public Pet postJson(HttpEntity<Pet> pet) {
		/*
		 * 返回responseEntity就不需要@ResponseBody ResponseEntity<Pet> responseEntity = new
		 * ResponseEntity<Pet>(pet.getBody(), HttpStatus.OK); return responseEntity;
		 */
		return pet.getBody();
	}

	// 跨域方式3 @CrossOrigin注解
	// @CrossOrigin
	@RequestMapping(path = "/remote/{petId}", method = { RequestMethod.GET })
//	@GetMapping("/remote/{petId}")
	@ResponseBody
	public Pet getPet(@PathVariable int petId) {
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setName("peiqi");
		pet.setAge(12);
		pet.setOwnerId(112);
		return pet;
	}

}
