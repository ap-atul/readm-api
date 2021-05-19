# readm.org api
Java api for readm.org, includes web scraping, uses jsoup

## Usage
1. Declare a client object, attach RListener

```java
class Sample implements RListener {

    // this refers to the instance of the RListener
    RClient client = new RClient(this);
}
```

2. Call methods to fetch data

```java

// browse the pages, with page no and genre
client.browse(1, null);

// search by keyword
client.search(<your query>);

// to get all chapters for certain manga
client.chapters(manga);

// to get all pages for certain chapter
client.pages(chapter);

// you can get all genres using
Set<String> genres = client.genres();

```

3. Implement the methods of RListener

```java
@Override
public void setMangas(List<Manga> mangas) {
    for (Manga m : mangas) {
        System.out.println(m);
    }
    c.chapters(mangas.get(0));
}

@Override
public void setChapters(Manga chapters) {
    for (Chapter c : chapters.chapters) {
        System.out.println(c);
    }
    c.pages(chapters.chapters.get(0));
}

@Override
public void setPages(Chapter pages) {
    for (String s : pages.pages) {
        System.out.println(s);
    }
}
```
