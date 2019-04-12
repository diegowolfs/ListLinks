package com.ibm.wolfs.linklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.wolfs.linklist.model.Link;
import com.ibm.wolfs.linklist.service.LinkService;

/**
 * 
 * This Class is responsible to execute operations of URL Track.
 *
 * @author Wolfshorndl, D. A.
 * 
 */

@Controller
public class LinkController {

	@Autowired
	private LinkService service;

	@RequestMapping("/")
	public String index() {
		return "linkstracker";
	}

	@RequestMapping(value = "track", method = RequestMethod.POST)
	public String track(@RequestParam("url_field") String url, Model model) {

		service.trackingLink(url);

		Iterable<Link> allLinks = service.listAllLinks();

		model.addAttribute("allLinks", allLinks);
		model.addAttribute("originallink", url);

		return "linkstracker";

	}

}
