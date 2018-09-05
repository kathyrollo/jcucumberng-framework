package project.wip;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import project.domain.Transaction;

/**
 * Maps DataTable with header row in feature file to multiple objects of
 * Type{@literal <T>}. Each row below the header is an object.<br>
 * <br>
 * Need fix for Issue <a href="https://bit.ly/2NSqn93">{@literal #1426}</a>.
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
			public Transaction transform(Map<String, String> map) {
				return new Transaction(map.get("name"), map.get("amount"), map.get("frequency"), map.get("month"));
			}
		}));

	}

}
