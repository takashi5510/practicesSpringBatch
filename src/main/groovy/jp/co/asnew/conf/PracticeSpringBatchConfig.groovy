package jp.co.asnew.conf

import java.util.List
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.item.support.CompositeItemProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import jp.co.asnew.chunk.*
import jp.co.asnew.chunk.composite.*
import jp.co.asnew.chunk.simple.*
import jp.co.asnew.chunk.split.*
import jp.co.asnew.listener.CommonJobListener

@Configuration
@EnableBatchProcessing
class PracticeSpringBatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory

	@Autowired
	StepBuilderFactory stepBuilderFactory

	@Autowired
	CommonJobListener commonJobListener
	
	@Autowired
	CommonItemWriter commonItemWriter
	
	@Autowired
	SimpleTasklet simpleTasklet
	
	@Autowired
	SimpleItemReader simpleItemReader
	
	@Autowired
	SimpleItemProcessor simpleItemProcessor
	
	@Autowired
	CompositeItemReader compositeItemReader
	
	@Autowired
	CompositeItemProcessor1 compositeItemProcessor1
	
	@Autowired
	CompositeItemProcessor2 compositeItemProcessor2
	
	@Autowired
	SplitItemReader1 splitItemReader1
	
	@Autowired
	SplitItemReader2 splitItemReader2
	
	@Autowired
	SplitItemProcessor1 splitItemProcessor1
	
	@Autowired
	SplitItemProcessor2 splitItemProcessor2
	
	SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
		new SimpleAsyncTaskExecutor()
	}

	@Bean
	Job simpleJob() {
		jobBuilderFactory.get('simpleJob')
			.listener(commonJobListener)
			.start(simpleTaskletStep())
			.next(simpleChunkStep())
			.build()
	}
	Step simpleTaskletStep() {
		stepBuilderFactory.get('simpleTaskletStep')
			.tasklet(simpleTasklet)
			.allowStartIfComplete(true)
			.build()
	}
	Step simpleChunkStep() {
		stepBuilderFactory.get('simpleChunkStep')
			.<Object, Object> chunk(1)
			.reader(simpleItemReader)
			.processor(simpleItemProcessor)
			.writer(commonItemWriter)
			.build()
	}
	
	@Bean
	Job compositeJob() {
		jobBuilderFactory.get('compositeJob')
			.listener(commonJobListener)
			.start(compositeStep())
			.build()
	}
	Step compositeStep() {
		stepBuilderFactory.get('compositeStep')
			.<Object, Object> chunk(1)
			.reader(compositeItemReader)
			.processor(compositeProcessor())
			.writer(commonItemWriter)
			.build()
	}
	CompositeItemProcessor<Object, Object> compositeProcessor() {
		CompositeItemProcessor compositeProcessor = new CompositeItemProcessor<Object, Object>()
		compositeProcessor.setDelegates([
			compositeItemProcessor1, compositeItemProcessor2,
		])
		compositeProcessor
	}

	@Bean
	Job splitFlowJob() {
		jobBuilderFactory.get('splitFlowJob')
			.listener(commonJobListener)
			.start(splitFlow())
			.end()
			.build()
	}
	Flow splitFlow() {
		new FlowBuilder<Flow>('splitFlow')
			.split(simpleAsyncTaskExecutor())
			.add(splitFlow1(), splitFlow2())
			.build()
	}
	Flow splitFlow1() {
		new FlowBuilder<Flow>('splitFlow1')
			.start(splitFlowStep1())
			.build()
	}
	Step splitFlowStep1() {
		stepBuilderFactory.get('splitFlowStep1')
			.<Object, Object> chunk(1)
			.reader(splitItemReader1)
			.processor(splitItemProcessor1)
			.writer(commonItemWriter)
			.build()
	}
	Flow splitFlow2() {
		new FlowBuilder<Flow>('splitFlow2')
			.start(splitFlowStep2())
			.build()
	}
	Step splitFlowStep2() {
		stepBuilderFactory.get('splitFlowStep2')
			.<Object, Object> chunk(1)
			.reader(splitItemReader2)
			.processor(splitItemProcessor2)
			.writer(commonItemWriter)
			.build()
	}

}
