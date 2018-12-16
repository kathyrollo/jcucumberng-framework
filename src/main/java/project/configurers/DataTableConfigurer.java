package project.configurers;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableTransformer;
import project.dataobjects.Transaction;

public class DataTableConfigurer implements TypeRegistryConfigurer {

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		/*
		 * Maps DataTable with header row to multiple objects of Type<T>. Each row below
		 * the header is an object.
		 */
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableEntryTransformer<Transaction>() {
			@Override
			public Transaction transform(Map<String, String> entry) {
				return Transaction.getInstance(entry);
			}
		}));

		/*
		 * Maps DataTable with label column to a single object of Type<T>. Left column
		 * is field name, right column is value.
		 */
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable table) throws Throwable {
				return Transaction.getInstance(table.asMaps().get(0));
			}
		}));

	}

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

}
