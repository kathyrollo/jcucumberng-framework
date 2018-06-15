package jcucumberng.steps.config;

import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import jcucumberng.steps.domain.RegularTransaction;

/*
 * Maps datatables in feature files to custom domain objects.
 */
public class DataTableConfigurer implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry registry) {
		registry.defineDataTableType(new DataTableType(RegularTransaction.class, new TableEntryTransformer<RegularTransaction>() {
			@Override
			public RegularTransaction transform(Map<String, String> entry) {
				return new RegularTransaction(entry.get("name"), entry.get("amount"), entry.get("frequency"));
			}
		}));
	}

}
