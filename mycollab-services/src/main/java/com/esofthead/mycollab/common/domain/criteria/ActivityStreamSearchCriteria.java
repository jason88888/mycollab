/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.common.domain.criteria;

import com.esofthead.mycollab.core.arguments.SearchCriteria;
import com.esofthead.mycollab.core.arguments.SetSearchField;
import com.esofthead.mycollab.core.arguments.StringSearchField;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class ActivityStreamSearchCriteria extends SearchCriteria {
    private static final long serialVersionUID = 1L;

    private SetSearchField<String> moduleSet;
    private SetSearchField<Integer> extraTypeIds;
    private StringSearchField createdUser;

    public StringSearchField getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(StringSearchField createdUser) {
        this.createdUser = createdUser;
    }

    public SetSearchField<String> getModuleSet() {
        return moduleSet;
    }

    public void setModuleSet(SetSearchField<String> moduleSet) {
        this.moduleSet = moduleSet;
    }

    public SetSearchField<Integer> getExtraTypeIds() {
        return extraTypeIds;
    }

    public void setExtraTypeIds(SetSearchField<Integer> extraTypeIds) {
        this.extraTypeIds = extraTypeIds;
    }
}
