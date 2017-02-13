package jp.co.asnew.chunk.simple

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.core.StepContribution
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@StepScope
@Component('simpleTasklet')
class SimpleTasklet implements Tasklet {

	Logger log = LoggerFactory.getLogger(SimpleTasklet.class)

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		log.info("${getClass().getSimpleName()} Start.")

		log.info("${getClass().getSimpleName()} Done.")

		RepeatStatus.FINISHED

	}
	
}
