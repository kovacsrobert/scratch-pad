/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package quartz;

import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.triggers.CronTriggerImpl;

public class MyCronTriggerImpl extends CronTriggerImpl implements CronTrigger {

	@Override
	public CompletedExecutionInstruction executionComplete(JobExecutionContext context,
			JobExecutionException result) {
		if (result != null && result.unscheduleAllTriggers()) {
			return CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR;
		}
		return super.executionComplete(context, result);
	}
}
