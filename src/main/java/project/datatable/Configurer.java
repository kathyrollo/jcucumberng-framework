package project.datatable;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableTransformer;
import project.dataobject.Transaction;

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

		/*
		 * Maps DataTable with header row in feature file to multiple objects of
		 * Type<T>. Each row below the header is an object.
		 */
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableEntryTransformer<Transaction>() {
			@Override
			public Transaction transform(Map<String, String> map) {
				return new Transaction(map.get("name"), map.get("amount"), map.get("frequency"), map.get("month"));
			}
		}));

		/*
		 * Maps DataTable with label column in feature file to a single object of
		 * Type<T>. Left column is field name, right column is value.
		 */
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable dataTable) throws Throwable {
				return new Transaction(dataTable.asMaps().get(0));
			}
		}));

	}

}
