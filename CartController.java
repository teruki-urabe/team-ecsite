package jp.co.internous.printemps.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.printemps.model.domain.TblCart;
import jp.co.internous.printemps.model.domain.dto.CartDto;
import jp.co.internous.printemps.model.form.CartForm;
import jp.co.internous.printemps.model.mapper.TblCartMapper;
import jp.co.internous.printemps.model.session.LoginSession;

@Controller
@RequestMapping("/printemps/cart")
public class CartController {
	
	@Autowired
	private LoginSession loginSession;

	@Autowired
	private TblCartMapper tblCartMapper;

	private Gson gson = new Gson();

	@RequestMapping("/")
	public String index(Model m) {

		int userId = loginSession.isLogined() ? loginSession.getUserId() : loginSession.getTmpUserId();

		List<CartDto> carts = tblCartMapper.findCartByUserId(userId);

		m.addAttribute("carts", carts);
		m.addAttribute("loginSession", loginSession);
		
		return "cart";
	}

	@RequestMapping("/add")
	public String add(CartForm form) {
		
		int userId = loginSession.isLogined() ? loginSession.getUserId() : loginSession.getTmpUserId();
		
		form.setUserId(userId);
		
		TblCart cart = new TblCart(form);
		if (tblCartMapper.findCountByUserIdAndProuductId(userId, form.getProductId()) > 0) {

			tblCartMapper.updateProductCountByUserIdAndProductId(cart);
		}else {
			tblCartMapper.insertCart(cart);
		}
		
		return "forward:/printemps/cart/";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/delete")
	public boolean delete(@RequestBody String checkedIdList) {
		int result = 0;

		Map<String,List<String>> map = gson.fromJson(checkedIdList, Map.class);
		List<String> checkedIds = map.get("checkedIdList");
		
		for(String id : checkedIds) {
			result += tblCartMapper.deleteCartById(Integer.parseInt(id));
		}
		
		return result > 0;
	}
}
