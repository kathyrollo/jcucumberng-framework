package project.wip;

import java.util.Locale;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableTransformer;
import project.domain.Transaction;

/**
 * Maps DataTable with label column in feature file to a single object of
 * Type{@literal <T>}. Left column is field name, right column is value.
 */
public class TableConfigurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable dataTable) throws Throwable {
				return new Transaction(dataTable.asMaps().get(0));
			}
		}));

	}

}
