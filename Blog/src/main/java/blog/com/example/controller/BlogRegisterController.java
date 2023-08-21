package blog.com.example.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.example.model.entity.UserEntity;
import blog.com.example.services.BlogService;
import blog.com.example.services.UserService;
import jakarta.servlet.http.HttpSession;



@Controller
public class BlogRegisterController {
	//@Autowiredを使用してUserServiceクラスを読み込んで下さい
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	

	@Autowired
	private BlogService blogService;
	
	/* URLが　/register getRegisterPage() メソッドは、"admin_register" 
	 * というビュー（画面）を表示することで、登録画面を表示できるようにソースを書いて下さい*/
	@GetMapping("/blog/register")
	public String getBlogRegisterPage() {
		return "blog-register.html";
	}
	
	@PostMapping("/blog/register/insert")
	public String insertBlog(@RequestParam String title,
			@RequestParam String date,
			@RequestParam String category,
			@RequestParam String detail,
			@RequestParam MultipartFile image) {
//	public String insertBlog(@RequestParam String title) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		} else {
			// Parameter check
			
			// db insert
		
			try {
				
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + image.getOriginalFilename();
				String savefileName = "C:\\blogImgs\\" + fileName;
				
				/**ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
				 * productImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
				 * Path.of()メソッドを使用して、保存先のパスを指定している。
				 * 保存先のパスは、"src/main/resources/static/product-img/"というディレクトリの中に
				 * 、fileNameで指定されたファイル名で保存される。。**/
//				Files.copy(image.getInputStream(), Path.of("C:/Users/81803/Documents/workspace-spring-tool-suite-4-4.19.1.RELEASE/blogImgs" + fileName));
				File dest = new File(savefileName);
				image.transferTo(dest);
				
//				byte[] imageBase64 = Base64.encodeBase64(image.getBytes(), true);
//		        String result = new String(imageBase64);
				
				blogService.createBlog(title,category,fileName,detail,user.getUserId());
				return "redirect:/blog/list";
			} catch (Exception e) {
				// ERROR MESSAHE
				return "/";
			}
			
		}
	}


}