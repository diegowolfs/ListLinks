package com.ibm.wolfs.linklist.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.wolfs.linklist.model.Link;
import com.ibm.wolfs.linklist.repository.LinkRepository;

/**
 * 
 * This Class is responsible to execute operations of URL Track.
 *
 * @author Wolfshorndl, D. A.
 * 
 */

@Service
public class LinkService {

	@Autowired
	private LinkRepository repository;

	private final List<Link> links = new ArrayList<Link>();
	private final List<String> fatherList = new ArrayList<String>();
	private final List<String> sonList = new ArrayList<String>();

	public List<Link> listAllLinks() {

		List<Link> allLinks = (List<Link>) repository.findAll();

		return allLinks;

	}

	public void save(Link link) {
		repository.save(link);
	}

	public List<Link> trackingLink(String linktotrack) {

		if (urlValidator(linktotrack)) {
			String url = linktotrack;
			Document htmlpage = null;
			try {
				htmlpage = Jsoup.connect(url).timeout(10 * 1000).get();
				;
				Elements allLinks = htmlpage.select("a[href]");
				checkAllLinks(linktotrack, links, allLinks);
			} catch (HttpStatusException hse) {
				hse.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (Link element : links) {
				fatherList.add(element.getFather());
				sonList.add(element.getLink());
			}

			trackingChilds();
		}

		return links;

	}

	public void trackingChilds() {

		Set<String> s = new HashSet<String>(fatherList);

		for (String str : sonList) {
			if (!s.contains(str) && urlValidator(str)) {
				String url = str;
				Document htmlpage = null;
				try {
					htmlpage = Jsoup.connect(url).timeout(10 * 1000).get();
					;
					Elements allLinks = htmlpage.select("a[href]");
					checkAllLinks(str, links, allLinks);
				} catch (HttpStatusException hse) {
					hse.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void checkAllLinks(String linktotrack, List<Link> links, Elements allLinks) {
		for (Element elementlink : allLinks) {
			Link linkobj = new Link();
			String linktracked = print("%s", elementlink.attr("abs:href"), trim(elementlink.text(), 35));

			linkobj.setFather(linktotrack);
			linkobj.setLink(linktracked);

			if ((linkobj.getFather().length() < 255) && (linkobj.getLink().length() < 255)) {
				save(linkobj);
			}

			links.add(linkobj);

		}
	}

	public static boolean urlValidator(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (URISyntaxException exception) {
			return false;
		} catch (MalformedURLException exception) {
			return false;
		}
	}

	private static String print(String msg, Object... args) {
		String urlstr = String.format(msg, args);
		return urlstr;
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

}
