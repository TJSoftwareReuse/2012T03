package tj.reuse;

import java.util.Properties;

public interface ConfigInterface {
	 String readValue(String filePath,String key);
	 Properties readProperties(String filePath);
	 Properties writeProperties(String filePath,String parameterName
				,String parameterValue);
}
