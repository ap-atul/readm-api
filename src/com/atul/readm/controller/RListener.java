package com.atul.readm.controller;

import java.util.List;

import com.atul.readm.model.Chapter;
import com.atul.readm.model.Manga;

public interface RListener {

	void setMangas(List<Manga> mangas);

	void setChapters(Manga manga);

	void setPages(Chapter chapter);
}
