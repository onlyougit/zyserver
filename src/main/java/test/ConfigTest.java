package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTest {

	@Bean
	public FunctionService functionService(){
		return new FunctionService();
	}
	@Bean
	public UseFunctionService useFunctionService(FunctionService functionService){
		UseFunctionService useFunctionService = new UseFunctionService();
		useFunctionService.setFunctionService(functionService);
		return useFunctionService;
	}
	
}
