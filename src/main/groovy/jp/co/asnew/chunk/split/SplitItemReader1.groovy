package jp.co.asnew.chunk.split

import java.util.List
import javax.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.NonTransientResourceException
import org.springframework.batch.item.ParseException
import org.springframework.batch.item.UnexpectedInputException
import org.springframework.stereotype.Component

@StepScope
@Component('splitItemReader1')
class SplitItemReader1 implements ItemReader<Object> {
	
	Logger log = LoggerFactory.getLogger(SplitItemReader1.class)

	int index = 0
	List inputs = []

	@PostConstruct
	def init() {
		inputs = [1, 2, 3]
	}
	
	@Override
	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		log.info("${getClass().getSimpleName()} Execute.")
		index < inputs.size() ? inputs[index++] : null
	}
	
}
