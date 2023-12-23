package com.project.mybookplace.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.mybookplace.review.service.ReviewService;
import com.project.mybookplace.user.domain.User;
import com.project.mybookplace.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;
	
	private static final String UPLOAD_EVENT_DIRECTORY = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/event/";
	private static final String UPLOAD_BOOK_DIRECTORY = "/usr/local/apache-tomcat-10.1.17/webapps/ROOT/WEB-INF/classes/static/upload/book/";
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		
		List<String> eventImageFileNames = getEventImageFileNames();
		List<String> bookImageFileNames = getBookImageFileNames();
        model.addAttribute("eventImageFileNames", eventImageFileNames);
        model.addAttribute("bookImageFileNames", bookImageFileNames);
		User tempUser = new User();
		int userId = 0;
		
		if(session.getAttribute("userId") != null) {
			userId = (Integer)session.getAttribute("userId");
			tempUser = userService.getUser(userId);
			model.addAttribute("admin", tempUser.isAdmin());
		} else {
			model.addAttribute("admin", null);
		}
		
		return "home.html";
	}
	
	@GetMapping("/book")
	public String reviewPage(@RequestParam(name = "bookid", required = false) Long bookid, Model model) {
		getPage(model, "book/book", "bookFragment");
		
		return "index/layout.html";
	}
	
	@GetMapping("/bestseller")
	public String bestsellerPage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		getPage(model, "book/bestseller", "bestsellerFragment");
		return "index/layout.html";
	}
	
	@GetMapping("/genre")
	public String genrePage(@RequestParam(name = "page", required = false) Integer page, Model model) {
		getPage(model, "book/bookList", "bookListFragment");
		model.addAttribute("nowPage", page);
		
		pageCount(model, page);
		
		return "index/layout.html";
	}
	
	@GetMapping("/upload")
	public String upload(Model model) {
		getPage(model, "admin/upload", "uploadFragment");
		
		List<String> eventImageFileNames = getEventImageFileNames();
		List<String> bookImageFileNames = getBookImageFileNames();
        model.addAttribute("eventImageFileNames", eventImageFileNames);
        model.addAttribute("bookImageFileNames", bookImageFileNames);
		
		return "index/layout.html";
	}
	
	
	
	private void pageCount(Model model, int page) {
		int newPage = ( page / 5 ) +1;
		int pageCount = 0;
		int startPage = 0;
		
		if(page < 21) {
			
			if(page % 5 == 0) {
				newPage = newPage - 1;
			}
			startPage = (newPage - 1) * 5 + 1;

			pageCount = newPage * 5;
		}
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("pageCount", pageCount);
	}
	
	private void getPage(Model model, String html, String fragment) {
		model.addAttribute("menu", html);
		model.addAttribute("menuHead", fragment);
	}
	
	 private List<String> getEventImageFileNames() {
	        List<String> imageFileNames = new ArrayList<String>();
	        File uploadDirectory = new File(UPLOAD_EVENT_DIRECTORY);
	        File[] files = uploadDirectory.listFiles();

	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile()) {
	                    imageFileNames.add("upload/event/"+file.getName());
	                }
	            }
	        }

	        return imageFileNames;
	    }
	 
	 private List<String> getBookImageFileNames() {
	        List<String> imageFileNames = new ArrayList<String>();
	        File uploadDirectory = new File(UPLOAD_BOOK_DIRECTORY);
	        File[] files = uploadDirectory.listFiles();

	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile()) {
	                    imageFileNames.add("upload/book/"+file.getName());
	                }
	            }
	        }

	        return imageFileNames;
	    }
}
