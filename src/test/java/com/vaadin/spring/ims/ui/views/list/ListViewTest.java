package com.vaadin.spring.ims.ui.views.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.spring.ims.backend.entity.Intern;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
        public void formShownWhenContactSelected() {
            Grid<Intern> grid = listView.grid;
            Intern firstIntern = getFirstItem(grid);

            InternForm form = listView.form;

            Assert.assertFalse(form.isVisible());
    		grid.asSingleSelect().setValue(firstIntern);
            Assert.assertTrue(form.isVisible());
            Assert.assertEquals(firstIntern.getFirstName(), form.firstName.getValue());
        }

    	private Intern getFirstItem(Grid<Intern> grid) {
    		return( (ListDataProvider<Intern>) grid.getDataProvider()).getItems().iterator().next();
    	}

}
