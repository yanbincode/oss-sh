package trigger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import core.exception.OssRollbackCheckedException;
import core.support.AbstractTriggerSupport;
import core.trigger.annotation.TriggerMethod;
import core.trigger.annotation.TriggerType;

import service.MonthRecordService;

/**
 * 计算月记录的触发器
 * 
 * @author yanbin
 * 
 */
@TriggerType(cronExpression = "0 0 21 1 * ?")
public class MonthRecordTrigger extends AbstractTriggerSupport {

	@Autowired
	private MonthRecordService monthRecordService;

	@TriggerMethod
	public void execute() {
		try {
			monthRecordService.add(new Date());
		} catch (OssRollbackCheckedException e) {
			log.error(e);
		}
	}
	// 可以做后续处理

}
