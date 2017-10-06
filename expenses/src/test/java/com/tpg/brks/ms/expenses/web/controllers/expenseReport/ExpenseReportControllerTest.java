package com.tpg.brks.ms.expenses.web.controllers.expenseReport;

import com.tpg.brks.ms.expenses.web.GivenTest;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseReportQueryController;
import com.tpg.brks.ms.expenses.web.model.WebApplicationUserFixture;
import com.tpg.brks.ms.expenses.web.resources.ExpenseReportResourceAssembler;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(ExpenseReportResourceAssembler.class)
@WebMvcTest(ExpenseReportQueryController.class)
public abstract class ExpenseReportControllerTest extends GivenTest implements WebApplicationUserFixture {
}
