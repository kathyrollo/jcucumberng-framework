package project.datatables;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import io.cucumber.datatable.TableTransformer;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import project.domain.simplydo.Transaction;

public class Configurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {

		JacksonTableTransformer jtt = new JacksonTableTransformer();
		registry.setDefaultParameterTransformer(jtt);
		registry.setDefaultDataTableCellTransformer(jtt);
		registry.setDefaultDataTableEntryTransformer(jtt);

		/*
		 * Maps DataTable with label column to a single object of Type<T>. Left column
		 * is field name, right column is value.
		 */
		registry.defineDataTableType(new DataTableType(Transaction.class, new TableTransformer<Transaction>() {
			@Override
			public Transaction transform(DataTable table) throws Throwable {
				return new Transaction(table.asMaps().get(0));
			}
		}));

	}

	private static final class JacksonTableTransformer
			implements ParameterByTypeTransformer, TableCellByTypeTransformer, TableEntryByTypeTransformer {

		private final ObjectMapper objMapper = new ObjectMapper();

		@Override
		public Object transform(String fromValue, Type toValueType) throws Throwable {
			return objMapper.convertValue(fromValue, objMapper.constructType(toValueType));
		}

		@Override
		public <T> T transform(String value, Class<T> cellType) throws Throwable {
			return objMapper.convertValue(value, cellType);
		}

		@Override
		public <T> T transform(Map<String, String> entry, Class<T> type, TableCellByTypeTransformer cellTransformer)
				throws Throwable {
			return objMapper.convertValue(entry, type);
		}

	}

}
