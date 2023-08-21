package blog.com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.example.model.entity.BlogEntity;
import blog.com.example.model.entity.UserEntity;
import blog.com.example.services.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	@GetMapping("/blog/list")
	public String getBlogListPage(Model model) {
		// セッションから情報を取得する
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			List<BlogEntity> blogList = blogService.selectAll(user.getUserId());
			List<BlogEntity> newBlogList = new ArrayList();
			for(BlogEntity item:blogList) {
				File dest = new File("C:\\blogImgs\\" + item.getBlogImage());
				
				byte[] fileContent;
				try {
					fileContent = Files.readAllBytes(dest.toPath());
					byte[] imageBase64 = Base64.encodeBase64(fileContent, true);
					item.setBlogImage("data:image/png;base64," + new String(imageBase64));
				} catch (IOException e) {
					e.printStackTrace();
				}				
				newBlogList.add(item);
			}
			
			model.addAttribute("blogList", newBlogList);
			model.addAttribute("userName", user.getUserName());
			return "blog-list.html";
		}
	}
	
	// 登録画面処理
	@GetMapping("/blog/newblog")
	public String newBlog(Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("userName", user.getUserName());
			model.addAttribute("userId", user.getUserId());
			// ブログのregisterに行く
//			return "blog-register.html";
			
			return "redirect:/blog/register";
		}
	}
	
	@PostMapping("/blog/blogEdit")
	public String editBlog(@RequestParam String blogId) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		} else {
			session.setAttribute("blogId",blogId);
			return "redirect:/blog/editPage";
		}
	}

	// 登録画面処理
//	@GetMapping("/blog/register")
//	public String getBlogRegisterPage(Model model) {
//		UserEntity user = (UserEntity) session.getAttribute("user");
//
//		if (user == null) {
//			return "redirect:/login";
//		} else {
//			model.addAttribute("userName", user.getUserName());
//			model.addAttribute("userId", user.getUserId());
//			// ブログのregisterに行く
//			return "blog-register.html";
////			return "redirect:/register";
//		}
//	}

	@PostMapping("/list/register/process")
	public String getBlogRegisterProcess(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article, @RequestParam Long userId,
			Model model) {
		// ブログ登録必要なパラメーターを渡す
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			/**
			 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
			 **/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			/**
			 * ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
			 * productImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
			 * Path.of()メソッドを使用して、保存先のパスを指定している。
			 * 保存先のパスは、"src/main/resources/static/blog-img/"というディレクトリの中に
			 * 、fileNameで指定されたファイル名で保存される。。
			 **/
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// サービスのメソッドを呼び出して、ブログを保存する
			if (blogService.createBlog(blogTitle, categoryName, fileName, article, userId)) {
				return "redirect:/blog-list";
			} else {
				// できなかった場合は、registerの画面に返す。
				return "redirect:/blog-register";
			}
		}
	}

	// 編集の画面を表示させるソース
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			BlogEntity blogList = blogService.getBlogPost(blogId);
			if (blogList == null) {
				return "redirect:/blog/list";
			} else {
				model.addAttribute("userName", user.getUserName());
				model.addAttribute("blogList", blogList);
				return "blog-edit.html";
			}

		}
	}
	@PostMapping("/blog/edit/process")
	public String getProductEditProcess(@RequestParam Long blogId,@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article,Model model) {
		
		UserEntity user = (UserEntity)session.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}else {
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + blogImage.getOriginalFilename();
			try {
				/**ファイルを実際にサーバー上に保存するための処理を行っています。Files.copy()メソッドを使用して、
				 * productImageオブジェクトから入力ストリームを取得し、指定されたパスにファイルをコピー。
				 * Path.of()メソッドを使用して、保存先のパスを指定している。
				 * 保存先のパスは、"src/main/resources/static/product-img/"というディレクトリの中に
				 * 、fileNameで指定されたファイル名で保存される。。**/
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(blogService.editBlog(blogId,  blogTitle, categoryName, fileName, article)) {
				return "redirect:/blog/list";
			}else {
				return "redirect:/blog/edit/" + blogId;
			}
		}		
	}
	@PostMapping("/blog/delete")
	public String delete(@RequestParam Long blogId) {
		if(blogService.deleteBlog(blogId)) {
			return "redirect:/blog/list";
		}else {
			return "redirect:/blog/edit/" + blogId;
		}
	}
	//ログアウト
	@GetMapping("/logout")
	public String Logout() {
		session.invalidate();
		return "redirect:/login";
	}
}

