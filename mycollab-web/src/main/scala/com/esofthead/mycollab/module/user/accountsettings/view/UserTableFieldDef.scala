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
package com.esofthead.mycollab.module.user.accountsettings.view

import com.esofthead.mycollab.common.TableViewField
import com.esofthead.mycollab.common.i18n.GenericI18Enum
import com.esofthead.mycollab.module.user.accountsettings.localization.{RoleI18nEnum, UserI18nEnum}
import com.esofthead.mycollab.module.user.domain.{SimpleUser, User}
import com.esofthead.mycollab.vaadin.web.ui.UIConstants

/**
  * @author MyCollab Ltd
  * @since 5.2.12
  */
object UserTableFieldDef {
  val username = new TableViewField(GenericI18Enum.FORM_NAME, User.Field.username.name(), UIConstants.TABLE_EX_LABEL_WIDTH)
  val rolename = new TableViewField(UserI18nEnum.FORM_ROLE, SimpleUser.Field.roleid.name(), UIConstants.TABLE_EX_LABEL_WIDTH)
  val email = new TableViewField(UserI18nEnum.FORM_EMAIL, User.Field.email.name(), UIConstants.TABLE_X_LABEL_WIDTH)
  val birthday = new TableViewField(UserI18nEnum.FORM_BIRTHDAY, User.Field.dateofbirth.name(), UIConstants.TABLE_DATE_WIDTH)
  val officephone = new TableViewField(UserI18nEnum.FORM_WORK_PHONE, User.Field.workphone.name(), UIConstants.TABLE_M_LABEL_WIDTH)
  val homephone = new TableViewField(UserI18nEnum.FORM_HOME_PHONE, User.Field.homephone.name(), UIConstants.TABLE_M_LABEL_WIDTH)
  val company = new TableViewField(UserI18nEnum.FORM_COMPANY, User.Field.company.name(), UIConstants.TABLE_M_LABEL_WIDTH)
}
