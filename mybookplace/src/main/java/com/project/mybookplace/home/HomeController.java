package com.project.mybookplace.home;

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
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		model.addAttribute("menu", "test");
		session.setAttribute("userId", null);
		return "index/layout.html";
	}
	
	@GetMapping("/bestseller")
	public String bestsellerPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		model.addAttribute("menu", "/menu/bestseller");
		model.addAttribute("menuHead", "bestsellerFragment");
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
//	                .queryParam("CategoryId",1) //장르별로 가져올떄 사용
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
