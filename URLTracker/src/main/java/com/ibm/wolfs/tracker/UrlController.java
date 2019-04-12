package com.ibm.wolfs.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.wolfs.tracker.model.Url;
import com.ibm.wolfs.tracker.service.UrlService;

/**
 * 
 * This Class is responsible to execute operations of URL Track.
 *
 * @author Wolfshorndl, D. A.
 * 
 */

@Controller
public class UrlController {

	@Autowired
	private UrlService service;

	@RequestMapping("/")
	public String index() {
		return "linkstracker";
	}

	@RequestMapping(value = "track", method = RequestMethod.POST)
	public String track(@RequestParam("url_field") String url, Model model) {

		service.trackingLink(url);

		Iterable<Url> allLinks = service.listAllLinks();

		model.addAttribute("allLinks", allLinks);
		model.addAttribute("originallink", url);

		return "linkstracker";

	}

}
