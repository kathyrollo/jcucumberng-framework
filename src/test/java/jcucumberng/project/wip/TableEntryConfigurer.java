package jcucumberng.project.wip;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import jcucumberng.project.domain.Transaction;

/**
 * Maps DataTable with header row in feature file to multiple domain objects of
 * Type&lt;T&gt;. Each row below the header is an object.
 */
public class TableEntryConfigurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		registry.defineDataTableType(new DataTableType(Transaction.class, new TableEntryTransformer<Transaction>() {
			@Override
			public Transaction transform(Map<String, String> entry) {
				return new Transaction(entry.get("name"), entry.get("amount"), entry.get("frequency"),
						entry.get("month"));
			}
		}));

	}

}
