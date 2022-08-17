package pl.achrzanowski.moneymanagementservletclient.expense;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class Expense {

    private String id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min=5, max=255, message = "Title must be between 5 and 255 characters long")
    private String title;
    private String description;
    @NotNull(message = "Category must be selected")
    private String category;
    @NotNull(message = "Importance must be selected")
    private String importance;
    @NotEmpty(message = "Amount must not be empty")
    @Min(value = 0, message = "Amount must be greater than 0")
    private String amount;
    @NotEmpty(message = "Date must be selected")
    private String occurrenceDate;
    private String createdDate;

}
