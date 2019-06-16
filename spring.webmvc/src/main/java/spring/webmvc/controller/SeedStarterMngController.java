package spring.webmvc.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.webmvc.model.Feature;
import spring.webmvc.model.Row;
import spring.webmvc.model.SeedStarter;
import spring.webmvc.model.Type;
import spring.webmvc.model.Variety;
import spring.webmvc.service.SeedStarterService;
import spring.webmvc.service.VarietyService;

@Controller
public class SeedStarterMngController {
	@Autowired
	private VarietyService varietyService;
	@Autowired
	private SeedStarterService seedStarterService;

	@ModelAttribute("allTypes")
	public List<Type> populateTypes() {
		return Arrays.asList(Type.ALL);
	}

	@ModelAttribute("allFeatures")
	public List<Feature> populateFeatures() {
		return Arrays.asList(Feature.ALL);
	}

	@ModelAttribute("allVarieties")
	public List<Variety> populateVarieties() {
		return this.varietyService.findAll();
	}

	@ModelAttribute("allSeedStarters")
	public List<SeedStarter> populateSeedStarters() {
		return this.seedStarterService.findAll();
	}

	@RequestMapping({ "/", "/seedstartermng" })
	public String showSeedstarters(final SeedStarter seedStarter, BindingResult bindingResult, ModelMap model) {
		seedStarter.setDatePlanted(Calendar.getInstance().getTime());
		SeedStarter seedStarter1 = new SeedStarter();
		seedStarter1.setCovered(true);
		seedStarter1.setDatePlanted(new Date());
		seedStarter1.setType(Type.WOOD);
		seedStarter1.setFeatures(Feature.ALL);
		bindingResult.rejectValue("datePlanted", "数据错误");
		model.addAttribute("seedStarted", seedStarter1);
		return "seedstartermng";
	}

	@RequestMapping(value = "/seedstartermng", params = { "save" })
	public String saveSeedstarter(final SeedStarter seedStarter, final BindingResult bindingResult,
			final ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "seedstartermng";
		}
		this.seedStarterService.add(seedStarter);
		model.clear();
		return "redirect:/seedstartermng";
	}

	@RequestMapping(value = "/seedstartermng", params = { "addRow" })
	public String addRow(final SeedStarter seedStarter, BindingResult bindingResult, ModelMap model) {
		seedStarter.getRows().add(new Row());
		model.addAttribute("seedStarted", seedStarter);

		// bindingResult.rejectValue("datePlanted", "数据错误");
		// 如果要让前台拿到错误信息，需要以这样的形式把bindingResult放到模型中去
		model.addAttribute(BindingResult.class.getName() + ".seedStarted", bindingResult);
		return "seedstartermng";
	}

	@RequestMapping(value = "/seedstartermng", params = { "removeRow" })
	public String removeRow(final SeedStarter seedStarter, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		seedStarter.getRows().remove(rowId.intValue());
		return "seedstartermng";
	}

}
