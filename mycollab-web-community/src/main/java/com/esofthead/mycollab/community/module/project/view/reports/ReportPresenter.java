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
package com.esofthead.mycollab.community.module.project.view.reports;

import com.esofthead.mycollab.module.project.view.ProjectModule;
import com.esofthead.mycollab.module.project.view.reports.IReportContainer;
import com.esofthead.mycollab.module.project.view.reports.IReportPresenter;
import com.esofthead.mycollab.module.project.view.user.ProjectDashboardContainer;
import com.esofthead.mycollab.vaadin.mvp.ScreenData;
import com.esofthead.mycollab.vaadin.web.ui.AbstractPresenter;
import com.vaadin.ui.ComponentContainer;

/**
 * @author MyCollab Ltd
 * @since 5.2.10
 */
public class ReportPresenter extends AbstractPresenter<IReportContainer> implements IReportPresenter {
    public ReportPresenter() {
        super(IReportContainer.class);
    }

    @Override
    protected void onGo(ComponentContainer container, ScreenData<?> data) {
        ProjectModule projectViewContainer = (ProjectModule) container;
        projectViewContainer.removeAllComponents();
        projectViewContainer.addComponent(view);
    }
}
