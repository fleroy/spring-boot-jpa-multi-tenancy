package org.github.fleroy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.github.fleroy.entity.Item;
import org.github.fleroy.repository.AllItemRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/all")
@Transactional
public class NoTenantController {

	@Autowired
	private AllItemRepository itemRepository;
	@Autowired

	@Qualifier("entityManagerFactory2")
	private EntityManager entityManager;

	@RequestMapping
	public String items(Model model) {
		List<Item> items = itemRepository.search();
		model.addAttribute("items", items);
		return "items";
	}

}
