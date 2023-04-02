package com.example.product;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ProductController {

	@Autowired
	ProductDAO productDao;

	@RequestMapping("/")
	public ModelAndView  home() {
		return new ModelAndView("index", "", "");
	}

	@RequestMapping("/list")
	public List<Map<String, Object>> list(@RequestParam(defaultValue = "") String product_name, ModelAndView mav) {
		return productDao.list(product_name);
	}

	@RequestMapping("/insert")
	public void insert(@RequestParam Map<String, Object> map, @RequestParam(required = false) MultipartFile img,
			HttpServletRequest request) {
		String filename = "-";
		if (img != null && !img.isEmpty()) {
			filename = img.getOriginalFilename();
			try {
				ServletContext application = request.getSession().getServletContext();
				String path = application.getRealPath("/static/images/");
				img.transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("filename", filename);
		productDao.insert(map);
	}

	@RequestMapping("/detail/{product_code}")
	public Map<String, Object> detail(@PathVariable String product_code, ModelAndView mav) {
		return productDao.detail(product_code);
	}

	@RequestMapping("/update")
	public void update(@RequestParam Map<String, Object> map, @RequestParam(required = false) MultipartFile img,
			HttpServletRequest request) {
		String filename = "-";
		if (img != null && !img.isEmpty()) {
			filename = img.getOriginalFilename();
			try {
				ServletContext application = request.getSession().getServletContext();
				String path = application.getRealPath("/static/images/");
				img.transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String product_code = map.get("product_code").toString();
			Map<String, Object> product = productDao.detail(product_code);
			filename = product.get("filename").toString();
		}
		map.put("filename", filename);
		productDao.update(map);
	}

	@RequestMapping("/delete")
	public void delete(int product_code, HttpServletRequest request) {
		String filename = productDao.filename(product_code);
		if (filename != null && !filename.equals("-")) {
			ServletContext application = request.getSession().getServletContext();
			String path = application.getRealPath("/static/images/");
			File file = new File(path + filename);
			if (file.exists()) {
				file.delete();
			}
		}
		productDao.delete(product_code);
	}

}
