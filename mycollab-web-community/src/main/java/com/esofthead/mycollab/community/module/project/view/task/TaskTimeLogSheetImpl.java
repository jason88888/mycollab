/**
 * This file is part of mycollab-web-community.
 *
 * mycollab-web-community is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web-community is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web-community.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.community.module.project.view.task;

import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.domain.SimpleTask;
import com.esofthead.mycollab.module.project.view.task.components.TaskTimeLogSheet;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.view.NotPresentWindow;
import com.vaadin.ui.UI;

/**
 * @author MyCollab Ltd
 * @since 5.1.4
 */
@ViewComponent
public class TaskTimeLogSheetImpl extends TaskTimeLogSheet {
    @Override
    protected Double getTotalBillableHours(SimpleTask bean) {
        return 0d;
    }

    @Override
    protected Double getTotalNonBillableHours(SimpleTask bean) {
        return 0d;
    }

    @Override
    protected Double getRemainedHours(SimpleTask bean) {
        return 0d;
    }

    @Override
    protected boolean hasEditPermission() {
        return CurrentProjectVariables.canWrite(ProjectRolePermissionCollections.TASKS);
    }

    @Override
    protected void showEditTimeWindow(SimpleTask bean) {
        UI.getCurrent().addWindow(new NotPresentWindow());
    }
}
