package jcucumberng.steps.config;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import jcucumberng.steps.domain.Expense;
import jcucumberng.steps.domain.Income;

public class DataTableConfigurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

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

}
