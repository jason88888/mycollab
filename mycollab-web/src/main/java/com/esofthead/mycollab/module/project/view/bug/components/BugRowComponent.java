/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.view.bug.components;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.configuration.SiteConfiguration;
import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.events.BugEvent;
import com.esofthead.mycollab.module.project.ui.components.IGroupComponent;
import com.esofthead.mycollab.module.project.view.bug.BugPopupFieldFactory;
import com.esofthead.mycollab.module.tracker.domain.SimpleBug;
import com.esofthead.mycollab.module.tracker.service.BugService;
import com.esofthead.mycollab.spring.AppContextUtil;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.mvp.ViewManager;
import com.esofthead.mycollab.vaadin.ui.UIUtils;
import com.esofthead.mycollab.vaadin.web.ui.ConfirmDialogExt;
import com.esofthead.mycollab.vaadin.web.ui.OptionPopupContent;
import com.esofthead.mycollab.vaadin.web.ui.UIConstants;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.hene.popupbutton.PopupButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author MyCollab Ltd
 * @since 5.1.1
 */
public class BugRowComponent extends MVerticalLayout {
    private SimpleBug bug;

    private PopupButton bugSettingPopupBtn;

    public BugRowComponent(final SimpleBug bug) {
        this.bug = bug;
        withSpacing(true).withMargin(false).withFullWidth().addStyleName(UIConstants.BORDER_LIST_ROW);

        bugSettingPopupBtn = new PopupButton();
        bugSettingPopupBtn.setIcon(FontAwesome.COGS);
        bugSettingPopupBtn.addStyleName(UIConstants.BUTTON_ICON_ONLY);
        OptionPopupContent filterBtnLayout = createPopupContent();
        bugSettingPopupBtn.setContent(filterBtnLayout);

        final ToggleBugSummaryField bugWrapper = new ToggleBugSummaryField(bug);

        BugPopupFieldFactory popupFieldFactory = ViewManager.getCacheComponent(BugPopupFieldFactory.class);
        MHorizontalLayout headerLayout = new MHorizontalLayout().withFullWidth().withMargin(new MarginInfo(false,
                true, false, false));
        Component priorityField = popupFieldFactory.createPriorityPopupField(bug);
        Component assigneeField = popupFieldFactory.createAssigneePopupField(bug);
        headerLayout.with(bugSettingPopupBtn, priorityField, assigneeField, bugWrapper).expand(bugWrapper);

        CssLayout footer = new CssLayout();

        Component commentsField = popupFieldFactory.createCommentsPopupField(bug);
        footer.addComponent(commentsField);

        Component followerField = popupFieldFactory.createFollowersPopupField(bug);
        footer.addComponent(followerField);

        Component statusField = popupFieldFactory.createStatusPopupField(bug);
        footer.addComponent(statusField);

        Component milestoneField = popupFieldFactory.createMilestonePopupField(bug);
        footer.addComponent(milestoneField);

        String deadlineTooltip = String.format("%s: %s", AppContext.getMessage(GenericI18Enum.FORM_DUE_DATE),
                AppContext.formatDate(bug.getDuedate()));
        AbstractComponent deadlineField = popupFieldFactory.createDeadlinePopupField(bug);
        deadlineField.setDescription(deadlineTooltip);
        footer.addComponent(deadlineField);

        Component startDateField = popupFieldFactory.createStartDatePopupField(bug);
        footer.addComponent(startDateField);

        Component endDateField = popupFieldFactory.createEndDatePopupField(bug);
        footer.addComponent(endDateField);

        if (!SiteConfiguration.isCommunityEdition()) {
            Component billableHoursView = popupFieldFactory.createBillableHoursPopupField(bug);
            footer.addComponent(billableHoursView);

            Component nonBillableHoursView = popupFieldFactory.createNonbillableHoursPopupField(bug);
            footer.addComponent(nonBillableHoursView);
        }

        this.with(headerLayout, footer);
    }

    private void deleteBug() {
        IGroupComponent root = UIUtils.getRoot(this, IGroupComponent.class);
        ComponentContainer parent = (ComponentContainer) this.getParent();
        if (parent != null) {
            parent.removeComponent(this);
            if (root != null) {
                ComponentContainer parentRoot = (ComponentContainer) root.getParent();
                if (parentRoot != null && parent.getComponentCount() == 0) {
                    parentRoot.removeComponent(root);
                }
            }
        }
    }

    private OptionPopupContent createPopupContent() {
        OptionPopupContent filterBtnLayout = new OptionPopupContent();

        Button editButton = new Button(AppContext.getMessage(GenericI18Enum.BUTTON_EDIT), new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                bugSettingPopupBtn.setPopupVisible(false);
                EventBusFactory.getInstance().post(new BugEvent.GotoEdit(BugRowComponent.this, bug));
            }
        });
        editButton.setEnabled(CurrentProjectVariables.canWrite(ProjectRolePermissionCollections.BUGS));
        editButton.setIcon(FontAwesome.EDIT);
        filterBtnLayout.addOption(editButton);
        Button deleteBtn = new Button(AppContext.getMessage(GenericI18Enum.BUTTON_DELETE), new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                bugSettingPopupBtn.setPopupVisible(false);
                ConfirmDialogExt.show(UI.getCurrent(),
                        AppContext.getMessage(GenericI18Enum.DIALOG_DELETE_TITLE, AppContext.getSiteName()),
                        AppContext.getMessage(GenericI18Enum.DIALOG_DELETE_SINGLE_ITEM_MESSAGE),
                        AppContext.getMessage(GenericI18Enum.BUTTON_YES),
                        AppContext.getMessage(GenericI18Enum.BUTTON_NO),
                        new ConfirmDialog.Listener() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    BugService bugService = AppContextUtil.getSpringBean(BugService.class);
                                    bugService.removeWithSession(bug, AppContext.getUsername(), AppContext.getAccountId());
                                    deleteBug();
                                }
                            }
                        });
            }
        });
        deleteBtn.setIcon(FontAwesome.TRASH_O);
        deleteBtn.setEnabled(CurrentProjectVariables.canAccess(ProjectRolePermissionCollections.TASKS));
        filterBtnLayout.addDangerOption(deleteBtn);
        return filterBtnLayout;
    }
}
