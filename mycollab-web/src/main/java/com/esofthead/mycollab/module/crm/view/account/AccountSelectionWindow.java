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
package com.esofthead.mycollab.module.crm.view.account;

import com.esofthead.mycollab.module.crm.CrmTooltipGenerator;
import com.esofthead.mycollab.module.crm.domain.Account;
import com.esofthead.mycollab.module.crm.domain.SimpleAccount;
import com.esofthead.mycollab.module.crm.domain.criteria.AccountSearchCriteria;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.SearchHandler;
import com.esofthead.mycollab.vaadin.web.ui.ButtonLink;
import com.esofthead.mycollab.vaadin.ui.FieldSelection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.Arrays;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class AccountSelectionWindow extends Window {
    private static final long serialVersionUID = 1L;

    private AccountTableDisplay tableItem;
    private FieldSelection<Account> fieldSelection;

    public AccountSelectionWindow(FieldSelection<Account> fieldSelection) {
        super("Account Selection");
        this.setWidth("900px");
        this.setResizable(false);
        this.setModal(true);
        this.fieldSelection = fieldSelection;
    }

    public void show() {
        MVerticalLayout layout = new MVerticalLayout();
        createAccountList();

        AccountSimpleSearchPanel accountSimpleSearchPanel = new AccountSimpleSearchPanel();
        accountSimpleSearchPanel.addSearchHandler(new SearchHandler<AccountSearchCriteria>() {
            @Override
            public void onSearch(AccountSearchCriteria criteria) {
                tableItem.setSearchCriteria(criteria);
            }

        });
        layout.addComponent(accountSimpleSearchPanel);
        layout.addComponent(tableItem);
        this.setContent(layout);

        tableItem.setSearchCriteria(new AccountSearchCriteria());
        center();
    }

    private void createAccountList() {
        tableItem = new AccountTableDisplay(Arrays.asList(
                AccountTableFieldDef.accountname(), AccountTableFieldDef.city(),
                AccountTableFieldDef.assignUser()));

        tableItem.setWidth("100%");
        tableItem.setDisplayNumItems(10);

        tableItem.addGeneratedColumn("accountname", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component generateCell(Table source, Object itemId, Object columnId) {
                final SimpleAccount account = tableItem.getBeanByIndex(itemId);

                ButtonLink accountLink = new ButtonLink(account.getAccountname(), new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        fieldSelection.fireValueChange(account);
                        AccountSelectionWindow.this.close();
                    }
                });
                accountLink.setDescription(CrmTooltipGenerator.generateToolTipAccount(
                        AppContext.getUserLocale(), account, AppContext.getSiteUrl()));
                return accountLink;
            }
        });
    }
}
