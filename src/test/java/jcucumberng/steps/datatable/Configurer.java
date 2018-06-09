package jcucumberng.steps.datatable;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import jcucumberng.steps.pojos.Expense;
import jcucumberng.steps.pojos.Income;

public class Configurer implements TypeRegistryConfigurer {

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		registry.defineDataTableType(new DataTableType(Income.class, new TableEntryTransformer<Income>() {
			@Override
			public Income transform(Map<String, String> entry) {
				return new Income(entry.get("name"), entry.get("amount"), entry.get("frequency"));
			}
		}));

		registry.defineDataTableType(new DataTableType(Expense.class, new TableEntryTransformer<Expense>() {
			@Override
			public Expense transform(Map<String, String> entry) {
				return new Expense(entry.get("name"), entry.get("amount"), entry.get("frequency"));
			}
		}));
	}

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

}
