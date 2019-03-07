package org.onap.ccsdk.dashboard.model.inventory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blueprint {

	private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

	static {
		YAML_MAPPER.registerModule(new Jdk8Module());
	}

	@JsonProperty("inputs")
	private Map<String, BlueprintInput> inputs;
	@JsonProperty("description")
	private String description;

	public static Blueprint parse(String blueprint) throws BlueprintParseException {
		try {
			return getYamlMapper().readValue(blueprint, Blueprint.class);
		} catch (IOException e) {
			throw new BlueprintParseException(e);
		}
	}

	private static ObjectMapper getYamlMapper() {
		return YAML_MAPPER;
	}

	public Map<String, BlueprintInput> getInputs() {
		return inputs;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "inputs: " + ((getInputs() != null) ? getInputs().toString() : "{}");
	}
	
	public static void main(String args[]) throws Exception {

		File file = new File("C:\\Temp\\testBP.yaml");
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");
		try {
			while(scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
		    }
			Blueprint bp = Blueprint.parse(fileContents.toString());
			System.out.println("blueprint contents: " + bp.toString());
		    } finally {
		        scanner.close();
		    }	
	}
}
