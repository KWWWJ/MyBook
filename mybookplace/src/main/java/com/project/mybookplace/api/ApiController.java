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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ApiController {
	
	// 배스트셀러 데이터를 보내주는 메서드
	@ResponseBody
	@GetMapping("getBestseller")
	public String bestseller(@RequestParam(name = "page", required = false) Integer page) {
		
		return getBestseller(page);
	}
	
	// 책 상세페이지에서 IBN13이 있는 책의 정보를 보내주는 메서드
	@ResponseBody
	@GetMapping("getbook")
	public String bookId(@RequestParam(name = "bookId", required = false) String bookId) {

		return getBook(bookId);
	}
	
	// 책 상세페이지에서 ISBN13이 없는 책의 정보를 보내주는 메서드
	@ResponseBody
	@GetMapping("getbookCI")
	public String bookIdCI(@RequestParam(name = "bookCid", required = false) String bookCid) {
		return getBookCI(bookCid);
	}
	
	// 처음 들어갔을때 최신 도서 정보를 볼 수 있게 데이터를 보내주는 메서드
	@ResponseBody
	@GetMapping("getBookList")
	public String bookPage(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "category", required = false) String category) {
		
		
		return getBookList(category, page);
	}
	
	// 각각의 페이지 작업을 위해 만들어둔 search 페이지의 컨트롤러.
	@GetMapping("/search")
	public String searchPage(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "search", required = false) String search,
			Model model) throws JSONException {

		model.addAttribute("menu", "book/searchList");
		model.addAttribute("menuHead", "searchListFragment");
		model.addAttribute("nowPage", page);
		
		pageCount(model, page, type, search);
		
		return "index/layout.html";
	}
	
	// 장르번호를 기준으로 불러와 데이터 보내주는 메서드
	@ResponseBody
	@GetMapping("getGenreList")
	public String genrePage(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "order", required = false) String order,
			@RequestParam(name = "categoryId", required = false) String categoryId) {
		
		return getBookGenre(order, categoryId, page);
	}
	
	// 리뷰쓸때 책 선택하도록 리스트 데이터 보내주는 메서드
	@ResponseBody
	@GetMapping("/getBookTitle")
	public String searchBook(
			@RequestParam(name = "search", required = false) String search,
			Model model) throws JSONException {
		
		
		return searchBook("Title", search);
	}
	
	// 책 제목 검색해서 그 책 정보를 다시 보내주는 메서드
	@ResponseBody
	@GetMapping("/searchBook")
	public String searchBook(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "search", required = false) String search,
			Model model) throws JSONException {
		
		
		
		return searchBook("Title", search, page);
	}
	
	// 작가로 검색해서 그 책 정보를 다시 보내주는 메서드
	@ResponseBody
	@GetMapping("/searchAuthor")
	public String searchAuthor(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "search", required = false) String search,
			Model model) throws JSONException {
		
		int dataLength = listLength(searchBook("Author", search));
		int bookListLength = dataLength / 10;
		
		if(bookListLength == 0) {
			bookListLength = 1;
		}
		
		if(!(dataLength%10 == 0) && dataLength > 10) {
			bookListLength = bookListLength + 1;
		}
		
		model.addAttribute("pageCount", bookListLength);
		
		return searchBook("Author", search, page);
	}
	
	// 일주일 단위로 바뀌는 베스트셀러를 가져오기
	public String getBestseller(int start) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
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
	
	// 새로 추가된 순으로 가져오기
	public String getBookList(String type, int start) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
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
	
	// ISBN13 기준으로 가져오기
	public String getBook(String bookId) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemLookUp.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
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
	        String getBook = result.getBody();
	        
	        return getBook;
	}
	// ISBN 기준으로 가져오기 (한정판이나 다른 버전의 도서의 경우 이것만 존재할 수 있다.)
	public String getBookCI(String bookId) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemLookUp.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
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
	        String getBookCI = result.getBody();
	        
	        return getBookCI;
	}
	// 장르 번호(CID) 기준으로 가져오기
	public String getBookGenre(String order, String cId, int page) {
		 URI aladinUri = UriComponentsBuilder
	                .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemList.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
	                .queryParam("QueryType",order)
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
	        String getBookGenre = result.getBody();
	        
	        return getBookGenre;
	}
	// 제목 혹은 작가 등으로 검색한 기준으로 가져오기
	public String searchBook(String order, String title) {
		 URI aladinUri = UriComponentsBuilder
				 .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemSearch.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
	                .queryParam("Query",title)
	                .queryParam("QueryType",order)
	                .queryParam("MaxResults",50)
	                .queryParam("start",1)
	                .queryParam("SearchTarget","Book")
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
	        String searchBook = result.getBody();
	        
	        return searchBook;
	}
	
	// 작가 혹은 제목을 기준으로 가져오되 한 번에 10개씩 패이지를 정함
	public String searchBook(String order, String search, int start) {
		 URI aladinUri = UriComponentsBuilder
				 .fromUriString("http://www.aladin.co.kr")
	                .path("/ttb/api/ItemSearch.aspx")
	                .queryParam("ttbkey","ttbbrilliantpop1102003")
	                .queryParam("Query",search)
	                .queryParam("QueryType",order)
	                .queryParam("MaxResults",10)
	                .queryParam("start",start)
	                .queryParam("SearchTarget","Book")
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
	        String searchBook = result.getBody();
	        
	        return searchBook;
	}
	
	// 가져오는 책의 개수를 return하는 메서드(페이지 생성에 사용)
	public int listLength(String listData) throws JSONException {
		
		JSONObject object = new JSONObject(listData);
		JSONArray data = object.getJSONArray("item");
		
		int dataLength = data.length();
		
		return dataLength;
	}
	
	// 가져오는 책의 량이 항상 200개를 채우지 않으므로 각각의 경우에 처리
	private void pageCount(Model model, int page, String type, String search) throws JSONException {
		int newPage = ( page / 5 ) +1;
		int pageCount = 0;
		int startPage = 0;
		int dataLength = listLength(searchBook(type, search));
		// 한번에 가져올수 있는 데이터가 50개로 제한이기 떄문에 페이지에 따라 곱해준다.
		int bookListLength = (((page / 5) + 1) * dataLength) / 10;
		if(bookListLength == 0) {
			bookListLength = 1;
		}
		
		if(!(dataLength%10 == 0) && dataLength > 10) {
			bookListLength = bookListLength+1;
		}
		
		if(page < bookListLength+1 && page < 21) {
			
			if(page % 5 == 0) {
				newPage = newPage - 1;
			}
			startPage = (newPage - 1) * 5 + 1;
			// 페이지가 충분히 있을 때는 전부 내오지만
			pageCount = newPage * 5;
			
			// 부족할때는 그만큼만 보이도록
			if(bookListLength < 5) {
				pageCount = bookListLength;		}
			// 5개보다는 많지만 딱 떨어지지 않는경우 그만큼만 나오도록
			if(!(bookListLength % 5 == 0) && startPage > (bookListLength/5) * 5 && bookListLength > 5) {
				pageCount = (bookListLength / 5) + (bookListLength % 5) - 1;
			}
			// 최대로 가져올 수 있는 데이터가 200개 까지이므로 제한을 걸어둔다
			if(bookListLength > 20) {
				bookListLength = 20;
			}
		}
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("allPage", bookListLength);
		model.addAttribute("searchPageCount", pageCount);
	}
}
