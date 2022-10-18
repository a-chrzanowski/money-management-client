package pl.achrzanowski.moneymanagementservletclient.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.achrzanowski.moneymanagementservletclient.category.CategoryService;
import pl.achrzanowski.moneymanagementservletclient.importance.ImportanceService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImportanceService importanceService;

    private final Map<String, Object> model = new HashMap<>();

    @GetMapping("/expense/all")
    public ModelAndView getAllView(@RegisteredOAuth2AuthorizedClient("expense-client-authorization-code") OAuth2AuthorizedClient authorizedClient){
        List<Expense> expenses = expenseService.getExpenses();

        if(!model.containsKey("selectedExpense"))
            if(expenses.isEmpty())
                model.put("selectedExpense", new Expense());
            else
                model.put("selectedExpense", expenses.get(0));

        model.put("expenses", expenses);
        model.put("newExpense", new Expense());
        model.put("categories", categoryService.getCategories());
        model.put("importanceList", importanceService.getImportanceList());

        return new ModelAndView("expense/expenses-view", model);
    }

    @GetMapping("/expense/{id}")
    @SuppressWarnings("unchecked")
    public String getSelectedView(@PathVariable String id){
        Expense selectedExpense;
        if(model.containsKey("expenses")) {
            List<Expense> expenses = (List<Expense>) model.get("expenses");
            selectedExpense = expenses.stream()
                    .filter(expense -> expense.getId().equals(id))
                    .findAny()
                    .orElse(null);
        } else {
            selectedExpense = expenseService.getExpense(id);
        }
        model.put("selectedExpense", selectedExpense);
        return "redirect:/expense/all";
    }

    @PostMapping("/expense/save")
    public String save(@Valid @ModelAttribute("newExpense") Expense newExpense,
                       BindingResult bindingResult){
        String errorsAttributeName = BindingResult.class.getName() + ".newExpense";
        if(bindingResult.hasErrors()){
            model.put(errorsAttributeName, bindingResult);
        } else {
            model.remove(errorsAttributeName);
            model.put("selectedExpense", expenseService.save(newExpense));
        }
        return "redirect:/expense/all";
    }

    @PostMapping("/expense/delete/{id}")
    public String delete(@PathVariable String id){
        expenseService.delete(id);
        model.remove("selectedExpense");
        return "redirect:/expense/all";
    }

}
