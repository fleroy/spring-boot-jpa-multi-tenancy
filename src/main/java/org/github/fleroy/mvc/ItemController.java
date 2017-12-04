package org.github.fleroy.mvc;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.github.fleroy.entity.Item;
import org.github.fleroy.repository.ItemRepository;
import org.github.fleroy.support.CurrentTenantIdentifierResolverImpl;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/tenant/{tenant}")
@Transactional
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @RequestMapping
    public String items(@PathVariable String tenant, Model model) {
        List<Item> items = itemRepository.search();
        model.addAttribute("tenant", tenant);
        model.addAttribute("items", items);
        return "items";
    }

    @RequestMapping("/re-index")
    @Transactional
    public String reIndex(@PathVariable String tenant, HttpServletRequest request) throws InterruptedException {
        RequestContextHolder.getRequestAttributes().setAttribute(CurrentTenantIdentifierResolverImpl.IDENTIFIER_ATTRIBUTE, tenant, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
        return "redirect:/{tenant}";
    }

}
