package pl.achrzanowski.moneymanagementservletclient.expense;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ExpenseDTO {

    private String id;
    @NotNull
    @Size(min=5, max=255)
    private String title;
    private String description;
    @NotNull
    private String category;
    @NotNull
    private String importance;
    @NotNull
    @Min(0)
    private String value;
    @NotNull
    private String occurrenceDate;
    private String createdDate;

}
