package com.ibm.wolfs.tracker.service;

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

import com.ibm.wolfs.tracker.model.Url;
import com.ibm.wolfs.tracker.repository.UrlRepository;

/**
 * 
 * This Class is responsible to execute operations of URL Track.
 *
 * @author Wolfshorndl, D. A.
 * 
 */

@Service
public class UrlService {

	@Autowired
	private UrlRepository repository;

	private final List<Url> links = new ArrayList<Url>();
	private final List<String> fatherList = new ArrayList<String>();
	private final List<String> sonList = new ArrayList<String>();

	public List<Url> listAllLinks() {

		List<Url> allLinks = (List<Url>) repository.findAll();

		return allLinks;

	}

	public void save(Url link) {
		repository.save(link);
	}

	public List<Url> trackingLink(String linktotrack) {

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

			for (Url element : links) {
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

	private void checkAllLinks(String linktotrack, List<Url> links, Elements allLinks) {
		for (Element elementlink : allLinks) {
			Url linkobj = new Url();
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
