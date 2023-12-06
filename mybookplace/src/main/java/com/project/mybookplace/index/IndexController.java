package com.project.mybookplace.index;

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

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class IndexController {
	
	@GetMapping("/home")
	public String home(Model model) {
		return "home.html";
	}
	
	@GetMapping("/book")
	public String reviewPage(@RequestParam(name = "bookid", required = false) Long bookid, Model model) {
		model.addAttribute("menu", "menu/book");
		model.addAttribute("menuHead", "bookFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/bestseller")
	public String bestsellerPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "menu/bestseller");
		model.addAttribute("menuHead", "bestsellerFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/genre")
	public String genrePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "menu/bookList");
		model.addAttribute("menuHead", "bookListFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/review")
	public String reviewPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "review/reviewList");
		model.addAttribute("menuHead", "reviewListFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("menu", "user/login");
		model.addAttribute("menuHead", "loginFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/regist")
	public String registPage(Model model) {
		model.addAttribute("menu", "user/regist");
		model.addAttribute("menuHead", "registFragment");
		return "index/layout.html";
	}
	
	@ResponseBody
	@GetMapping("getBestseller")
	public String bestseller(@RequestParam(name = "page", required = false) Integer page) {
		
		return getBestseller(page);
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
	        String Bestseller = result.getBody();
	        
	        return Bestseller;
	}
	
	public String getBook(Long bookId) {
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
	
	public String searchBookTitle(String title) {
		 URI aladinUri = UriComponentsBuilder
				 .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemSearch.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102001")
	                .queryParam("Query","aladdin")
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
