package project.dataobject;

import java.util.Map;

public class Transaction {

	private String name = null;
	private String amount = null;
	private String frequency = null;
	private String month = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public static Transaction getInstance(Map<String, String> entry) {
		Transaction transaction = new Transaction();
		transaction.setName(entry.get("name"));
		transaction.setAmount(entry.get("amount"));
		transaction.setFrequency(entry.get("frequency"));
		transaction.setMonth(entry.get("month"));
		return transaction;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [name=");
		builder.append(name);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append(", month=");
		builder.append(month);
		builder.append("]");
		return builder.toString();
	}

}
