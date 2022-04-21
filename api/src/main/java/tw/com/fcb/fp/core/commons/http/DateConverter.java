package tw.com.fcb.fp.core.commons.http;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, LocalDate>{

	@Override
	public LocalDate convert(String source) {
		// TODO Auto-generated method stub
		
		if(source == null) {
			return LocalDate.now();
		}
		
		if(source.length() == 10) {
			return LocalDate.of(Integer.valueOf(source.substring(0,4)), 
					Integer.valueOf(source.substring(5,7)), 
					Integer.valueOf(source.substring(8,10)));
		}else if(source.length() == 8) {
			return LocalDate.of(Integer.valueOf(source.substring(0,4)), 
					Integer.valueOf(source.substring(4,6)), 
					Integer.valueOf(source.substring(6,8)));
		} else if (source.length() == 7) {
			
			String rocYear = source.substring(0,3);
			
			return LocalDate.of(Integer.parseInt(rocYear)+1911, 
					Integer.valueOf(source.substring(3,5)), 
					Integer.valueOf(source.substring(5,7)));
			
		} else if (source.length() == 6) {
			
			String rocYear = source.substring(0,2);
			
			return LocalDate.of(Integer.parseInt(rocYear)+1911, 
					Integer.valueOf(source.substring(2,4)), 
					Integer.valueOf(source.substring(4,6)));
		}
		
		
		return null;
	}

}
