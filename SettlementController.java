 package jp.co.internous.printemps.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.internous.printemps.model.domain.MstDestination;
import jp.co.internous.printemps.model.mapper.MstDestinationMapper;
import jp.co.internous.printemps.model.mapper.TblCartMapper;
import jp.co.internous.printemps.model.mapper.TblPurchaseHistoryMapper;
import jp.co.internous.printemps.model.session.LoginSession;

@Controller
@RequestMapping("/printemps/settlement")
public class SettlementController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstDestinationMapper mstDestinationMapper;
	
	@Autowired
	private TblCartMapper tblCartMapper;
	
	@Autowired
	private TblPurchaseHistoryMapper purchaseHistoryMapper;
	
	@RequestMapping("/")
	public String settlement(Model m) {
		int userId = loginSession.getUserId();
		
		List<MstDestination> destinations = mstDestinationMapper.findDestinationByUserId(userId);
		m.addAttribute("destinations",destinations);
		m.addAttribute("loginSession", loginSession);
		
		return "settlement";
	}
	
	@RequestMapping("/complete")
	public String complete(	@RequestParam("id") int destinationId) {
		int userId = loginSession.getUserId();
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("userId",userId);
		parameter.put("destinationId",destinationId);
		purchaseHistoryMapper.insert(parameter);
		tblCartMapper.deleteCartByUserId(userId);
		
		return "forward:/printemps/history/";
	}
}
