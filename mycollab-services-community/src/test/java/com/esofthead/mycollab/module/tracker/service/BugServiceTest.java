/**
 * This file is part of mycollab-services-community.
 *
 * mycollab-services-community is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services-community is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services-community.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.tracker.service;

import com.esofthead.mycollab.common.domain.GroupItem;
import com.esofthead.mycollab.core.arguments.*;
import com.esofthead.mycollab.module.tracker.domain.BugWithBLOBs;
import com.esofthead.mycollab.module.tracker.domain.SimpleBug;
import com.esofthead.mycollab.module.tracker.domain.criteria.BugSearchCriteria;
import com.esofthead.mycollab.test.DataSet;
import com.esofthead.mycollab.test.service.IntergrationServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringJUnit4ClassRunner.class)
public class BugServiceTest extends IntergrationServiceTest {

    @Autowired
    protected BugService bugService;

    @DataSet
    @Test
    public void testGetListBugs() {
        List<SimpleBug> bugs = bugService.findPagableListByCriteria(new BasicSearchRequest<BugSearchCriteria>(
                null, 0, Integer.MAX_VALUE));

        assertThat(bugs.size()).isEqualTo(3);
        assertThat(bugs).extracting("id", "detail", "summary").contains(
                tuple(1, "detail 1", "summary 1"),
                tuple(2, "detail 2", "summary 2"),
                tuple(3, "detail 3", "summary 3"));
    }

    @DataSet
    @Test
    public void testSearchDefectsByUserCriteria() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        criteria.setAssignuser(StringSearchField.and("user1"));
        criteria.setLoguser(StringSearchField.and("admin"));
        criteria.setSummary(StringSearchField.and("summary"));
        criteria.setDetail(StringSearchField.and("detail"));

        List<SimpleBug> bugs = bugService.findPagableListByCriteria(new BasicSearchRequest<>(criteria, 0, Integer.MAX_VALUE));
        assertThat(bugs.size()).isEqualTo(1);
        assertThat(bugs).extracting("id", "detail", "summary").contains(tuple(2, "detail 2", "summary 2"));
    }

    @DataSet
    @Test
    public void testGetExtBug() {
        SimpleBug bug = bugService.findById(1, 1);
        assertThat(bug.getLoguserFullName()).isEqualTo("Nguyen Hai");
        assertThat(bug.getAssignuserFullName()).isEqualTo("Nguyen Hai");
        assertThat(bug.getAffectedVersions().size()).isEqualTo(1);
        assertThat(bug.getFixedVersions().size()).isEqualTo(2);
        assertThat(bug.getComponents().size()).isEqualTo(1);
    }

    @DataSet
    @Test
    public void testSearchByComponents() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        criteria.setComponentids(new SetSearchField<>(1, 2));

        List<SimpleBug> bugs = bugService.findPagableListByCriteria(new BasicSearchRequest<>(criteria, 0, Integer.MAX_VALUE));

        assertThat(bugs.size()).isEqualTo(1);
        assertThat(bugs).extracting("id", "detail", "summary").contains(
                tuple(1, "detail 1", "summary 1"));
    }

    @DataSet
    @Test
    public void testGetComponentDefectsSummary() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        criteria.setProjectId(new NumberSearchField(1));
        bugService.getComponentDefectsSummary(criteria);
    }

    @DataSet
    @Test
    public void testSearchByVersions() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        criteria.setFixedversionids(new SetSearchField<>(1, 2, 3));
        criteria.setAffectedversionids(new SetSearchField<>(1, 2, 3));

        List<SimpleBug> bugs = bugService.findPagableListByCriteria(new BasicSearchRequest<>(criteria, 0, Integer.MAX_VALUE));

        assertThat(bugs.size()).isEqualTo(1);
        assertThat(bugs).extracting("id", "detail", "summary").contains(tuple(1, "detail 1", "summary 1"));
    }

    @DataSet
    @Test
    public void testSearchByAssignedUser() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        List<GroupItem> assignedDefectsSummary = bugService.getAssignedDefectsSummary(criteria);

        assertThat(assignedDefectsSummary.size()).isEqualTo(2);
        assertThat(assignedDefectsSummary).extracting("groupid", "value", "extraValue").contains(tuple("admin", 1, null),
                tuple("user1", 2, null));
    }

    @DataSet
    @Test
    public void testSearchByDateCriteria2() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        Calendar date = new GregorianCalendar();
        date.set(Calendar.YEAR, 2009);
        date.set(Calendar.MONTH, 0);
        date.set(Calendar.DAY_OF_MONTH, 2);

        criteria.setUpdatedDate(new DateSearchField(date.getTime()));

        assertThat(bugService.findPagableListByCriteria(new BasicSearchRequest<>(criteria, 0, Integer.MAX_VALUE)).size()).isEqualTo(0);
    }

    @DataSet
    @Test
    public void testBugStatus() {
        BugSearchCriteria criteria = new BugSearchCriteria();
        List<GroupItem> groupitems = bugService.getStatusSummary(criteria);
        assertThat(groupitems.size()).isEqualTo(1);
        assertThat(groupitems).extracting("groupid", "value", "extraValue").contains(tuple("1", 3, null));
    }

    @Test
    @DataSet
    public void testSaveBug() {
        BugWithBLOBs bug = new BugWithBLOBs();
        bug.setSummary("summary4");
        bug.setStatus("aaa");
        bug.setProjectid(1);
        bug.setSaccountid(1);
        int bugId = bugService.saveWithSession(bug, "admin");
        assertThat(bugService.findById(bugId, 1).getSummary()).isEqualTo("summary4");
    }
}
