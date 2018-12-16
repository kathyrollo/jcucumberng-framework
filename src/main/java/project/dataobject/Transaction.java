package project.dataobject;

import java.util.Map;

public class Transaction {

	private String name = null;
	private String amount = null;
	private String frequency = null;
	private String month = null;

	public Transaction(String name, String amount, String frequency, String month) {
		this.name = name;
		this.amount = amount;
		this.frequency = frequency;
		this.month = month;
	}

	public Transaction(Map<String, String> map) {
		this.name = map.get("name");
		this.amount = map.get("amount");
		this.frequency = map.get("frequency");
		this.month = map.get("month");
	}

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
