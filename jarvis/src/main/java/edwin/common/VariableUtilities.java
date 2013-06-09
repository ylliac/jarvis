package edwin.common;

public final class VariableUtilities {

	public static String replaceVariablesByTheirValues(String text,
			VariableRepository variables) {
		String result = "";

		String currentText = text;
		int nextVarIndex = currentText.indexOf('%');
		while (nextVarIndex != -1) {

			// Append the text before the variable declaration
			result += currentText.substring(0, nextVarIndex);

			String varName;

			int nextSpaceIndex = currentText.indexOf(' ', nextVarIndex);
			if (nextSpaceIndex == -1) {
				varName = currentText.substring(nextVarIndex);
				currentText = "";
			} else {
				varName = currentText.substring(nextVarIndex, nextSpaceIndex);
				currentText = currentText.substring(nextSpaceIndex);
			}

			// Remove the '%'
			varName = varName.substring(1);

			// Get the value of the variable
			Variable<?> variable = variables.findFromKey(varName);
			if (variable == null) {
				throw new IllegalArgumentException("Unknown variable "
						+ varName);
			} else {
				// Append the variable value
				Object value = variable.getValue();
				if (value == null) {
					result += "null";
				} else {
					result += value.toString();
				}
			}

			nextVarIndex = currentText.indexOf('%');
		}
		// Append the text after the last variable declaration
		result += currentText;

		return result;
	}

}
