package com.prefin.service.money;

import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@AllArgsConstructor
public class AutoTransferJob implements Job {

    private final AllowanceService allowanceService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("====================== Job Executed =======================");
//        allowanceService.allowanceTransfer();
    }

}
