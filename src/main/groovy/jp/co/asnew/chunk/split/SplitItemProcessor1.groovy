package jp.co.asnew.chunk.split

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@StepScope
@Component('splitItemProcessor1')
class SplitItemProcessor1 implements ItemProcessor<Object, Object> {

	Logger log = LoggerFactory.getLogger(SplitItemProcessor1.class)
	
	@Override
	public Object process(Object item) throws Exception {
		
		log.info("${getClass().getSimpleName()} Execute. item: ${item}")
		
		item
		
	}
	
}