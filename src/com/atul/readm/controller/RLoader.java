package com.atul.readm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.atul.readm.model.Chapter;
import com.atul.readm.model.Manga;

class RLoader {

	public static List<Manga> browse(int page, String genre) {
		List<Manga> mangaList = new ArrayList<>();
		try {
			String pageUrl;

			if (genre == null)
				pageUrl = RApiBuilder.buildBrowse(page);
			else
				pageUrl = RApiBuilder.buildCatBrowse(page, genre);

			Element doc = Jsoup.connect(pageUrl).get().body();
			for (Element manga : doc.select("li[class=mb-lg]")) {
				String title = manga.select("div[class=subject-title]").select("a").attr("title");
				String url = manga.select("div[class=subject-title]").select("a").attr("href");
				String summary = manga.select("p[class=desktop-only excerpt]").text();
				String rating = manga.select("div[class=color-imdb]").text().split(" ")[1];
				String art = manga.select("div[class=poster-with-subject]").select("img").attr("src");
				List<String> tags = new ArrayList<>();

				for (Element tag : manga.select("span[class=genres]").select("a")) {
					tags.add(tag.attr("title"));
				}

				Manga m = new Manga(title, url, summary, rating, art, tags);
				mangaList.add(m);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mangaList;
	}

	public static Manga getChapters(Manga manga) {
		List<Chapter> chapterList = new ArrayList<>();

		try {

			Element doc = Jsoup.connect(RApiBuilder.buildCombo(manga.url)).get().body();
			for (Element chp : doc.select("section[class=episodes-box]")
					.select("table[class=ui basic unstackable table]")) {
				String title = chp.select("a").text();
				String url = chp.select("a").attr("href");
				String pub = chp.select("td[class=episode-date]").text();

				Chapter chapter = new Chapter(title, url, pub, null);
				chapterList.add(chapter);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		manga.chapters = chapterList;
		return manga;
	}

	public static Chapter getPages(Chapter chapter) {
		List<String> pages = new ArrayList<>();

		try {

			Element doc = Jsoup.connect(RApiBuilder.buildCombo(chapter.url)).get().body();
			for (Element pg : doc.select("img[class=img-responsive scroll-down]")) {
				String page = pg.attr("src");
				pages.add(page);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		chapter.pages = pages;
		return chapter;
	}

}
