package com.tpg.brks.ms.expenses.web.controllers.expense;

import com.tpg.brks.ms.expenses.web.GivenTest;
import com.tpg.brks.ms.expenses.web.controllers.ExpenseQueryController;
import com.tpg.brks.ms.expenses.web.resources.ExpenseResourceAssembler;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(ExpenseResourceAssembler.class)
@WebMvcTest(ExpenseQueryController.class)
public abstract class ExpenseControllerTest extends GivenTest {
}
