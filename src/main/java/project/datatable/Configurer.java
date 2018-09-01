package project.datatable;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableTransformer;
import project.domain.Transaction;

/**
 * Maps DataTable in feature file to objects.
 */
public class Configurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		// With header row, multiple objects of Type<T>
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableEntryTransformer<Transaction>() {
			@Override
			public Transaction transform(Map<String, String> map) {
				return new Transaction(map.get("name"), map.get("amount"), map.get("frequency"), map.get("month"));
			}
		}));

		// With label column, single object of Type<T>
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable dataTable) throws Throwable {
				return new Transaction(dataTable.asMaps().get(0));
			}
		}));

	}

}
