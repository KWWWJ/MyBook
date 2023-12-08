package com.project.mybookplace.api;

import java.net.URI;
import java.nio.charset.Charset;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ApiController {
	
	@ResponseBody
	@GetMapping("getBestseller")
	public String bestseller(@RequestParam(name = "page", required = false) Integer page) {
		
		return getBestseller(page);
	}
	
	@ResponseBody
	@GetMapping("getbook")
	public String bookId(@RequestParam(name = "bookId", required = false) String bookId) {

		return getBook(bookId);
	}
	
	@ResponseBody
	@GetMapping("getbookCI")
	public String bookIdCI(@RequestParam(name = "bookIdCI", required = false) String bookId) {

		return getBookCI(bookId);
	}
	
	@ResponseBody
	@GetMapping("getBookList")
	public String bookPage(@RequestParam(name = "page", required = false) int page,
			@RequestParam(name = "category", required = false) String category,
			Model model) {
		
		
		return getBookList(category, page);
	}
	
	@ResponseBody
	@GetMapping("getGenreList")
	public String genrePage(@RequestParam(name = "page", required = false) int page,
			@RequestParam(name = "categoryId", required = false) String categoryId,
			Model model) {
		
		return getBookGenre(categoryId, page);
	}
	
	
	public String getBestseller(int start) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("QueryType","Bestseller")
	                .queryParam("MaxResults",50)
	                .queryParam("start",start)
	                .queryParam("Cover","Big")
	                .queryParam("SearchTarget","Book")
	                .queryParam("output","js")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String bestseller = result.getBody();
	        
	        return bestseller;
	}
	
	public String getBookList(String type, int start) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("QueryType",type)
	                .queryParam("MaxResults",10)
	                .queryParam("start",start)
	                .queryParam("Cover","Big")
	                .queryParam("SearchTarget","Book")
	                .queryParam("output","js")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String bookList = result.getBody();
	        
	        return bookList;
	}
	
	public String getBook(String bookId) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemLookUp.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("ItemIdType","ISBN13")
	                .queryParam("ItemId",bookId)
	                .queryParam("output","js")
	                .queryParam("Cover","Big")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String Bestseller = result.getBody();
	        
	        return Bestseller;
	}
	
	public String getBookCI(String bookId) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemLookUp.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("ItemIdType","ISBN")
	                .queryParam("ItemId",bookId)
	                .queryParam("output","js")
	                .queryParam("Cover","Big")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String Bestseller = result.getBody();
	        
	        return Bestseller;
	}
	
	public String getBookGenre(String cId, int page) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("QueryType","Bestseller")
	                .queryParam("MaxResults",10)
	                .queryParam("start",page)
	                .queryParam("CategoryId",cId)
	                .queryParam("output","js")
	                .queryParam("SearchTarget","Book")
	                .queryParam("Cover","Big")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String Bestseller = result.getBody();
	        
	        return Bestseller;
	}
	
	public String searchBookTitle(String title) {
		 URI aladinUri = UriComponentsBuilder
				 .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemSearch.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("Query",title)
	                .queryParam("QueryType","Title")
	                .queryParam("MaxResults",50)
	                .queryParam("start",1)
	                .queryParam("SearchTarget","Book")
	                .queryParam("Cover","Big")
	                .queryParam("output","js")
	                .queryParam("Version",20131101)
	                .encode(Charset.forName("UTF-8"))
	                .encode()
	                .build()
	                .toUri();
	 
	        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
	                .get(aladinUri)
	                .build();
	 
	        log.info("uri : {}",aladinUri);
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
	        String Bestseller = result.getBody();
	        
	        return Bestseller;
	}
}
