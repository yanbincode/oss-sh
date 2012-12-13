package trigger;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import service.YearRecordService;
import core.exception.OssRollbackCheckedException;
import core.support.AbstractTriggerSupport;
import core.trigger.annotation.TriggerMethod;
import core.trigger.annotation.TriggerType;

/**
 * 年报统计日终
 * 
 * @author yanbin
 * 
 */
@TriggerType(cronExpression = "0 0 21 1 * ?")
public class YearRecordTrigger extends AbstractTriggerSupport {

	@Autowired
	private YearRecordService yearRecordService;

	@TriggerMethod
	public void execute() {
		try {
			yearRecordService.add(new Date());
		} catch (OssRollbackCheckedException e) {
			log.error(e);
		}
	}
	
}
